package com.example.sklepinternetowy.repositories;

import com.example.sklepinternetowy.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
