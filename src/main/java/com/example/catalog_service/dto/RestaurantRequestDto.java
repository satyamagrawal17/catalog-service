package com.example.catalog_service.dto;

import lombok.Data;

@Data
public class RestaurantRequestDto {
    private String name;
    private AddressDto address;
}
