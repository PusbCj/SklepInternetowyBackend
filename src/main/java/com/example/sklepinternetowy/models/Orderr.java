package com.example.sklepinternetowy.models;

import com.example.sklepinternetowy.models.user.UserApplication;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Orderr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date createDate;
    @OneToOne
    private ShopCart shopCart;

    @ManyToOne
    private UserApplication user;

    @ManyToOne
    private Address address;

    private OrderStatus orderStatus;

    public Orderr() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public ShopCart getShopCart() {
        return shopCart;
    }

    public void setShopCart(ShopCart shopCart) {
        this.shopCart = shopCart;
    }

    public UserApplication getUser() {
        return user;
    }

    public void setUser(UserApplication user) {
        this.user = user;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @PrePersist
    private void prePersist() {
        if (createDate == null) {
            createDate = new Date();
        }
    }
}
