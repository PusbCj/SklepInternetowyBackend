package com.example.sklepinternetowy.controllers;

import com.example.sklepinternetowy.exception.ActivationKeyIsInvalid;
import com.example.sklepinternetowy.exception.EmailAlreadyExistInDatabaseException;
import com.example.sklepinternetowy.exception.UsernameAlreadyExistInDatabaseException;
import com.example.sklepinternetowy.models.ErrorMessage;
import com.example.sklepinternetowy.models.user.UserDtoRegister;
import com.example.sklepinternetowy.models.user.UsernameAndPassword;
import com.example.sklepinternetowy.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping
    public void activateAccount(@RequestParam String key,@RequestParam String username){
        userService.activateUser(key,username);
    }

    @PostMapping("forget/")
    public void generateKeyChangePassword(@RequestBody String username){
        userService.generateForgetKey(username);
    }

    @PostMapping("changeassword/")
    public void changePasswordKey(@RequestBody String username, @RequestBody String password, @RequestBody Long key){
        userService.changeForgottenPassword(username,password,key);
    }

    @ExceptionHandler(ActivationKeyIsInvalid.class)
    public ResponseEntity<ErrorMessage> activationKeyIsInvalidHandler(){
        return new ResponseEntity<ErrorMessage>(new ErrorMessage("Klucz aktywacyjny nieprawidłowy"),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(EmailAlreadyExistInDatabaseException.class)
    public ResponseEntity<ErrorMessage> emailAlreadyExistInDatabaseExceptionHandler(){
        return new ResponseEntity<ErrorMessage>(new ErrorMessage("Użytkownik o podanym adresie email jest już zarejstrowany"),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UsernameAlreadyExistInDatabaseException.class)
    public ResponseEntity<ErrorMessage> usernameAlreadyExistInDatabaseExceptionHandler(){
        return new ResponseEntity<ErrorMessage>(new ErrorMessage("Użytkownik o podanym niku jest już zarejstrowany"),HttpStatus.BAD_REQUEST);
    }
}
