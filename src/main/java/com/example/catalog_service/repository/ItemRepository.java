package com.example.catalog_service.repository;

import com.example.catalog_service.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
