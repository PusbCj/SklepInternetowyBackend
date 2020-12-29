package com.example.sklepinternetowy.controllers;


import com.example.sklepinternetowy.models.ItemShopCart;
import com.example.sklepinternetowy.models.ShopCart;
import com.example.sklepinternetowy.services.ShopCartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/shopcart/")
public class ShopCartController {


    private final ShopCartService shopCartService;

    public ShopCartController(ShopCartService shopCartService) {
        this.shopCartService = shopCartService;
    }

    @PreAuthorize("hasAuthority('User')")
    @GetMapping
    public ResponseEntity<ShopCart> getCurrentCart(){
        Optional<ShopCart> currentCart = shopCartService.getCurrentCart();

        if(currentCart.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(currentCart.get(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('User')")
    @PutMapping
    public ShopCart updateCart(@RequestBody ShopCart shopCart){
        return shopCartService.updateCart(shopCart);
    }

    @PreAuthorize("hasAuthority('User')")
    @PostMapping("addproduct")
    public ShopCart addProduct(@RequestBody ItemShopCart itemShopCart){
        return shopCartService.addProduct(itemShopCart);
    }
    @PreAuthorize("hasAuthority('User')")
    @PostMapping("addshopcart/{id}")
    public ShopCart addShopCarttoRegisterUser(@PathVariable Long id){
        return shopCartService.addShopCartToExistingOne(id);
    }

    @GetMapping("anon/{id}")
    public ShopCart getCartById(@PathVariable Long id){
        return shopCartService.getCartById(id);
    }

    @PutMapping("anon/")
    public ShopCart updateCartById( @RequestBody ShopCart shopCart){
        return shopCartService.updateCartById(shopCart);
    }

    @PostMapping("anon/{id}/addproduct")
    public ShopCart addProductToCartId(@RequestBody ItemShopCart itemShopCart,@PathVariable Long id){
        return shopCartService.addProductToCartId(itemShopCart,id);
    }

}
