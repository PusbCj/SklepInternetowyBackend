package com.example.sklepinternetowy.services;

import com.example.sklepinternetowy.models.user.UserDtoRegister;

public interface UserService {


    void signup(UserDtoRegister userDtoRegister);

    void activateUser(String key, String username);
}
