package com.example.sklepinternetowy.repositories;

import com.example.sklepinternetowy.models.Category;
import com.example.sklepinternetowy.models.Product;
import com.example.sklepinternetowy.models.ProductCategoryAge;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryTest {


    private final ProductRepository underTestRepository;

    private final ProductCategoryAgeRepository productCategoryAgeRepository;


    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductRepositoryTest(ProductRepository underTestRepository, ProductCategoryAgeRepository productCategoryAgeRepository, CategoryRepository categoryRepository) {
        this.underTestRepository = underTestRepository;
        this.productCategoryAgeRepository = productCategoryAgeRepository;
        this.categoryRepository = categoryRepository;

        Category category = categoryRepository.save(new Category());
        ProductCategoryAge productCategoryAge1 = productCategoryAgeRepository.save(new ProductCategoryAge());
        ProductCategoryAge productCategoryAge2 = productCategoryAgeRepository.save(new ProductCategoryAge());


        Product product1 = new Product();
        product1.setBrand("abc");
        product1.setQuantity(8L);
        product1.setDescription("testdesc");
        product1.setDisabled(false);
        product1.setName("kndf");
        product1.setQuantityAvailable(2L);
        product1.setCategory(category);
        product1.setPrice(BigDecimal.valueOf(234));
        product1.setProductCategoryAgeList(new ArrayList<>());
        product1.getProductCategoryAgeList().add(productCategoryAge1);

        Product product2 = new Product();
        product2.setBrand("abc");
        product2.setQuantity(8L);
        product2.setDescription("testdesc");
        product2.setDisabled(false);
        product2.setName("jfdf");
        product2.setQuantityAvailable(3L);
        product2.setCategory(category);
        product2.setPrice(BigDecimal.valueOf(211));
        product2.setProductCategoryAgeList(new ArrayList<>());
        product2.getProductCategoryAgeList().add(productCategoryAge2);

        Product product3 = new Product();
        product3.setBrand("bcd");
        product3.setQuantity(9L);
        product3.setDescription("testdesc");
        product3.setDisabled(false);
        product3.setName("fdgdg");
        product3.setQuantityAvailable(3L);
        product3.setCategory(category);
        product3.setPrice(BigDecimal.valueOf(111));
        product3.setProductCategoryAgeList(new ArrayList<>());
        product3.getProductCategoryAgeList().add(productCategoryAge1);
        product3.getProductCategoryAgeList().add(productCategoryAge2);

        underTestRepository.save(product1);
        underTestRepository.save(product2);
        underTestRepository.save(product3);
    }

//    @AfterEach
//    void tearDown() {
//        underTestRepository.deleteAll();
//        categoryRepository.deleteAll();
//    }

    @Test
    void itShouldSaveProduct() {
        //given
        Product product = new Product();
        product.setBrand("testowy");
        product.setQuantity(3L);
        product.setDescription("testdesc");
        product.setDisabled(false);
        product.setName("fdgdg");
        product.setQuantityAvailable(2L);

        //when
        Product product2 = underTestRepository.save(product);

        //then

        Optional<Product> optionalProduct = underTestRepository.findById(4L);
        assertThat(optionalProduct)
                .isPresent()
                .hasValueSatisfying(product1 -> {
                    assertThat(product1)
                            .isEqualToIgnoringGivenFields(product, "id", "photoUrl");
                });

        //after
        underTestRepository.delete(product2);
    }

    @Test
    void itShouldFindAllByCategory_IdOrderByPriceAsc() {
        //given
        //when
        List<Product> products = underTestRepository
                .findAllByCategory_IdOrderByPriceAsc(1L, PageRequest.of(0, 30))
                .getContent();
        //then
        assertThat(products).isNotNull().hasSize(3).isSortedAccordingTo((x, y) -> x.getPrice().subtract(y.getPrice()).intValue());
    }

    @Test
    void itShouldFindAllByCategory_IdOrderByPriceDesc() {
        //given
        //when
        List<Product> products = underTestRepository
                .findAllByCategory_IdOrderByPriceDesc(1L, PageRequest.of(0, 30))
                .getContent();
        //then
        assertThat(products).isNotNull().hasSize(3).isSortedAccordingTo((x, y) -> y.getPrice().subtract(x.getPrice()).intValue());
    }

    @Test
    void itShouldFindDistinctByCategory_IdAndProductCategoryAgeListInAndPriceBetweenAndDisabledFalse() {
        //given
        List<ProductCategoryAge> productCategoryAges = productCategoryAgeRepository.findAll();
        var productDisabled = new Product();
        productDisabled.setProductCategoryAgeList(productCategoryAges);
        productDisabled.setBrand("abc");
        productDisabled.setCategory(categoryRepository.findById(1L).get());
        productDisabled.setPrice(BigDecimal.valueOf(200));
        productDisabled.setDisabled(true);
        productDisabled = underTestRepository.save(productDisabled);
        var prod5 = new Product();
        prod5.setProductCategoryAgeList(productCategoryAges);
        prod5.setPrice(BigDecimal.valueOf(200));
        prod5.setDisabled(false);
        Category category = categoryRepository.save(new Category());
        prod5.setCategory(category);
        prod5 = underTestRepository.save(prod5);
        var prod6 = new Product();
        prod6.setPrice(BigDecimal.valueOf(200));
        prod6.setDisabled(false);
        prod6.setCategory(categoryRepository.findById(1L).get());
        prod6=underTestRepository.save(prod6);
        //when
        List<Product> products = underTestRepository
                .findDistinctByCategory_IdAndProductCategoryAgeListInAndPriceBetweenAndDisabledFalse
                        (1L, productCategoryAges, BigDecimal.valueOf(50), BigDecimal.valueOf(300)
                                , PageRequest.of(0, 30))
                .getContent();
        //then
        assertThat(products).isNotNull().hasSize(3)
                .doesNotContain(productDisabled)
                .doesNotContain(prod5)
                .doesNotContain(prod6);
        //after
        underTestRepository.delete(productDisabled);
        underTestRepository.delete(prod5);
        categoryRepository.delete(category);
        underTestRepository.delete(prod6);
    }

    @Test
    void itShouldGetBrandsbyCategory() {
        //given
        //when
        Set<String> brandsbyCategory = underTestRepository.getBrandsbyCategory(1L);
        //then
        assertThat(brandsbyCategory).isNotNull().hasSize(2).contains("abc").contains("bcd");
    }

    @Test
    void itShouldGetBrandsEnabledByCategory() {
        //given
        var product1 = new Product();
        product1.setDisabled(true);
        product1.setBrand("xyz");
        product1.setCategory(categoryRepository.findById(1L).get());
        underTestRepository.save(product1);
        //when
        Set<String> brands = underTestRepository.getBrandsEnabledByCategory(1L);
        //then
        assertThat(brands).isNotNull().hasSize(2).contains("abc").contains("bcd").doesNotContain("xyz");

        //after
        underTestRepository.delete(product1);

    }

    @Test
    void itShouldFindDistinctByCategory_IdAndProductCategoryAgeListInAndPriceBetweenAndDisabledIsFalseAndBrandIsIn() {
        //given
        //when
        //then
    }
}
