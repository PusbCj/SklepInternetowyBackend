package com.example.sklepinternetowy.mappers;

import com.example.sklepinternetowy.models.Address;
import com.example.sklepinternetowy.models.user.UserApplication;
import com.example.sklepinternetowy.models.user.UserDtoRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

public class UserMapper {



    public static UserApplication userApplicationFromUserDtoRegistration(UserDtoRegister userDtoRegister){
        UserApplication userApplication= new UserApplication();
        userApplication.setFirstName(userDtoRegister.getFirstName());
        userApplication.setLastName(userDtoRegister.getLastName());
        userApplication.setUsername(userDtoRegister.getUsername());
        userApplication.setEmail(userDtoRegister.getEmail());
        userApplication.setPassword(userDtoRegister.getPassword());
        userApplication.setAddress(new HashSet<>());
        Address address = new Address();
        address.setCity(userDtoRegister.getCity());
        address.setHouseNumber(userDtoRegister.getHouseNumber());
        address.setName(userDtoRegister.getNameAddress());
        address.setStreet(userDtoRegister.getStreet());
        address.setPostCode(userDtoRegister.getPostCode());
        userApplication.getAddress().add(address);
        return userApplication;
    }
}
