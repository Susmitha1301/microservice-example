package com.example.product_service.repository;

import com.example.product_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
    public interface ProductRepository extends JpaRepository<Product, Long>{
    @Query(value = "SELECT * FROM product WHERE price > ?1", nativeQuery = true)
    List<Product> findProductsByPriceGreaterThan(Double price);
    }

