package com.example.sklepinternetowy.services;

import com.example.sklepinternetowy.models.Product;
import com.example.sklepinternetowy.models.ProductCategoryAge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;


public interface ProductService {
    Page<Product> getAllProductsByCategory(Long categoryNumber, Pageable pageable);
    Page<Product> getAllProductsByCategoryDesc(Long categoryNumber, Pageable pageable);

    Page<Product> getAllProducts(Pageable pageable);

    Product delete(Long id);

    Product save(Product product);



    Page<Product> getAllProductsByCategory(Long categoryNumber, Pageable pageable, List<String> brands, List<ProductCategoryAge> age, BigDecimal priceLow, BigDecimal priceHigh);

    Product getById(Long id);


    Set<String> getAllBrands(Long categoryId);

    Set<ProductCategoryAge> getAlProductCategoryAge();

    Product update(Product product);
}
