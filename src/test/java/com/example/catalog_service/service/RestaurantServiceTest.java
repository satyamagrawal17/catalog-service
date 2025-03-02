package com.example.catalog_service.service;

import com.example.catalog_service.dto.AddressDto;
import com.example.catalog_service.dto.RestaurantRequestDto;
import com.example.catalog_service.model.Restaurant;
import com.example.catalog_service.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
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
}