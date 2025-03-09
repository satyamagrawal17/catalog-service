package com.example.catalog_service.service;

import com.example.catalog_service.dto.ItemDto;
import com.example.catalog_service.model.Item;
import com.example.catalog_service.model.Restaurant;
import com.example.catalog_service.repository.ItemRepository;
import com.example.catalog_service.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    private RestaurantRepository restaurantRepository;
    private ItemRepository itemRepository;

    private ItemDto itemDto;
    private Long restaurantId;
    private Long ownerId;

    @BeforeEach
    void setUp() {
        restaurantRepository = Mockito.mock(RestaurantRepository.class);
        itemRepository = Mockito.mock(ItemRepository.class);

        itemService = new ItemService(restaurantRepository, itemRepository);

        itemDto = new ItemDto();
        itemDto.setName("Test Item");
        itemDto.setPrice(10.0);

        restaurantId = 1L;
        ownerId = 1L;
    }

    @Test
    void testCreateItem_Success() {
        Restaurant restaurant = new Restaurant();
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));

        itemService.create(itemDto, restaurantId, ownerId);

        verify(restaurantRepository, times(1)).findById(restaurantId);
        verify(itemRepository, times(1)).save(any(Item.class));
    }

    @Test
    void testCreateItem_RestaurantNotFound() {
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> itemService.create(itemDto, restaurantId, ownerId));

        verify(restaurantRepository, times(1)).findById(restaurantId);
        verify(itemRepository, never()).save(any(Item.class));
    }

    @Test
    void testCreateItem_RepositoryThrowsException() {
        when(restaurantRepository.findById(restaurantId)).thenThrow(new RuntimeException("Simulated repository error"));

        assertThrows(RuntimeException.class, () -> itemService.create(itemDto, restaurantId, ownerId));

        verify(restaurantRepository, times(1)).findById(restaurantId);
        verify(itemRepository, never()).save(any(Item.class));
    }
}