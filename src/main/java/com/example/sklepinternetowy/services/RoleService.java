package com.example.sklepinternetowy.services;

import com.example.sklepinternetowy.models.user.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {
    Page<Role> getAll(Pageable pageable);

    Role getById(Long id);

    Role save(Role role);

    Role delete(Long id);
}
