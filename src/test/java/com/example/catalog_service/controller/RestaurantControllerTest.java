package com.example.catalog_service.controller;

import com.example.catalog_service.dto.AddressDto;
import com.example.catalog_service.dto.RestaurantRequestDto;
import com.example.catalog_service.service.RestaurantService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RestaurantControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    private RestaurantService restaurantService;


    private RestaurantRequestDto restaurantRequestDto;

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
    }

    @Test
    void testCreateRestaurantSuccess() throws Exception {
        doNothing().when(restaurantService).create(restaurantRequestDto, 1L);
        mockMvc.perform(MockMvcRequestBuilders.post("/users/1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restaurantRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Restaurant created successfully"));
        verify(restaurantService).create(restaurantRequestDto, 1L);
    }

    @Test
    void testCreateRestaurant_MissingName_ValidationError() throws Exception {
        restaurantRequestDto.setName(""); // set address, but not name.

        mockMvc.perform(MockMvcRequestBuilders.post("/users/1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restaurantRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Name cannot be blank"))); // Check for validation error message

        verify(restaurantService, never()).create(any(RestaurantRequestDto.class), anyLong());
    }


}