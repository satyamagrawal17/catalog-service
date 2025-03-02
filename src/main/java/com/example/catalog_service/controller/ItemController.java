package com.example.catalog_service.controller;

import com.example.catalog_service.dto.ItemDto;
import com.example.catalog_service.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{user_id}/restaurants/{restaurant_id}/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ItemDto itemDto, @PathVariable(name = "user_id") Long owner_id, @PathVariable Long restaurant_id) {
        try {
            itemService.create(itemDto, restaurant_id, owner_id);
            return new ResponseEntity<>("Item created successfully", HttpStatus.CREATED);

        }
        catch (Exception e) {
            return new ResponseEntity<>("Error creating restaurant", HttpStatus.BAD_REQUEST);

        }
    }
}
