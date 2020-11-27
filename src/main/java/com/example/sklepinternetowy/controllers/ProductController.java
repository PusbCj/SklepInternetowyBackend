package com.example.sklepinternetowy.controllers;

import com.example.sklepinternetowy.models.Product;
import com.example.sklepinternetowy.services.FileService;
import com.example.sklepinternetowy.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {
    private final ProductService productService;
    private final FileService fileService;


    public ProductController(ProductService productService, FileService fileService) {
        this.productService = productService;
        this.fileService = fileService;
    }

    @GetMapping
    public Page<Product> getAllProducts(Pageable pageable){
        return productService.getAllProducts(pageable);
    }
    @GetMapping("/category")
    public Page<Product> getAllProductsByCategory(@PathVariable Long Categorynumber, Pageable pageable){
        return productService.getAllProductsByCategory(pageable);
    }
    @PostMapping
    public Product save(@RequestParam MultipartFile file, @RequestBody Product product){
        String filename = fileService.storeFile(file);
        product.setPhotoUrl("http://195.80.229.73:443/photo/"+filename);
        return productService.save(product);
    }

    @DeleteMapping("id")
    public Product delete(@PathVariable Long id){
        return productService.delete(id);
    }
}
