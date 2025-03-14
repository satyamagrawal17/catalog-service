package com.example.catalog_service.service;

import com.example.catalog_service.dto.ItemRequestDto;
import com.example.catalog_service.model.Item;
import com.example.catalog_service.model.Restaurant;
import com.example.catalog_service.repository.ItemRepository;
import com.example.catalog_service.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final RestaurantRepository restaurantRepository;
    private final ItemRepository itemRepository;

    public Item create(ItemRequestDto itemRequestDto, Long restaurantId, Long ownerId) {
        Restaurant savedRestaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new RuntimeException("restaurant id is not valid"));
        Item newItem = new Item(itemRequestDto, savedRestaurant);
        return itemRepository.save(newItem);
    }

    public List<Item> getAllItems(Long restaurantId, Long ownerId) {
        return itemRepository.findAllByRestaurant_Id(restaurantId);
    }

    public Item fetchById(Long id, Long ownerId) {
        return itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));
    }
}
