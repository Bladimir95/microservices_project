package com.bladimircrisosto.product_service.repository;

import com.bladimircrisosto.product_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    List<Product> findByCategory(String category);

    List<Product> findByStockQuantityGreaterThan(int quantity);

    boolean existsByName(String name);
}