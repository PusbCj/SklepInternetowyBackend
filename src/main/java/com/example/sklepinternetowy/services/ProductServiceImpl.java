package com.example.sklepinternetowy.services;

import com.example.sklepinternetowy.exception.ProductNotFindException;
import com.example.sklepinternetowy.models.Product;
import com.example.sklepinternetowy.repositories.PhotoUrlRepository;
import com.example.sklepinternetowy.repositories.ProductRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

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
        product.setDisabled(false);
        return productRepository.save(product);

    }



    @Override
    public Page<Product> getAllProductsByCategory(Long categoryNumber, Pageable pageable, List<String> brands, Long age, BigDecimal priceLow, BigDecimal priceHigh) {
        if((brands==null || brands.size()==0) & age == null & priceLow ==null & priceHigh ==null){
            return getAllProductsByCategory(categoryNumber, pageable);
        }
        if(age ==null) age =100L;
        if(priceLow ==null) priceLow = BigDecimal.valueOf(0);
        if (priceHigh ==null) priceHigh = BigDecimal.valueOf(10000);

        if(brands==null || brands.size()==0)
            return productRepository
                    .findAllByCategory_IdAndAgeIsLessThanEqualAndPriceBetweenAndDisabledFalse(categoryNumber, age, priceLow, priceHigh, pageable);
        else
            return productRepository
                    .findAllByCategory_IdAndAgeIsLessThanEqualAndPriceBetweenAndBrandInAndDisabledFalse(categoryNumber, age, priceLow, priceHigh, brands, pageable);

    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow(()->new ProductNotFindException("Nie odnaleziono produktu"));
    }

    @Override
    public Set<String> getAllBrands(Long categoryId) {
        return productRepository.getBrandsbyCategory(categoryId);
    }


}
