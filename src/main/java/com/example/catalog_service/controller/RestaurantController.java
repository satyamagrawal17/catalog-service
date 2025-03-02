package com.example.catalog_service.controller;

import com.example.catalog_service.dto.RestaurantRequestDto;
import com.example.catalog_service.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{user_id}/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody RestaurantRequestDto restaurantRequestDto, @PathVariable(name = "user_id") Long owner_id) {
        try {
            restaurantService.create(restaurantRequestDto, owner_id);
            return new ResponseEntity<>("Restaurant created successfully", HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Error creating restaurant", HttpStatus.BAD_REQUEST);
        }
    }
}
