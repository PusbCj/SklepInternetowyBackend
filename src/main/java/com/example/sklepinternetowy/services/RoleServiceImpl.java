package com.example.sklepinternetowy.services;

import com.example.sklepinternetowy.models.user.Role;
import com.example.sklepinternetowy.repositories.user.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Page<Role> getAll(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    @Override
    public Role getById(Long id) {
        return roleRepository.findById(id).orElseThrow();
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role delete(Long id) {
        Role role= getById(id);
        roleRepository.deleteById(id);
        return role;
    }
}
