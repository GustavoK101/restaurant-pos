package com.example.order.service;

import com.example.order.Repository.OrderRepository;
import com.example.order.client.MenuClient;
import com.example.order.config.RabbitMQConfig;
import com.example.order.dto.MenuItemDTO;
import com.example.order.dto.OrderRequestDTO;
import com.example.order.model.Order;
import com.example.order.model.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    private final MenuClient menuClient; // Our Feign Client
    private final RabbitTemplate rabbitTemplate;

    public Order createOrder(OrderRequestDTO request) {
        Order order = new Order();
        order.setCustomer(request.getCustomer());
        order.setStatus("CREATED");
        order.setCreatedAt(LocalDateTime.now());

        double total = 0;
        List<OrderItem> items = new ArrayList<>();

        for (var reqItem : request.getOrderItems()) {
            // Synchronous call to Menu Service
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

        // Notify via RabbitMQ
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
