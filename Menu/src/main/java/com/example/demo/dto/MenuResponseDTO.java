package com.example.demo.dto;

import com.example.demo.model.MenuItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class MenuResponseDTO {
    private List<MenuItem> items;
    private long totalRecords;
}
