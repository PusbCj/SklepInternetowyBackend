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

import java.math.BigDecimal;


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

    @PreAuthorize("permitAll()")
    @GetMapping("/category/{categoryNumber}")
    public Page<Product> getAllProductsByCategory(@PathVariable Long categoryNumber,@RequestParam(required = false) String brand,
                                                  @RequestParam(required = false) Long age,@RequestParam(required = false) BigDecimal priceLow,
                                                  @RequestParam(required = false) BigDecimal priceHigh,@RequestParam(required = false) Boolean desc
            ,Pageable pageable){
        return productService.getAllProductsByCategory(categoryNumber, pageable,brand,age,priceLow,priceHigh,desc);
    }

    @GetMapping("/id/{id}")
    public Product getById(@PathVariable Long id){
       return productService.getById(id);
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
