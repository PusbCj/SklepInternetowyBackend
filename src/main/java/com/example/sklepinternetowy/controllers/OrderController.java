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

    @PreAuthorize("hasAuthority('User')")
    @GetMapping
    public Page<Orderr> getAllOrdersByUser(Pageable pageable) {
        return orderService.getAllOrdersByUser(pageable);
    }

    @PreAuthorize("hasAuthority('User')")
    @GetMapping("{id}")
    public Orderr getCurrentOrderById(@PathVariable Long id) {
        return orderService.getCurrentOrderById(id);
    }

    @GetMapping("notlogged/getnew/{cartid}")
    public Orderr getNewOrder(@PathVariable Long cartid ){
        return orderService.getNewOrderNotLoggedUser(cartid);
    }

    @PatchMapping("notlogged/update/")
    public Orderr updateOrder(@RequestBody Orderr order ){
        return orderService.updateOrderNotLoggedUser(order);
    }
    @GetMapping("notlogged/finalise/{id}")
    public void finaliseOrderNotLoggedUser(@PathVariable Long id ){
         orderService.finaliseOrderNotLoggedUser(id);
    }

    @GetMapping("notlogged/{id}")
    public Orderr getOrderNotLoggedUserById(@PathVariable Long id ){
       return orderService.getOrderNotLoggedUserBy(id);
    }

    @GetMapping("finalise/{id}")
    public void finaliseOrder(@PathVariable Long id){
        orderService.finaliseOrder(id);
    }




    @PatchMapping("{id}")
    public Orderr update(@RequestBody Orderr order, @PathVariable Long id) {
        return orderService.update(order, id);
    }


    //admin

    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping("admin/")
    public Orderr save(@RequestBody Orderr order) {
        return orderService.save(order);
    }
    @PreAuthorize("hasAuthority('Admin')")
    @PatchMapping("admin/")
    public Orderr update(@RequestBody Orderr order) {
        return orderService.update(order);
    }

    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping("admin/")
    public Page<Orderr> getAllOrders(@RequestParam Long button,Pageable pageable) {
        return orderService.getAllOrders(button ,pageable);
    }

    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping("admin/{id}")
    public Orderr getById(@PathVariable Long id) {
        return orderService.getById(id);
    }

    @PreAuthorize("hasAuthority('Admin')")
    @DeleteMapping("admin/{id}")
    public void delete(@PathVariable Long id) {
         orderService.delete(id);
    }


}
