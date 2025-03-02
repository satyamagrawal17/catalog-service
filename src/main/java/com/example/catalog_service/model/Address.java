package com.example.catalog_service.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String street; // Optional: Add street, city, etc., if needed
    private String city;
    private String state;
    private String postalCode;
    private double longitude;
    private double latitude;
}
