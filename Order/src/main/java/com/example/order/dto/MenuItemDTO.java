package com.example.order.dto;

import lombok.Data;

@Data
public class MenuItemDTO {
    private String id;
    private String name;
    private String description;
    private Double price;
}