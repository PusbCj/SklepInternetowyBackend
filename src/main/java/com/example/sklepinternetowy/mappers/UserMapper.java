package com.example.sklepinternetowy.mappers;

import com.example.sklepinternetowy.models.user.UserApplication;
import com.example.sklepinternetowy.models.user.UserDtoRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserMapper {



    public static UserApplication userApplicationFromUserDtoRegistration(UserDtoRegister userDtoRegister){
        UserApplication userApplication= new UserApplication();
        userApplication.setId(userDtoRegister.getId());
        userApplication.setFirstName(userDtoRegister.getFirstName());
        userApplication.setLastName(userDtoRegister.getLastName());
        userApplication.setUsername(userDtoRegister.getUsername());
        userApplication.setEmail(userDtoRegister.getEmail());
        userApplication.setPassword(userDtoRegister.getPassword());
        return userApplication;
    }
}
