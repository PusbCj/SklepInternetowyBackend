package com.example.sklepinternetowy.services;

import com.example.sklepinternetowy.exception.ProductNotFindException;
import com.example.sklepinternetowy.models.Product;
import com.example.sklepinternetowy.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public Page<Product> getAllProductsByCategory(Long categoryNumber, Pageable pageable) {
        return productRepository.findAllByCategory_Id(categoryNumber, pageable);
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
        Product product1 = productRepository.save(product);
        return product1;
    }
}
