package com.example.sklepinternetowy.services;

import com.example.sklepinternetowy.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;


public interface ProductService {
    Page<Product> getAllProductsByCategory(Long categoryNumber, Pageable pageable);
    Page<Product> getAllProductsByCategoryDesc(Long categoryNumber, Pageable pageable);

    Page<Product> getAllProducts(Pageable pageable);

    Product delete(Long id);

    Product save(Product product);



    Page<Product> getAllProductsByCategory(Long categoryNumber, Pageable pageable, String brand, Long age, BigDecimal priceLow, BigDecimal priceHigh, Boolean desc);

    Product getById(Long id);
}
