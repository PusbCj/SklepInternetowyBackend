package com.example.sklepinternetowy.services;

import com.example.sklepinternetowy.exception.ProductNotFindException;
import com.example.sklepinternetowy.models.Product;
import com.example.sklepinternetowy.models.ProductCategoryAge;
import com.example.sklepinternetowy.repositories.PhotoUrlRepository;
import com.example.sklepinternetowy.repositories.ProductCategoryAgeRepository;
import com.example.sklepinternetowy.repositories.ProductRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final PhotoUrlRepository photoUrlRepository;
    private final ProductCategoryAgeRepository productCategoryAgeRepository;

    public ProductServiceImpl(ProductRepository productRepository, PhotoUrlRepository photoUrlRepository, ProductCategoryAgeRepository productCategoryAgeRepository) {
        this.productRepository = productRepository;
        this.photoUrlRepository = photoUrlRepository;

        this.productCategoryAgeRepository = productCategoryAgeRepository;
    }


    @Override
    @Cacheable(cacheNames = "AllProductByCategory")
    public Page<Product> getAllProductsByCategory(Long categoryNumber, Pageable pageable) {
        return productRepository.findAllByCategory_IdOrderByPriceAsc(categoryNumber, pageable);
    }

    @Override
    @Cacheable(cacheNames = "AllProductByCategoryDesc")
    public Page<Product> getAllProductsByCategoryDesc(Long categoryNumber, Pageable pageable) {
        return productRepository.findAllByCategory_IdOrderByPriceDesc(categoryNumber, pageable);
    }

    @Override
    @Cacheable(cacheNames = "AllProductByCategoryWithoutParam")
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
        product.setId(null);
        return productRepository.save(product);

    }


    @Override
    @Cacheable(cacheNames = "AllProductByCategoryMultiParam")
    public Page<Product> getAllProductsByCategory(Long categoryNumber, Pageable pageable, List<String> brands, List<ProductCategoryAge> age, BigDecimal priceLow, BigDecimal priceHigh) {
        if((brands==null || brands.size()==0) & age == null & priceLow ==null & priceHigh ==null){
            return getAllProductsByCategory(categoryNumber, pageable);
        }
        if(age ==null) age =productCategoryAgeRepository.findAll();
        if(priceLow ==null) priceLow = BigDecimal.valueOf(0);
        if (priceHigh ==null) priceHigh = BigDecimal.valueOf(10000);

        if(brands==null || brands.size()==0)
            return productRepository
                    .findDistinctByCategory_IdAndProductCategoryAgeListInAndPriceBetweenAndDisabledFalse(categoryNumber, age, priceLow, priceHigh, pageable);
        else
            return productRepository
                    .findDistinctByCategory_IdAndProductCategoryAgeListInAndPriceBetweenAndDisabledIsFalseAndBrandIsIn(categoryNumber, age, priceLow, priceHigh, brands, pageable);

    }

    @Override
    @Cacheable(cacheNames = "GetOneProductById")
    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow(()->new ProductNotFindException("Nie odnaleziono produktu"));
    }

    @Override
    @Cacheable(cacheNames = "GetAllBrandsByCategory")
    public Set<String> getAllBrands(Long categoryId) {
        if(categoryId == null){
            return getAllBrands();
        }else {
            return productRepository.getBrandsbyCategory(categoryId);
        }
    }

    @Override
    @Cacheable(cacheNames = "GetAllCategoryAge")
    public Set<ProductCategoryAge> getAlProductCategoryAge() {
        return new HashSet<>( productCategoryAgeRepository.findAll());
    }

    @Override
    public Product update(Product product) {
        photoUrlRepository.saveAll(product.getPhotoUrl());
        return productRepository.save(product);
    }

    @Override
    @Cacheable(cacheNames = "GetAllBrands")
    public Set<String> getAllBrands() {
        return productRepository.getAllBrands();
    }


}
