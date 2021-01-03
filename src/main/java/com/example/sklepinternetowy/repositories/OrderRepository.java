package com.example.sklepinternetowy.repositories;

import com.example.sklepinternetowy.models.Orderr;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orderr,Long> {

}
