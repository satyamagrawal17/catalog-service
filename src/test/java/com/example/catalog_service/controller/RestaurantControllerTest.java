//package com.example.catalog_service.controller;
//
//import com.example.catalog_service.dto.AddressDto;
//import com.example.catalog_service.dto.RestaurantRequestDto;
//import com.example.catalog_service.model.Address;
//import com.example.catalog_service.model.Restaurant;
//import com.example.catalog_service.service.RestaurantService;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class RestaurantControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @MockitoBean
//    private RestaurantService restaurantService;
//
//
//    private RestaurantRequestDto restaurantRequestDto;
//    private Restaurant restaurant;
//    private Long ownerId;
//    private Long restaurantId;
//
//    @BeforeEach
//    void setUp() {
//        AddressDto addressDto = new AddressDto();
//        addressDto.setStreet("123 Main St");
//        addressDto.setCity("Springfield");
//        addressDto.setState("IL");
//        addressDto.setPostalCode("62701");
//        addressDto.setLongitude(-89.6500);
//        addressDto.setLatitude(39.7817);
//
//        restaurantRequestDto = new RestaurantRequestDto();
//        restaurantRequestDto.setName("Test Restaurant");
//        restaurantRequestDto.setAddress(addressDto);
//
//        restaurant = new Restaurant();
//        restaurant.setId(1L);
//        restaurant.setName("Test Restaurant");
//        Address address = new Address();
//
//        address.setStreet("123 Main St");
//        address.setCity("Springfield");
//        address.setState("IL");
//        address.setPostalCode("62701");
//        address.setLongitude(-89.6500);
//        address.setLatitude(39.7817);
//        restaurant.setAddress(address);
//
//        ownerId = 1L;
//        restaurantId = 1L;
//
//    }
//
//    @Test
//    void testCreateRestaurantSuccess() throws Exception {
//        doNothing().when(restaurantService).create(restaurantRequestDto, 1L);
//        mockMvc.perform(MockMvcRequestBuilders.post("/users/1/restaurants")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(restaurantRequestDto)))
//                .andExpect(status().isCreated())
//                .andExpect(content().string("Restaurant created successfully"));
//        verify(restaurantService).create(restaurantRequestDto, 1L);
//    }
//
//    @Test
//    void testCreateRestaurant_MissingName_ValidationError() throws Exception {
//        restaurantRequestDto.setName(""); // set address, but not name.
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/users/1/restaurants")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(restaurantRequestDto)))
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string(org.hamcrest.Matchers.containsString("Name cannot be blank"))); // Check for validation error message
//
//        verify(restaurantService, never()).create(any(RestaurantRequestDto.class), anyLong());
//    }
//
//    // Get All Restaurants
//
//    @Test
//    void testFetchAllRestaurants_Success() throws Exception {
//        Restaurant restaurant1 = new Restaurant();
//        restaurant1.setId(1L);
//        restaurant1.setName("Test Restaurant 1");
//
//        Restaurant restaurant2 = new Restaurant();
//        restaurant2.setId(2L);
//        restaurant2.setName("Test Restaurant 2");
//
//        List<Restaurant> restaurants = Arrays.asList(restaurant1, restaurant2);
//
//        when(restaurantService.fetchAll(1L)).thenReturn(restaurants);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/users/1/restaurants"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2))
//                .andExpect(jsonPath("$[0].name").value("Test Restaurant 1"))
//                .andExpect(jsonPath("$[1].name").value("Test Restaurant 2"));
//
//        verify(restaurantService, times(1)).fetchAll(1L);
//    }
//
//    @Test
//    void testFetchAllRestaurants_EmptyList() throws Exception {
//        when(restaurantService.fetchAll(1L)).thenReturn(Collections.emptyList());
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/users/1/restaurants"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(0));
//
//        verify(restaurantService, times(1)).fetchAll(1L);
//    }
//
//    // Get Restaurant By Id
//
//    @Test
//    void testFetchRestaurantById_Success() throws Exception {
//        when(restaurantService.fetchById(restaurantId, ownerId)).thenReturn(restaurant);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/users/1/restaurants/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("Test Restaurant"));
//
//        verify(restaurantService, times(1)).fetchById(restaurantId, ownerId);
//    }
//}