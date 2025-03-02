package com.example.catalog_service.dto;

import lombok.Data;

@Data
public class AddressDto {
    private String street; // Optional: Add street, city, etc., if needed
    private String city;
    private String state;
    private String postalCode;
    private double longitude;
    private double latitude;

}
