package com.example.sklepinternetowy.controllers;

import com.example.sklepinternetowy.models.user.ChangePassword;
import com.example.sklepinternetowy.models.user.ChangeYourData;
import com.example.sklepinternetowy.models.user.UserApplication;
import com.example.sklepinternetowy.repositories.user.UserApplicationRepository;
import com.example.sklepinternetowy.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;
    private final UserApplicationRepository userApplicationRepository;

    public UserController(UserService userService, UserApplicationRepository userApplicationRepository) {
        this.userService = userService;
        this.userApplicationRepository = userApplicationRepository;
    }

    @PostMapping("/changepassword")
    public void changePassword(@RequestBody ChangePassword changePassword){
        userService.changePassword(changePassword);

    }
    @PatchMapping("/changeuserdate")
    public void changeUserdate(@RequestBody ChangeYourData changeYourData){
        userService.changeYourdate(changeYourData);
    }

    @GetMapping("/getuserdate")
    public ChangeYourData getUserData(){
       return userService.getUserData();
    }

    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping
    public Page<UserApplication> getAll(Pageable pageable){

        return userApplicationRepository.findAll(pageable);
    }

    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping
    public void saveUser(@RequestBody UserApplication userApplication){
        userService.save(userApplication);
    }

    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping("{id}")
    public ResponseEntity<UserApplication> getById(@PathVariable Long id){
        var optionalUser = userApplicationRepository.findById(id);
        return optionalUser.map(userApplication -> new ResponseEntity<>(userApplication, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PreAuthorize("hasAuthority('Admin')")
    @DeleteMapping("{id}")
    public void deleteUserById(@PathVariable Long id){
        userApplicationRepository.deleteById(id);
    }



}
