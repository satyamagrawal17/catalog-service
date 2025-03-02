package com.example.catalog_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemDto {
    @NotBlank(message = "Name cannot be blank")
    @NotNull
    private String name;
    @NotBlank(message = "Amount cannot be blank")
    @NotNull
    @Min(value = 0, message = "Amount cannot be negative")
    private double amount;
}
