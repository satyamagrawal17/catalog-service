package com.example.catalog_service.service;

import com.example.catalog_service.dto.ItemDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {
    public void create(@Valid ItemDto itemDto, Long restaurantId, Long ownerId) {

    }
}
