package com.example.catalog_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/{user_id}/restaurants/{restaurant_id}/items")
@RequiredArgsConstructor
public class MenuItemsController {
}
