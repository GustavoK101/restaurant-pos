package com.example.order.client;

import com.example.order.dto.MenuItemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "menu-service", url = "${menu.service.url}")
public interface MenuClient {

    @GetMapping("/menu-items/{id}")
    MenuItemDTO getMenuItemById(@PathVariable("id") String id);
}
