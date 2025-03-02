package com.example.catalog_service.controller;

import com.example.catalog_service.dto.AddressDto;
import com.example.catalog_service.dto.RestaurantRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class RestaurantControllerTest {

    @InjectMocks
    private RestaurantController restaurantController;

    private RestaurantRequestDto validRestaurantRequestDto;

    @BeforeEach
    void setUp() {
        AddressDto addressDto = new AddressDto();
        addressDto.setStreet("123 Main St");
        addressDto.setCity("Springfield");
        addressDto.setState("IL");
        addressDto.setPostalCode("62701");
        addressDto.setLongitude(-89.6500);
        addressDto.setLatitude(39.7817);

        validRestaurantRequestDto = new RestaurantRequestDto();
        validRestaurantRequestDto.setName("Test Restaurant");
        validRestaurantRequestDto.setAddress(addressDto);
    }

    @Test
    void testCreateRestaurantSuccess() {
        ResponseEntity<?> response = restaurantController.create(validRestaurantRequestDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Restaurant created successfully", response.getBody());
    }


}