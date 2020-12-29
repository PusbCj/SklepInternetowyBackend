package com.example.sklepinternetowy.controllers;

import com.example.sklepinternetowy.models.Category;
import com.example.sklepinternetowy.repositories.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@PreAuthorize("permitAll()")
@RestController
@RequestMapping("api/v1/category")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @GetMapping
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }
    @GetMapping("{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        return optionalCategory.map(category -> new ResponseEntity<>(category, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping
    public Category save(@RequestBody Category category){
        return categoryRepository.save(category);
    }


    @PreAuthorize("hasAuthority('Admin')")
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        categoryRepository.deleteById(id);
    }

}
