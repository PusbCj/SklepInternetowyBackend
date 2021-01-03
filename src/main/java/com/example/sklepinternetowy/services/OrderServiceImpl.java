package com.example.sklepinternetowy.services;

import com.example.sklepinternetowy.exception.OrderNotFoundException;
import com.example.sklepinternetowy.models.OrderStatus;
import com.example.sklepinternetowy.models.Orderr;
import com.example.sklepinternetowy.models.ShopCart;
import com.example.sklepinternetowy.models.user.UserApplication;
import com.example.sklepinternetowy.repositories.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {


    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ShopCartService shopCartService;

    public OrderServiceImpl(OrderRepository orderRepository, UserService userService, ShopCartService shopCartService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.shopCartService = shopCartService;
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
            Optional<ShopCart> currentCart = shopCartService.getCurrentCart();
            if (currentCart.isPresent())
                order.setShopCart(currentCart.get());
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
        return orderRepository.findAll(pageable);
    }

    @Override
    public Orderr save(Orderr order) {
        order.setId(0L); //Make sure that Order is not update;
        return orderRepository.save(order);
    }

    @Override
    public Orderr update(Orderr order, Long id) {
        getCurrentOrderById(id);
        return orderRepository.save(order);
    }

    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
}
