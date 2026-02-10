package com.example.order.dto;

import com.example.order.model.Customer;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {
    private Customer customer;
    private List<OrderItemRequest> orderItems;

    @Data
    public static class OrderItemRequest {
        private String productId;
        private Integer quantity;
    }
}
