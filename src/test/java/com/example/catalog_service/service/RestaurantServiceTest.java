package com.example.catalog_service.service;

import com.example.catalog_service.dto.AddressDto;
import com.example.catalog_service.dto.RestaurantRequestDto;
import com.example.catalog_service.model.Restaurant;
import com.example.catalog_service.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RestaurantServiceTest {

    @Autowired
    private RestaurantService restaurantService;

    @MockitoBean
    private RestaurantRepository restaurantRepository;

    private RestaurantRequestDto restaurantRequestDto;
    private Long ownerId;

    @BeforeEach
    void setUp() {
        AddressDto addressDto = new AddressDto();
        addressDto.setStreet("123 Main St");
        addressDto.setCity("Springfield");
        addressDto.setState("IL");
        addressDto.setPostalCode("62701");
        addressDto.setLongitude(-89.6500);
        addressDto.setLatitude(39.7817);

        restaurantRequestDto = new RestaurantRequestDto();
        restaurantRequestDto.setName("Test Restaurant");
        restaurantRequestDto.setAddress(addressDto);

        ownerId = 123L;
    }

    // Tests for create method
    @Test
    void testCreateRestaurant_Success() {
        Restaurant restaurantToSave = new Restaurant(restaurantRequestDto, ownerId);
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurantToSave);

        restaurantService.create(restaurantRequestDto, ownerId);

        verify(restaurantRepository, times(1)).save(any(Restaurant.class));
    }

    @Test
    void testCreateRestaurant_RepositoryThrowsException() {
        when(restaurantRepository.save(any(Restaurant.class))).thenThrow(new RuntimeException("Simulated repository error"));

        assertThrows(RuntimeException.class, () -> restaurantService.create(restaurantRequestDto, ownerId));

        verify(restaurantRepository, times(1)).save(any(Restaurant.class));
    }

    @Test
    void testCreateRestaurant_NullRequest() {
        assertThrows(NullPointerException.class, () -> restaurantService.create(null, ownerId));
        verify(restaurantRepository, never()).save(any(Restaurant.class));
    }

    @Test
    void testCreateRestaurant_NullOwnerId() {
        assertThrows(NullPointerException.class, () -> restaurantService.create(restaurantRequestDto, null));
        verify(restaurantRepository, never()).save(any(Restaurant.class));
    }

    @Test
    void testCreateRestaurant_EmptyName() {
        restaurantRequestDto.setName("");
        when(restaurantRepository.save(any(Restaurant.class))).thenThrow(new RuntimeException("Simulated repository error"));
        assertThrows(RuntimeException.class, () -> restaurantService.create(restaurantRequestDto, ownerId));
    }

    // Tests for fetch all restaurants method
    @Test
    void testFetchAllRestaurants_Success() {
        List<Restaurant> restaurants = List.of(
                new Restaurant(restaurantRequestDto, ownerId),
                new Restaurant(restaurantRequestDto, ownerId)
        );
        when(restaurantRepository.findAll()).thenReturn(restaurants);

        List<Restaurant> result = restaurantService.fetchAll(ownerId);

        assertEquals(2, result.size());
        verify(restaurantRepository, times(1)).findAll();
    }

    @Test
    void testFetchAllRestaurants_EmptyList() {
        when(restaurantRepository.findAll()).thenReturn(Collections.emptyList());

        List<Restaurant> result = restaurantService.fetchAll(ownerId);

        assertTrue(result.isEmpty());
        verify(restaurantRepository, times(1)).findAll();
    }

    @Test
    void testFetchAllRestaurants_RepositoryThrowsException() {
        when(restaurantRepository.findAll()).thenThrow(new RuntimeException("Simulated repository error"));

        assertThrows(RuntimeException.class, () -> restaurantService.fetchAll(ownerId));

        verify(restaurantRepository, times(1)).findAll();
    }

    // Tests for fetch Restaurant by ID

    @Test
    void testFetchRestaurantById_Success() {
        Restaurant restaurant = new Restaurant(restaurantRequestDto, ownerId);
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));

        Restaurant result = restaurantService.fetchById(1L, ownerId);

        assertNotNull(result);
        assertEquals(restaurant.getName(), result.getName());
        verify(restaurantRepository, times(1)).findById(anyLong());
    }

    @Test
    void testFetchRestaurantById_NotFound() {
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> restaurantService.fetchById(1L, ownerId));

        verify(restaurantRepository, times(1)).findById(anyLong());
    }

    @Test
    void testFetchRestaurantById_RepositoryThrowsException() {
        when(restaurantRepository.findById(anyLong())).thenThrow(new RuntimeException("Simulated repository error"));

        assertThrows(RuntimeException.class, () -> restaurantService.fetchById(1L, ownerId));

        verify(restaurantRepository, times(1)).findById(anyLong());
    }
}