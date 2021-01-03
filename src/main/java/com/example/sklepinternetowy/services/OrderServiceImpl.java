package com.example.sklepinternetowy.services;

import com.example.sklepinternetowy.models.Orderr;
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

    public OrderServiceImpl(OrderRepository orderRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    @Override
    public Orderr getCurrentOrder() {
        UserApplication userApplication = userService.getUserObjectLogged();

        return null;
    }

    @Override
    public Orderr getCurrentOrderById(Long id) {
        return null;
    }

    @Override
    public Page<Orderr> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Orderr save(Orderr order) {
        return null;
    }

    @Override
    public Orderr update(Orderr order, Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
}
