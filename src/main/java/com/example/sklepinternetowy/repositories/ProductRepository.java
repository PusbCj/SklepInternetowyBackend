package com.example.sklepinternetowy.repositories;

import com.example.sklepinternetowy.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    Page<Product> findAllByCategory_IdOrderByPriceAsc(Long category_id, Pageable pageable);
    Page<Product> findAllByCategory_IdOrderByPriceDesc(Long category_id, Pageable pageable);

    Page<Product> findAllByCategory_IdAndAgeIsLessThanEqualAndPriceBetweenAndDisabledFalse(Long category_id,
                                                                           Long age, BigDecimal price, BigDecimal price2, Pageable pageable);

    Page<Product> findAllByCategory_IdAndAgeIsLessThanEqualAndPriceBetweenAndBrandInAndDisabledFalse(Long category_id,
                                                                                                                Long age, BigDecimal price, BigDecimal price2,List<String> brands, Pageable pageable);

    @Query("SELECT DISTINCT u.brand FROM Product u WHERE u.category.id = ?1")
    Set<String> getBrandsbyCategory(Long categoryId);

    @Query("SELECT DISTINCT u.brand FROM Product u WHERE u.disabled = false AND u.category.id = ?1")
    Set<String> getBrandsEnabledByCategory(Long categoryId);

    Page<Product> findProductsByBrandIn(Collection<String> brand, Pageable pageable);


}
