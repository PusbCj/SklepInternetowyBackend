package com.example.sklepinternetowy.controllers;


import com.example.sklepinternetowy.models.user.Role;
import com.example.sklepinternetowy.services.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/role/")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public Page<Role> getAllRoles(Pageable pageable){
        return roleService.getAll(pageable);
    }

    @GetMapping("{id}")
    public Role getByid(@PathVariable Long id){
        return roleService.getById(id);
    }
    @PostMapping
    public Role save(@RequestBody Role role){
        return roleService.save(role);
    }

    @DeleteMapping({"id"})
    public Role delete(@PathVariable Long id){
        return roleService.delete(id);
    }

}
