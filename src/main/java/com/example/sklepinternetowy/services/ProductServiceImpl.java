package com.example.sklepinternetowy.services;

import com.example.sklepinternetowy.exception.ProductNotFindException;
import com.example.sklepinternetowy.models.Product;
import com.example.sklepinternetowy.repositories.PhotoUrlRepository;
import com.example.sklepinternetowy.repositories.ProductRepository;
import com.google.common.base.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final PhotoUrlRepository photoUrlRepository;

    public ProductServiceImpl(ProductRepository productRepository, PhotoUrlRepository photoUrlRepository) {
        this.productRepository = productRepository;
        this.photoUrlRepository = photoUrlRepository;
    }


    @Override
    public Page<Product> getAllProductsByCategory(Long categoryNumber, Pageable pageable) {
        return productRepository.findAllByCategory_IdOrderByPriceAsc(categoryNumber, pageable);
    }
    @Override
    public Page<Product> getAllProductsByCategoryDesc(Long categoryNumber, Pageable pageable) {
        return productRepository.findAllByCategory_IdOrderByPriceDesc(categoryNumber, pageable);
    }

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product delete(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFindException("Taki produkt nie istnieje w bazie"));
        productRepository.delete(product);
        return product;
    }

    @Override
    public Product save(Product product) {
        photoUrlRepository.saveAll(product.getPhotoUrl());
        return productRepository.save(product);

    }

    @Override
    public Page<Product> getAllProductsByCategory(Long categoryNumber, Pageable pageable, String brand, Long age, BigDecimal priceLow, BigDecimal priceHigh, Boolean desc) {
       if(desc==null) desc=false;

        if( !false )
        return getProductsAsc(categoryNumber, pageable, brand, age, priceLow, priceHigh);
        else
            return getProductsDesc(categoryNumber, pageable, brand, age, priceLow, priceHigh);
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow(()->new ProductNotFindException("Nie odnaleziono produktu"));
    }

    private Page<Product> getProductsAsc(Long categoryNumber, Pageable pageable, String brand, Long age, BigDecimal priceLow, BigDecimal priceHigh) {
        if(Strings.isNullOrEmpty(brand) & age == null & priceLow ==null & priceHigh ==null){
            return getAllProductsByCategory(categoryNumber, pageable);
        }
        if(age ==null) age =100L;
        if(priceLow ==null) priceLow = BigDecimal.valueOf(0);
        if (priceHigh ==null) priceHigh = BigDecimal.valueOf(10000);

        if(Strings.isNullOrEmpty(brand))
            return productRepository
                    .findAllByCategory_IdAndAgeIsLessThanEqualAndPriceBetweenOrderByPriceAsc(categoryNumber, age, priceLow, priceHigh, pageable);
        else
            return productRepository
                    .findAllByCategory_IdAndAgeIsLessThanEqualAndPriceBetweenAndBrandIsContainingOrderByPriceAsc(categoryNumber, age, priceLow, priceHigh, brand, pageable);
    }


    private Page<Product> getProductsDesc(Long categoryNumber, Pageable pageable, String brand, Long age, BigDecimal priceLow, BigDecimal priceHigh) {
        if(Strings.isNullOrEmpty(brand) & age == null & priceLow ==null & priceHigh ==null){
            return getAllProductsByCategoryDesc(categoryNumber, pageable);
        }
        if(age ==null) age =100l;
        if(priceLow ==null) priceLow = BigDecimal.valueOf(0);
        if (priceHigh ==null) priceHigh = BigDecimal.valueOf(10000);

        if(Strings.isNullOrEmpty(brand))
            return productRepository
                    .findAllByCategory_IdAndAgeIsLessThanEqualAndPriceBetweenOrderByPriceDesc(categoryNumber, age, priceLow, priceHigh, pageable);
        else
            return productRepository
                    .findAllByCategory_IdAndAgeIsLessThanEqualAndPriceBetweenAndBrandIsContainingOrderByPriceDesc(categoryNumber, age, priceLow, priceHigh, brand, pageable);
    }
}
