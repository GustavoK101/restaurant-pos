package com.example.demo.controller;

import com.example.demo.dto.MenuResponseDTO;
import com.example.demo.model.MenuItem;
import com.example.demo.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/menu-items")
@RequiredArgsConstructor
public class MenuItemController {
    private final MenuItemRepository repository;

    @PostMapping
    public MenuItem create(@RequestBody MenuItem item) {
        return repository.save(item);
    }

    @GetMapping("/{id}")
    public MenuItem getById(@PathVariable String id) {
        return repository.findById(id).orElseThrow();
    }

    @GetMapping
    public MenuResponseDTO getAll(
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset) {

        // Calculate page index from offset
        int page = offset / limit;

        PageRequest pageRequest = PageRequest.of(page, limit);
        Page<MenuItem> menuPage = repository.findAll(pageRequest);

        return new MenuResponseDTO(
                menuPage.getContent(),
                menuPage.getTotalElements()
        );
    }
    @PutMapping("/{id}")
    public MenuItem update(@PathVariable String id, @RequestBody MenuItem updatedItem) {
        return repository.findById(id)
                .map(item -> {
                    item.setName(updatedItem.getName());
                    item.setDescription(updatedItem.getDescription());
                    item.setPrice(updatedItem.getPrice());
                    return repository.save(item);
                }).orElseThrow(() -> new RuntimeException("Item not found"));
    }

    @DeleteMapping("/{id}")
    public Map<String, String> delete(@PathVariable String id) {
        repository.deleteById(id);
        return Map.of(
                "message", "Menu item deleted successfully",
                "id", id
        );
    }
}
