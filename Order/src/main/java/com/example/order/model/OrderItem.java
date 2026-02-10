package com.example.order.model;

import lombok.Data;

@Data
public class OrderItem {
    private String productId;
    private String name;
    private Integer quantity;
    private Double price;
}