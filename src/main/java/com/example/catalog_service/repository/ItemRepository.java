package com.example.catalog_service.repository;

import com.example.catalog_service.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByRestaurant_Id(Long restaurantId);
}
