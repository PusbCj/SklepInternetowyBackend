package com.example.sklepinternetowy.models;

import com.example.sklepinternetowy.models.user.UserApplication;
import org.apache.catalina.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Orderr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreatedDate
    private Date createDate;
    @OneToOne
    private ShopCart shopCart;

    @ManyToOne
    private UserApplication user;

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

    //    @PrePersist
//    private void prePersist(){
//        if(createDate==null){
//            createDate = new Date();
//        }
//    }
}
