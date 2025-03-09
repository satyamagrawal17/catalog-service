package com.example.catalog_service.controller;

import com.example.catalog_service.dto.ItemDto;
import com.example.catalog_service.service.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ItemService itemService;

    private ItemDto itemDto;
    private Long ownerId;
    private Long restaurantId;

    @BeforeEach
    void setUp() {
        itemDto = new ItemDto();
        itemDto.setName("Test Item");
        itemDto.setPrice(10.0);

        ownerId = 1L;
        restaurantId = 1L;
    }

    @Test
    void testCreateItemSuccess() throws Exception {
        doNothing().when(itemService).create(itemDto, restaurantId, ownerId);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/1/restaurants/1/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Item created successfully"));

        verify(itemService, times(1)).create(itemDto, restaurantId, ownerId);
    }

    @Test
    void testCreateItem_MissingName_ValidationError() throws Exception {
        itemDto.setName("");

        mockMvc.perform(MockMvcRequestBuilders.post("/users/1/restaurants/1/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Name cannot be blank")));

        verify(itemService, never()).create(any(ItemDto.class), anyLong(), anyLong());
    }
}