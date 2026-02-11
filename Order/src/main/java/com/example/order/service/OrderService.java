package com.example.order.service;

import com.example.order.Repository.OrderRepository;
import com.example.order.client.MenuClient;
import com.example.order.config.RabbitMQConfig;
import com.example.order.dto.MenuItemDTO;
import com.example.order.dto.OrderRequestDTO;
import com.example.order.model.Order;
import com.example.order.model.OrderItem;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository repository;
    private final MenuClient menuClient;
    private final RabbitTemplate rabbitTemplate;

    public OrderService(OrderRepository repository, MenuClient menuClient, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.menuClient = menuClient;
        this.rabbitTemplate = rabbitTemplate;
    }

    public Order createOrder(OrderRequestDTO request) {
        Order order = new Order();
        order.setCustomer(request.getCustomer());
        order.setStatus("CREATED");
        order.setCreatedAt(LocalDateTime.now());

        double total = 0;
        List<OrderItem> items = new ArrayList<>();

        for (var reqItem : request.getOrderItems()) {
            MenuItemDTO menuInfo = menuClient.getMenuItemById(reqItem.getProductId());

            OrderItem item = new OrderItem();
            item.setProductId(menuInfo.getId());
            item.setName(menuInfo.getName());
            item.setPrice(menuInfo.getPrice());
            item.setQuantity(reqItem.getQuantity());

            items.add(item);
            total += (menuInfo.getPrice() * reqItem.getQuantity());
        }

        order.setOrderItems(items);
        order.setTotalAmount(total);

        Order savedOrder = repository.save(order);
        sendStatusNotification(savedOrder);

        return savedOrder;
    }

    // Updated to support pagination requirements
    public Page<Order> getAllOrders(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Order getOrderById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    public Order updateStatus(String id, String newStatus) {
        // Changed orderRepository to repository to match your final field name
        Order order = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        order.setStatus(newStatus);
        order.setUpdatedAt(LocalDateTime.now());
        Order savedOrder = repository.save(order);

        // Trigger RabbitMQ notification on status change
        sendStatusNotification(savedOrder);

        return savedOrder;
    }

    private void sendStatusNotification(Order order) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                order
        );
    }
}