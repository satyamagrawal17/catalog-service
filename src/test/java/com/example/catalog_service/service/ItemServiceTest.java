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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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

    @Test
    void testGetAllItemsByRestaurantId_Success() {
        Restaurant restaurant = new Restaurant();
        List<ItemDto> itemDtoList = Arrays.asList(
                new ItemDto("Item 1", 10.0),
                new ItemDto("Item 2", 20.0)
        );
        Item item1 = new Item(itemDtoList.getFirst(), restaurant);
        Item item2 = new Item(itemDtoList.get(1), restaurant);
        List<Item> itemList = Arrays.asList(item1, item2);

        when(itemRepository.findAllByRestaurant_Id(restaurantId)).thenReturn(itemList);

        List<ItemDto> result = itemService.getAllItems(restaurantId, ownerId);

        assertEquals(2, result.size());
        assertEquals("Item 1", result.get(0).getName());
        assertEquals(10.0, result.get(0).getPrice());
        assertEquals("Item 2", result.get(1).getName());
        assertEquals(20.0, result.get(1).getPrice());

        verify(itemRepository, times(1)).findAllByRestaurant_Id(restaurantId);
    }

    @Test
    void testGetAllItemsByRestaurantId_EmptyList() {
        when(itemRepository.findAllByRestaurant_Id(restaurantId)).thenReturn(Collections.emptyList());

        List<ItemDto> result = itemService.getAllItems(restaurantId, ownerId);

        assertTrue(result.isEmpty());

        verify(itemRepository, times(1)).findAllByRestaurant_Id(restaurantId);
    }

    @Test
    void testGetAllItemsByRestaurantId_RepositoryThrowsException() {
        when(itemRepository.findAllByRestaurant_Id(restaurantId)).thenThrow(new RuntimeException("Simulated repository error"));

        assertThrows(RuntimeException.class, () -> itemService.getAllItems(restaurantId, ownerId));

        verify(itemRepository, times(1)).findAllByRestaurant_Id(restaurantId);
    }
}