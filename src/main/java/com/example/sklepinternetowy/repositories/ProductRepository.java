package com.example.sklepinternetowy.repositories;

import com.example.sklepinternetowy.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    Page<Product> findAllByCategory_Id(Long category_id, Pageable pageable);
}
