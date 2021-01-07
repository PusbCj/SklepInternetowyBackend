package com.example.sklepinternetowy.repositories;

import com.example.sklepinternetowy.models.Product;
import com.example.sklepinternetowy.models.ProductCategoryAge;
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

    Page<Product> findDistinctByCategory_IdAndProductCategoryAgeListInAndPriceBetweenAndDisabledFalse(Long category_id,
                                                                                                 List<ProductCategoryAge> age, BigDecimal price, BigDecimal price2, Pageable pageable);


    @Query("SELECT DISTINCT u.brand FROM Product u WHERE u.category.id = ?1")
    Set<String> getBrandsbyCategory(Long categoryId);

    @Query("SELECT DISTINCT u.brand FROM Product u WHERE u.disabled = false AND u.category.id = ?1")
    Set<String> getBrandsEnabledByCategory(Long categoryId);

    @Query("SELECT DISTINCT u.brand FROM Product u")
    Set<String> getAllBrands();


    Page<Product> findDistinctByCategory_IdAndProductCategoryAgeListInAndPriceBetweenAndDisabledIsFalseAndBrandIsIn(Long category_id, List<ProductCategoryAge> age, BigDecimal price, BigDecimal price2, Collection<String> brand, Pageable pageable);




}
