package com.example.sklepinternetowy.services;

import com.example.sklepinternetowy.models.Product;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;

public interface ProductService {
    Page<Product> getAllProductsByCategory(Pageable pageable);

    Page<Product> getAllProducts(Pageable pageable);

    Product delete(Long id);

    Product save(Product product);
}
