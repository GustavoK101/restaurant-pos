package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "menuItems")
@Data
public class MenuItem {
    @Id
    private String id;
    private String name;
    private String description;
    private Double price;
}
