package com.example.sklepinternetowy.models;

import com.example.sklepinternetowy.models.user.UserApplication;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class ShopCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private Date dateOfCreation;


    @OneToMany
    private List<ItemShopCart> itemShopCartList;

    private CartStatus cartStatus;

    @JsonIgnore
    @ManyToOne
    private UserApplication userApplication;

    public ShopCart() {
        itemShopCartList = new ArrayList<>();
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public List<ItemShopCart> getItemShopCartList() {
        return itemShopCartList;
    }

    public void setItemShopCartList(List<ItemShopCart> itemShopCartList) {
        this.itemShopCartList = itemShopCartList;
    }

    public CartStatus getCartStatus() {
        return cartStatus;
    }

    public void setCartStatus(CartStatus cartStatus) {
        this.cartStatus = cartStatus;
    }

    public UserApplication getUserApplication() {
        return userApplication;
    }

    public void setUserApplication(UserApplication userApplication) {
        this.userApplication = userApplication;
    }

    @PrePersist
    public void onPrePersist(){
        setDateOfCreation(new Date());
    }


}
