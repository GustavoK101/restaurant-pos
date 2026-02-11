package com.example.order.dto;

import com.example.order.model.Order;
import java.util.List;

public class OrderHistoryResponseDTO {
    private List<Order> orders;
    private int limit;
    private long offset;
    private long totalRecords;

    public OrderHistoryResponseDTO(List<Order> orders, int limit, long offset, long totalRecords) {
        this.orders = orders;
        this.limit = limit;
        this.offset = offset;
        this.totalRecords = totalRecords;
    }

    // Getters and Setters
    public List<Order> getOrders() { return orders; }
    public int getLimit() { return limit; }
    public long getOffset() { return offset; }
    public long getTotalRecords() { return totalRecords; }
}
