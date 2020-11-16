package com.example.sklepinternetowy.repositories;

import com.example.sklepinternetowy.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
