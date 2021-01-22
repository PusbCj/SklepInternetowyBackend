package com.example.sklepinternetowy.services;

import com.example.sklepinternetowy.exception.OrderNotFoundException;
import com.example.sklepinternetowy.models.*;
import com.example.sklepinternetowy.models.user.UserApplication;
import com.example.sklepinternetowy.repositories.AddressRepository;
import com.example.sklepinternetowy.repositories.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {


    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ShopCartService shopCartService;
    private final AddressRepository addressRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserService userService, ShopCartService shopCartService, AddressRepository addressRepository) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.shopCartService = shopCartService;
        this.addressRepository = addressRepository;
    }

    @Override
    public Orderr getCurrentOrder() {
        UserApplication userApplication = userService.getUserObjectLogged();
        Optional<Orderr> optionalOrderr = orderRepository
                .findFirstByUserIdAndOrderStatus(userApplication.getId(), OrderStatus.CREATE);
        if (optionalOrderr.isPresent()) {
            return optionalOrderr.get();
        } else {
            Orderr order = new Orderr();
            ShopCart currentCart = shopCartService.getCurrentOpenCartOrCreateNew();

            order.setShopCart(currentCart);
            order.setUser(userApplication);
            order.setAddress(userApplication.getAddress().get(0));
            order.getAddress().setName(userApplication.getFirstName() + " " + userApplication.getLastName());
            order.setOrderStatus(OrderStatus.CREATE);
            return orderRepository.save(order);
        }
    }

    @Override
    public Orderr getCurrentOrderById(Long id) {
        Orderr order = orderRepository
                .findById(id).orElseThrow(() -> new OrderNotFoundException("Koszyk nie odnaaleziony"));
        if (order.getUser() != null && order.getUser() != userService.getUserObjectLogged()) {
            throw new OrderNotFoundException("Koszyk nalezy do innego użytkownika");
        }else {

            return order;
        }


    }

    @Override
    public Page<Orderr> getAllOrders(Pageable pageable) {
        return orderRepository.findAllByOrderStatusIsNot(OrderStatus.CREATE,pageable);
    }

    @Override
    public Orderr save(Orderr order) {
        order.setId(0L); //Make sure that Order is not update;
        return orderRepository.save(order);
    }

    @Override
    public Orderr update(Orderr order, Long id) {
        getCurrentOrderById(id);
        order.setUser(userService.getUserObjectLogged());
        addressRepository.save(order.getAddress());
        return orderRepository.save(order);
    }

    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void finaliseOrder(Long id) {
        Orderr order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Nie odnalezione zamówienie"));
        if(!order.getUser().equals(userService.getUserObjectLogged())){
            throw new OrderNotFoundException("Zamówienie o podanym id nie nalezy do tego użytkownika");
        }

        //wyslanie maila itp

        order.setOrderStatus(OrderStatus.CLOSE);
        order.getShopCart().setCartStatus(CartStatus.CLOSE);
    }

    @Transactional
    @Override
    public Orderr getNewOrderNotLoggedUser(Long cartid) {
        ShopCart cart = shopCartService.getCartById(cartid);
        if(cart.getUserApplication() != null){
            throw new RuntimeException("Cart id niepoprawne");
        }
        Orderr orderr = new Orderr();
        orderr.setOrderStatus(OrderStatus.CREATE);
        orderr.setShopCart(cart);
        orderr.setAddress(addressRepository.save(new Address()));

        return orderRepository.save(orderr);
    }


    @Override
    @Transactional
    public Orderr updateOrderNotLoggedUser(Orderr order) {

        checkIfexistAndIfHasNotUser(order.getId());
        order.setAddress(addressRepository.save(order.getAddress()));

        return orderRepository.save(order);
    }

    private Orderr checkIfexistAndIfHasNotUser(Long id) {
        Orderr orderr = orderRepository
                .findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Nie odnaleziony koszyk"));
        if(orderr.getUser()!= null){
            throw  new OrderNotFoundException("To zamówienie należy do innego użytkownika");
        }
        return orderr;
    }

    @Transactional
    @Override
    public void finaliseOrderNotLoggedUser(Long id) {
        Orderr order = checkIfexistAndIfHasNotUser(id);
        order.setOrderStatus(OrderStatus.CLOSE);
        order.getShopCart().setCartStatus(CartStatus.CLOSE);
        //logika

    }

    @Override
    public Orderr getOrderNotLoggedUserBy(Long id) {
        Orderr orderr = checkIfexistAndIfHasNotUser(id);
        return orderr;
    }

    @Override
    public Orderr update(Orderr order) {
        orderRepository
                .findById(order.getId())
                .orElseThrow(()-> new OrderNotFoundException("Nie ma takiego koszyka"));
        return orderRepository.save(order);
    }

    @Override
    public Orderr getById(Long id) {
        return orderRepository.findById(id).orElseThrow(()-> new OrderNotFoundException("Brak koszyka"));
    }

    @Override
    public Page<Orderr> getAllOrdersByUser(Pageable pageable) {
        UserApplication user = userService.getUserObjectLogged();
        return orderRepository.findAllByUserId(user.getId(),pageable);
    }
}
