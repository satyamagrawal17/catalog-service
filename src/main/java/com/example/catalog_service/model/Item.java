package com.example.catalog_service.model;

import com.example.catalog_service.dto.ItemDto;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private double price;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public Item(ItemDto itemDto, Restaurant restaurant) {
        this.name = itemDto.getName();
        this.price = itemDto.getPrice();
        this.restaurant = restaurant;
    }
}
