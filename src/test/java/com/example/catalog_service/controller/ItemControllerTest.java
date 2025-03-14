//package com.example.catalog_service.controller;
//
//import com.example.catalog_service.dto.ItemDto;
//import com.example.catalog_service.service.ItemService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class ItemControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockitoBean
//    private ItemService itemService;
//
//    private ItemDto itemDto;
//    private Long ownerId;
//    private Long restaurantId;
//
//    @BeforeEach
//    void setUp() {
//        itemDto = new ItemDto();
//        itemDto.setName("Test Item");
//        itemDto.setPrice(10.0);
//
//        ownerId = 1L;
//        restaurantId = 1L;
//    }
//
//    @Test
//    void testCreateItemSuccess() throws Exception {
//        doNothing().when(itemService).create(itemDto, restaurantId, ownerId);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/users/1/restaurants/1/items")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(itemDto)))
//                .andExpect(status().isCreated())
//                .andExpect(content().string("Item created successfully"));
//
//        verify(itemService, times(1)).create(itemDto, restaurantId, ownerId);
//    }
//
//    @Test
//    void testCreateItem_MissingName_ValidationError() throws Exception {
//        itemDto.setName("");
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/users/1/restaurants/1/items")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(itemDto)))
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string(org.hamcrest.Matchers.containsString("Name cannot be blank")));
//
//        verify(itemService, never()).create(any(ItemDto.class), anyLong(), anyLong());
//    }
//
//    @Test
//    void testGetAllItemsByRestaurantId_Success() throws Exception {
//        List<ItemDto> itemList = Arrays.asList(
//                new ItemDto("Item 1", 10.0),
//                new ItemDto("Item 2", 20.0)
//        );
//
//        when(itemService.getAllItems(restaurantId, ownerId)).thenReturn(itemList);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/users/1/restaurants/1/items")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].name").value("Item 1"))
//                .andExpect(jsonPath("$[0].price").value(10.0))
//                .andExpect(jsonPath("$[1].name").value("Item 2"))
//                .andExpect(jsonPath("$[1].price").value(20.0));
//
//        verify(itemService, times(1)).getAllItems(restaurantId, ownerId);
//    }
//
//    @Test
//    void testGetAllItemsByRestaurantId_Error() throws Exception {
//        when(itemService.getAllItems(restaurantId, ownerId)).thenThrow(new RuntimeException("Error getting items"));
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/users/1/restaurants/1/items")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string("Error getting items"));
//
//        verify(itemService, times(1)).getAllItems(restaurantId, ownerId);
//    }
//
//}