package com.example.sklepinternetowy.services;

import com.example.sklepinternetowy.models.Orderr;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {



    Orderr getCurrentOrder();

    Orderr getCurrentOrderById(Long id);

    Page<Orderr> getAllOrders(Long button, Pageable pageable);

    Orderr save(Orderr order);

    Orderr update(Orderr order, Long id);

    void delete(Long id);

    void finaliseOrder(Long id);

    Orderr getNewOrderNotLoggedUser(Long cartid);

    Orderr updateOrderNotLoggedUser(Orderr order);

    void finaliseOrderNotLoggedUser(Long id);


    Orderr getOrderNotLoggedUserBy(Long id);

    Orderr update(Orderr order);

    Orderr getById(Long id);

    Page<Orderr> getAllOrdersByUser(Pageable pageable);
}
