package com.example.sklepinternetowy.controllers;

import com.example.sklepinternetowy.models.Orderr;
import com.example.sklepinternetowy.services.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/order/")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("hasAuthority('User')")
    @GetMapping("current/")
    public Orderr getCurrentOrder() {
        return orderService.getCurrentOrder();
    }

    @GetMapping("{id}")
    public Orderr getCurrentOrderById(@PathVariable Long id) {
        return orderService.getCurrentOrderById(id);
    }

    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping
    public Page<Orderr> getAllOrders(Pageable pageable) {
        return orderService.getAllOrders(pageable);
    }

    @PostMapping
    public Orderr save(@RequestBody Orderr order) {
        return orderService.save(order);
    }

    @PatchMapping("{id}")
    public Orderr update(@RequestBody Orderr order, @PathVariable Long id) {
        return orderService.update(order, id);
    }

    @PreAuthorize("hasAuthority('Admin')")
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
         orderService.delete(id);
    }


}
