package com.example.catalog_service.controller;

import com.example.catalog_service.dto.RestaurantRequestDto;
import com.example.catalog_service.model.Restaurant;
import com.example.catalog_service.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.List;

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

    @GetMapping
    public ResponseEntity<?> fetchAll(@PathVariable(name = "user_id") Long owner_id) {
        try {
            List<Restaurant> restaurants = restaurantService.fetchAll(owner_id);
            return new ResponseEntity<>(restaurants, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Error fetching restaurants", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{restaurant_id}")
    public ResponseEntity<?> fetchById(@PathVariable(name = "user_id") Long owner_id, @PathVariable Long restaurant_id) {
        try {
            Restaurant restaurantById = restaurantService.fetchById(restaurant_id, owner_id);
            return new ResponseEntity<>(restaurantById, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Error fetching restaurant by " + restaurant_id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
