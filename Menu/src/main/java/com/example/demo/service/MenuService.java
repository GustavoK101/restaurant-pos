package com.example.demo.service;

import com.example.demo.model.MenuItem;
import com.example.demo.repository.MenuItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MenuService {

    private final MenuItemRepository repository;

    // Constructor injection for the repository
    public MenuService(MenuItemRepository repository) {
        this.repository = repository;
    }

    // Fixes the error: menuService.saveMenuItem(item)
    public MenuItem saveMenuItem(MenuItem item) {
        return repository.save(item);
    }

    // Fixes the error: menuService.getMenuItemById(id)
    public MenuItem getMenuItemById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found with id: " + id));
    }

    // Fixes the error: menuService.getAllMenuItems(...)
    public Page<MenuItem> getAllMenuItems(Pageable pageable) {
        return repository.findAll(pageable);
    }

    // Existing update logic
    public MenuItem updateMenuItem(String id, MenuItem updatedDetails) {
        return repository.findById(id).map(item -> {
            item.setName(updatedDetails.getName());
            item.setDescription(updatedDetails.getDescription());
            item.setPrice(updatedDetails.getPrice());
            return repository.save(item);
        }).orElseThrow(() -> new RuntimeException("Menu item not found"));
    }

    // Method for the delete endpoint
    public void deleteMenuItem(String id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Menu item not found with id: " + id);
        }
        repository.deleteById(id);
    }
}