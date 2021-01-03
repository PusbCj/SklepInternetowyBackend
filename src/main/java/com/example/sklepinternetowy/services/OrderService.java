package com.example.sklepinternetowy.services;

import com.example.sklepinternetowy.models.Orderr;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {



    Orderr getCurrentOrder();

    Orderr getCurrentOrderById(Long id);

    Page<Orderr> getAllOrders(Pageable pageable);

    Orderr save(Orderr order);

    Orderr update(Orderr order, Long id);

    void delete(Long id);
}
