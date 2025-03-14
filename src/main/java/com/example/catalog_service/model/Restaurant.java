package com.example.catalog_service.model;

import com.example.catalog_service.dto.RestaurantRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private long owner_id;
    @Column(nullable = false)
    private String name;

    @Embedded
    private Address address;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items;

    public Restaurant(RestaurantRequestDto request, Long owner_id) {
        this.owner_id = owner_id;
        this.name = request.getName();
        this.address = new Address();
        this.address.setLongitude(request.getAddress().getLongitude());
        this.address.setLatitude(request.getAddress().getLatitude());
        this.address.setState(request.getAddress().getState());
        this.address.setCity(request.getAddress().getCity());
        this.address.setStreet(request.getAddress().getStreet());
        this.address.setPostalCode(request.getAddress().getPostalCode());
        this.items = new ArrayList<>();
    }
}
