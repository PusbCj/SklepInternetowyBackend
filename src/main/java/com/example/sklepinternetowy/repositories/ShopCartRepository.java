package com.example.sklepinternetowy.repositories;

import com.example.sklepinternetowy.models.CartStatus;
import com.example.sklepinternetowy.models.ShopCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopCartRepository extends JpaRepository<ShopCart,Long> {


    Optional<ShopCart> findFirstByUserApplicationIdAndCartStatus(Long userApplication_id, CartStatus cartStatus);
}
