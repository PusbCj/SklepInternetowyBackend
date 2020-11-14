package com.example.sklepinternetowy.controllers;

import com.example.sklepinternetowy.models.user.UserDtoRegister;
import com.example.sklepinternetowy.models.user.UsernameAndPassword;
import com.example.sklepinternetowy.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("api/v1/signup/")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity register(@Valid @RequestBody UserDtoRegister userDtoRegister){
        userService.signup(userDtoRegister);
        return new ResponseEntity(HttpStatus.OK);
    }
}
