package com.example.catalog_service.service;


import com.example.catalog_service.dto.RestaurantRequestDto;
import com.example.catalog_service.model.Restaurant;
import com.example.catalog_service.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public void create(RestaurantRequestDto request, Long owner_id) {
        Restaurant newRestaurant = new Restaurant(request, owner_id);
        restaurantRepository.save(newRestaurant);
    }

    public List<Restaurant> fetchAll(Long owner_id) {
        return restaurantRepository.findAll();
    }

    public Restaurant fetchById(Long restaurantId, Long ownerId) {
        return restaurantRepository.findById(restaurantId).orElseThrow(() -> new RuntimeException("Restaurant not found"));
    }
}
