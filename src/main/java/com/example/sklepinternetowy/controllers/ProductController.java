package com.example.sklepinternetowy.controllers;

import com.example.sklepinternetowy.models.Product;
import com.example.sklepinternetowy.models.ProductCategoryAge;
import com.example.sklepinternetowy.services.FileService;
import com.example.sklepinternetowy.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;


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
    public Page<Product> getAllProductsByCategory(@PathVariable Long categoryNumber,
                                                  @RequestParam(required = false) List<ProductCategoryAge> ages,
                                                  @RequestParam(required = false) BigDecimal priceLow,
                                                  @RequestParam(required = false) BigDecimal priceHigh,
                                                    @RequestParam(required =false) List<String> brands
                                                    ,Pageable pageable){
        return productService.getAllProductsByCategory(categoryNumber, pageable, brands, ages, priceLow, priceHigh);

    }

    @GetMapping("/id/{id}")
    public Product getById(@PathVariable Long id){
       return productService.getById(id);
    }



    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping
    public Product save( @RequestBody Product product){
        return productService.save(product);
    }

    @PreAuthorize("hasAuthority('Admin')")
    @PutMapping
    public Product update( @RequestBody Product product){
        return productService.update(product);
    }

    @PreAuthorize("hasAuthority('Admin')")
    @DeleteMapping("{id}}")
    public Product delete(@PathVariable Long id){
        return productService.delete(id);
    }

    @GetMapping("/brand/")
    public Set<String> getAllBrands(@RequestParam Long categoryId){
        return productService.getAllBrands(categoryId);
    }

    @GetMapping("/agecat/")
    public Set<ProductCategoryAge> getAllProductCategoryAge(){
        return productService.getAlProductCategoryAge();
    }
}
