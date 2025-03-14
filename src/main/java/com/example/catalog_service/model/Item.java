package com.example.catalog_service.model;

import com.example.catalog_service.dto.ItemRequestDto;
import jakarta.persistence.*;
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
    @Column(nullable = false)
    private int stock;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public Item(ItemRequestDto itemRequestDto, Restaurant restaurant) {
        this.name = itemRequestDto.getName();
        this.price = itemRequestDto.getPrice();
        this.stock = itemRequestDto.getStock();
        this.restaurant = restaurant;
    }
}
