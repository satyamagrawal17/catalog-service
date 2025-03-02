package com.example.catalog_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RestaurantRequestDto {
    @NotBlank(message = "Name cannot be blank")
    @NotNull
    private String name;
    private AddressDto address;
}
