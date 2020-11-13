package com.example.sklepinternetowy.controllers;

import com.example.sklepinternetowy.models.user.UsernameAndPassword;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/signup/")
public class RegistrationController {

    @PostMapping
    public ResponseEntity register(UsernameAndPassword usernameAndPassword){
        return new ResponseEntity(HttpStatus.OK);
    }
}
