package com.example.catalog_service.model;

import com.example.catalog_service.dto.RestaurantRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;

    @Embedded
    private Address address;

    public Restaurant(RestaurantRequestDto request) {
        this.name = request.getName();
        this.address.setLongitude(request.getAddress().getLongitude());
        this.address.setLatitude(request.getAddress().getLatitude());
        this.address.setState(request.getAddress().getState());
        this.address.setCity(request.getAddress().getCity());
        this.address.setStreet(request.getAddress().getStreet());
        this.address.setPostalCode(request.getAddress().getPostalCode());
    }
}
