package com.example.sklepinternetowy.controllers;

import com.example.sklepinternetowy.models.Product;
import com.example.sklepinternetowy.services.FileService;
import com.example.sklepinternetowy.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@PreAuthorize("permitAll()")
@RestController
@RequestMapping("api/v1/product")
public class ProductController {
    private final ProductService productService;
    private final FileService fileService;


    public ProductController(ProductService productService, FileService fileService) {
        this.productService = productService;
        this.fileService = fileService;
    }

    @PreAuthorize("permitAll()")
    @GetMapping
    public Page<Product> getAllProducts(Pageable pageable){
        return productService.getAllProducts(pageable);
    }

    @PreAuthorize("hasRole('User')")
    @GetMapping("/category/{categoryNumber}")
    public Page<Product> getAllProductsByCategory(@PathVariable Long categoryNumber, Pageable pageable){
        return productService.getAllProductsByCategory(categoryNumber, pageable);
    }


    @PostMapping
    public Product save( @RequestBody Product product){
        return productService.save(product);
    }

    @DeleteMapping("{id}}")
    public Product delete(@PathVariable Long id){
        return productService.delete(id);
    }
}
