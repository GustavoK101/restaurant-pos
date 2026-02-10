package com.example.order.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "orders")
@Data
public class Order {
    @Id
    private String id;
    private Customer customer;
    private List<OrderItem> orderItems;
    private Double totalAmount;
    private String status; // CREATED, PREPARING, DELIVERED
    private LocalDateTime createdAt;
}
