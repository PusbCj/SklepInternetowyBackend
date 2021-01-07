package com.example.sklepinternetowy.repositories;

import com.example.sklepinternetowy.models.OrderStatus;
import com.example.sklepinternetowy.models.Orderr;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orderr,Long> {

    Optional<Orderr> findFirstByUserIdAndOrderStatus(Long user_id, OrderStatus orderStatus);

    Page<Orderr> findAllByUserId(Long user_id, Pageable pageable);


}
