package com.example.sklepinternetowy.repositories;

import com.example.sklepinternetowy.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    Page<Product> findAllByCategory_IdOrderByPriceAsc(Long category_id, Pageable pageable);
    Page<Product> findAllByCategory_IdOrderByPriceDesc(Long category_id, Pageable pageable);

    Page<Product> findAllByCategory_IdAndAgeIsLessThanEqualAndPriceBetweenOrderByPriceAsc(Long category_id,
                                                                           Long age, BigDecimal price, BigDecimal price2, Pageable pageable);
    Page<Product> findAllByCategory_IdAndAgeIsLessThanEqualAndPriceBetweenOrderByPriceDesc(Long category_id,
                                                                                          Long age, BigDecimal price, BigDecimal price2, Pageable pageable);
    Page<Product> findAllByCategory_IdAndAgeIsLessThanEqualAndPriceBetweenAndBrandIsContainingOrderByPriceDesc(Long category_id,
                                                                                               Long age, BigDecimal price, BigDecimal price2, String brand, Pageable pageable);
    Page<Product> findAllByCategory_IdAndAgeIsLessThanEqualAndPriceBetweenAndBrandIsContainingOrderByPriceAsc(Long category_id,
                                                                                                               Long age, BigDecimal price, BigDecimal price2, String brand, Pageable pageable);
}
