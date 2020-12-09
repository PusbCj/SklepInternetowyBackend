package com.example.sklepinternetowy.repositories.user;

import com.example.sklepinternetowy.models.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findFirstByName(String s);
}
