package com.example.catalog_service.controller;

import com.example.catalog_service.dto.RestaurantRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @PostMapping
    public ResponseEntity<?> create(@RequestBody RestaurantRequestDto restaurantRequestDto) {
        try {
            return new ResponseEntity<>("Restaurant created successfully", HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Error creating restaurant", HttpStatus.BAD_REQUEST);
        }
    }
}
