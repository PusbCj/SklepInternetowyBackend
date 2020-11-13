package com.example.sklepinternetowy.repositories.user;

import com.example.sklepinternetowy.models.user.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission,Long> {
}
