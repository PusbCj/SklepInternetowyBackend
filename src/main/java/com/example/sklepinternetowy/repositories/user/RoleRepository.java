package com.example.sklepinternetowy.repositories.user;

import com.example.sklepinternetowy.models.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
