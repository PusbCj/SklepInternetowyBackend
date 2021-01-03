package com.example.sklepinternetowy.mappers;

import com.example.sklepinternetowy.models.Address;
import com.example.sklepinternetowy.models.user.UserApplication;
import com.example.sklepinternetowy.models.user.UserDtoRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class UserMapper {



    public static UserApplication userApplicationFromUserDtoRegistration(UserDtoRegister userDtoRegister){
        UserApplication userApplication= new UserApplication();
        userApplication.setFirstName(userDtoRegister.getFirstName());
        userApplication.setLastName(userDtoRegister.getLastName());
        userApplication.setUsername(userDtoRegister.getUsername());
        userApplication.setEmail(userDtoRegister.getEmail());
        userApplication.setPassword(userDtoRegister.getPassword());
        userApplication.setAddress(new ArrayList<>());
        Address address = new Address();
        address.setName(userDtoRegister.getFirstName() + " " + userDtoRegister.getLastName());
        address.setCity(userDtoRegister.getCity());
        address.setHouseNumber(userDtoRegister.getHouseNumber());
        address.setName(userDtoRegister.getNameAddress());
        address.setStreet(userDtoRegister.getStreet());
        address.setPostCode(userDtoRegister.getPostCode());
        userApplication.getAddress().add(address);
        userApplication.setActivate(false);
        Random random = new Random();
        userApplication.setKeyActivation(String.valueOf(random.nextInt()));

        return userApplication;
    }
}
