package com.example.order.controller;

import com.example.order.dto.OrderHistoryResponseDTO;
import com.example.order.dto.OrderRequestDTO;
import com.example.order.dto.OrderStatusUpdateDTO; // You'll need to create this DTO
import com.example.order.model.Order;
import com.example.order.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(@RequestBody OrderRequestDTO request) {
        return orderService.createOrder(request);
    }

    @GetMapping
    public OrderHistoryResponseDTO getAllOrders(
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset) {

        int pageNumber = offset / limit;
        Page<Order> page = orderService.getAllOrders(PageRequest.of(pageNumber, limit));

        // Wrap the results into your custom format
        return new OrderHistoryResponseDTO(
                page.getContent(),
                limit,
                offset,
                page.getTotalElements() // This maps to totalRecords
        );
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable String id) {
        return orderService.getOrderById(id);
    }


    @PatchMapping("/{orderId}/status")
    public Order updateOrderStatus(
            @PathVariable String orderId,
            @RequestBody OrderStatusUpdateDTO statusUpdate) {

        return orderService.updateStatus(orderId, statusUpdate.getStatus());
    }
}