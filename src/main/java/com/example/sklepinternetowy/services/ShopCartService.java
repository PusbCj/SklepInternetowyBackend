package com.example.sklepinternetowy.services;

import com.example.sklepinternetowy.models.ItemShopCart;
import com.example.sklepinternetowy.models.ShopCart;

import java.util.Optional;

public interface ShopCartService {
    ShopCart addProduct(ItemShopCart itemShopCart);

    ShopCart updateCart(ShopCart shopCart);

    Optional<ShopCart> getCurrentCart();

    ShopCart getCartById(Long cartId);


    ShopCart updateCartById(ShopCart shopCart);

    ShopCart addProductToCartId(ItemShopCart itemShopCart, Long id);

    ShopCart addShopCartToExistingOne(Long id);
}
