package com.example.sklepinternetowy.models;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private Category category;

    @Length(max=5000)
    @Column(length = 5000)
    private String description;
    @OneToMany
    private List<PhotoUrl> photoUrl;
    private Long quantity;
    private Long quantityAvailable;
    private String brand;
    @ManyToMany
    private List<ProductCategoryAge> productCategoryAgeList;
    private Boolean disabled;

    private BigDecimal price;
    public Product() {
        photoUrl= new ArrayList<>();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(Long quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }


    public void setPhotoUrl(List<PhotoUrl> photoUrl) {
        this.photoUrl = photoUrl;
    }

    public List<PhotoUrl> getPhotoUrl() {
        return photoUrl;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public List<ProductCategoryAge> getProductCategoryAgeList() {
        return productCategoryAgeList;
    }

    public void setProductCategoryAgeList(List<ProductCategoryAge> productCategoryAgeList) {
        this.productCategoryAgeList = productCategoryAgeList;
    }
}
