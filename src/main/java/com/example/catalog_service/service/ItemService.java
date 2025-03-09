package com.example.catalog_service.service;

import com.example.catalog_service.dto.ItemDto;
import com.example.catalog_service.model.Item;
import com.example.catalog_service.model.Restaurant;
import com.example.catalog_service.repository.ItemRepository;
import com.example.catalog_service.repository.RestaurantRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final RestaurantRepository restaurantRepository;
    private final ItemRepository itemRepository;

    public void create(ItemDto itemDto, Long restaurantId, Long ownerId) {
        Restaurant savedRestaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new RuntimeException("restaurant id is not valid"));
        Item newItem = new Item(itemDto, savedRestaurant);
        itemRepository.save(newItem);
    }

}
