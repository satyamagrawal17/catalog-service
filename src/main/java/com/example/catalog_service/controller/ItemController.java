package com.example.catalog_service.controller;

import com.example.catalog_service.dto.ItemDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{user_id}/restaurants/{restaurant_id}/items")
@RequiredArgsConstructor
public class ItemController {

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ItemDto itemDto, @PathVariable(name = "user_id") Long owner_id, @PathVariable Long restaurant_id) {
        try {

            return new ResponseEntity<>("Item created successfully", HttpStatus.CREATED);

        }
        catch (Exception e) {
            return new ResponseEntity<>("Error creating restaurant", HttpStatus.BAD_REQUEST);

        }
    }
}
