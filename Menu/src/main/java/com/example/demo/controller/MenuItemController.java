package com.example.demo.controller;

import com.example.demo.dto.MenuResponseDTO;
import com.example.demo.model.MenuItem;
import com.example.demo.service.MenuService; // Inject Service instead
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/menu-items")
public class MenuItemController {

    private final MenuService menuService;

    public MenuItemController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Best practice for POST
    public MenuItem create(@RequestBody MenuItem item) {
        return menuService.saveMenuItem(item); // Add this method to service or keep repo call
    }

    @GetMapping("/{id}")
    public MenuItem getById(@PathVariable String id) {
        return menuService.getMenuItemById(id);
    }

    @GetMapping
    public MenuResponseDTO getAll(
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset) {

        int page = offset / limit;
        Page<MenuItem> menuPage = menuService.getAllMenuItems(PageRequest.of(page, limit));

        return new MenuResponseDTO(
                menuPage.getContent(),
                menuPage.getTotalElements()
        );
    }

    @PutMapping("/{id}")
    public MenuItem update(@PathVariable String id, @RequestBody MenuItem updatedItem) {
        // This now uses the logic you fixed in the Service earlier
        return menuService.updateMenuItem(id, updatedItem);
    }

    @DeleteMapping("/{id}")
    public Map<String, String> delete(@PathVariable String id) {
        menuService.deleteMenuItem(id);
        return Map.of(
                "message", "Menu item deleted successfully",
                "id", id
        );
    }
}