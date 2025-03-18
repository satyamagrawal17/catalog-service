//package com.example.catalog_service.dto;
//
//import com.example.catalog_service.model.Item;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class ItemResponseDto {
//    // id, name, price, stock, restaurnantId, restaurantName
//    private Long id;
//    private String name;
//    private double price;
//    private int stock;
//    private Long restaurantId;
//    private String restaurantName;
//
//    public ItemResponseDto(Item item) {
//        this.id = item.getId();
//        this.name = item.getName();
//        this.price = item.getPrice();
//        this.stock = item.getStock();
//        this.restaurantId = item.getRestaurant().getId();
//        this.restaurantName = item.getRestaurant().getName();
//    }
//}
