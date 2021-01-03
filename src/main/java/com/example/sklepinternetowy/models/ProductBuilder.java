package com.example.sklepinternetowy.models;

import java.math.BigDecimal;
import java.util.List;

public final class ProductBuilder {
    private Long id;
    private String name;
    private Category category;
    private String description;
    private List<PhotoUrl> photoUrl;
    private Long quantity;
    private Long quantityAvailable;
    private String brand;
    private List<ProductCategoryAge> productCategoryAgeList;
    private Boolean disabled;
    private BigDecimal price;

    private ProductBuilder() {
    }

    public static ProductBuilder aProduct() {
        return new ProductBuilder();
    }

    public ProductBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public ProductBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder withCategory(Category category) {
        this.category = category;
        return this;
    }

    public ProductBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public ProductBuilder withPhotoUrl(List<PhotoUrl> photoUrl) {
        this.photoUrl = photoUrl;
        return this;
    }

    public ProductBuilder withQuantity(Long quantity) {
        this.quantity = quantity;
        return this;
    }

    public ProductBuilder withQuantityAvailable(Long quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
        return this;
    }

    public ProductBuilder withBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public ProductBuilder withProductCategoryAgeList(List<ProductCategoryAge> productCategoryAgeList) {
        this.productCategoryAgeList = productCategoryAgeList;
        return this;
    }

    public ProductBuilder withDisabled(Boolean disabled) {
        this.disabled = disabled;
        return this;
    }

    public ProductBuilder withPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Product build() {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setCategory(category);
        product.setDescription(description);
        product.setPhotoUrl(photoUrl);
        product.setQuantity(quantity);
        product.setQuantityAvailable(quantityAvailable);
        product.setBrand(brand);
        product.setProductCategoryAgeList(productCategoryAgeList);
        product.setDisabled(disabled);
        product.setPrice(price);
        return product;
    }
}
