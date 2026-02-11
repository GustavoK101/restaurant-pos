package com.example.demo.dto;

import com.example.demo.model.MenuItem;
import java.util.List;


public class MenuResponseDTO {
    private List<MenuItem> items;
    private long totalRecords;

    public MenuResponseDTO(List<MenuItem> items, long totalRecords) {
        this.items = items;
        this.totalRecords = totalRecords;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }
}
