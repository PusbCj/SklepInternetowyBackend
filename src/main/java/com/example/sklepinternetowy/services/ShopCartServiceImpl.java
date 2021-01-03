package com.example.sklepinternetowy.services;

import com.example.sklepinternetowy.models.Address;
import com.example.sklepinternetowy.models.CartStatus;
import com.example.sklepinternetowy.models.ItemShopCart;
import com.example.sklepinternetowy.models.ShopCart;
import com.example.sklepinternetowy.models.user.UserApplication;
import com.example.sklepinternetowy.repositories.AddressRepository;
import com.example.sklepinternetowy.repositories.ItemShopCartRepository;
import com.example.sklepinternetowy.repositories.ShopCartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShopCartServiceImpl implements ShopCartService {
    private final UserService userService;
    private final ShopCartRepository shopCartRepository;
    private final ItemShopCartRepository itemShopCartRepository;
    private Logger logger = LoggerFactory.getLogger(ShopCartServiceImpl.class);
    private final AddressRepository addressRepository;

    public ShopCartServiceImpl(UserService userService,
                               ShopCartRepository shopCartRepository, ItemShopCartRepository itemShopCartRepository, AddressRepository addressRepository) {
        this.userService = userService;
        this.shopCartRepository = shopCartRepository;
        this.itemShopCartRepository = itemShopCartRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public ShopCart addProduct(ItemShopCart itemShopCart) {
        var shopCart = getCurrentOpenCartOrCreateNew();
        return addProductNotDublicate(itemShopCart, shopCart);


    }

    private ShopCart getCurrentOpenCartOrCreateNew() {
        Optional<ShopCart> shopCartOpt = getCurrentCart();
        ShopCart shopCart;
        if (shopCartOpt.isEmpty()) {
            shopCart = new ShopCart();
            shopCart.setCartStatus(CartStatus.START);

            shopCart.setUserApplication(userService.getUserObjectLogged());
            shopCart = shopCartRepository.save(shopCart);
        } else {
            shopCart = shopCartOpt.get();
        }
        return shopCart;
    }

    @Override
    public ShopCart updateCart(ShopCart shopCart) {
        shopCart.getItemShopCartList().forEach(itemShopCartRepository::save);
        ShopCart oldShopCart = shopCartRepository.findById(shopCart.getId()).orElseThrow(() -> new RuntimeException("Shop cart nie odnaleziony"));
        oldShopCart.setItemShopCartList(shopCart.getItemShopCartList());
        return shopCartRepository.save(oldShopCart);
    }

    @Override
    public Optional<ShopCart> getCurrentCart() {
        UserApplication user = userService.getUserObjectLogged();
        return shopCartRepository
                .findFirstByUserApplicationIdAndCartStatus(user.getId(), CartStatus.START);

    }

    @Override
    public ShopCart getCartById(Long cartId) {
        Optional<ShopCart> shopCartOptional = shopCartRepository.findById(cartId);
        if (shopCartOptional.isPresent() &&
                shopCartOptional.get().getUserApplication() == null &&
                shopCartOptional.get().getCartStatus() == CartStatus.START) {
            return shopCartOptional.get();
        } else {
            throw new RuntimeException("Koszyk nie odnaleziony");
        }

    }

    @Override
    public ShopCart updateCartById(ShopCart shopCart) {
        Optional<ShopCart> shopCartOptional = shopCartRepository.findById(shopCart.getId());
        if (shopCartOptional.isPresent() && shopCartOptional.get().getUserApplication() == null
                && shopCartOptional.get().getCartStatus() == CartStatus.START) {
            itemShopCartRepository.saveAll(shopCart.getItemShopCartList());
            return shopCartRepository.save(shopCart);
        } else {
            throw new RuntimeException("Koszyk nie odnaleziony");
        }
    }

    @Override
    public ShopCart addProductToCartId(ItemShopCart itemShopCart, Long id) {
        ShopCart shopCart;
        if (shopCartRepository.findById(id).isPresent()
                && shopCartRepository.findById(id).get().getUserApplication() == null
                && shopCartRepository.findById(id).get().getCartStatus() == CartStatus.START) {
            shopCart = shopCartRepository.findById(id).get();
        } else {
            ShopCart shopCart1 = new ShopCart();
            shopCart1.setCartStatus(CartStatus.START);
            shopCart = shopCartRepository.save(shopCart1);
        }

        return addProductNotDublicate(itemShopCart, shopCart);
    }

    @Override
    public ShopCart addShopCartToExistingOne(Long id) {
        ShopCart currentCart = getCurrentOpenCartOrCreateNew();
        if (shopCartRepository.findById(id).isPresent()) {
            ShopCart shopCartNotRegisterUser = shopCartRepository.findById(id).get();
            shopCartNotRegisterUser.getItemShopCartList()
                    .forEach(x -> currentCart.getItemShopCartList()
                            .add(itemShopCartRepository.save(new ItemShopCart(x.getProduct(), x.getQuantity()))));

            shopCartNotRegisterUser.setCartStatus(CartStatus.CLOSE);
            shopCartRepository.save(shopCartNotRegisterUser);
            return shopCartRepository.save(currentCart);
        }

        return currentCart;
    }


    private ShopCart addProductNotDublicate(ItemShopCart itemShopCart, ShopCart shopCart) {
        Optional<ItemShopCart> itemShopCartOptional = shopCart.getItemShopCartList()
                .stream()
                .filter(item -> item.getProduct().getId().equals(itemShopCart.getProduct().getId()))
                .findFirst();
        if (itemShopCartOptional.isEmpty()) {
            itemShopCartRepository.save(itemShopCart);
            shopCart.getItemShopCartList().add(itemShopCart);
        } else {
            ItemShopCart itemShopCart1 = itemShopCartOptional.get();
            itemShopCart1.setQuantity(itemShopCart1.getQuantity() + itemShopCart.getQuantity());
            itemShopCartRepository.save(itemShopCart1);
        }

        return shopCartRepository.save(shopCart);
    }


}
