package com.example.sklepinternetowy.services;

import com.example.sklepinternetowy.models.user.UserDtoRegister;

public interface UserService {


    void signup(UserDtoRegister userDtoRegister);

    void activateUser(String key, String username);

    void generateForgetKey(String username);

    void changeForgottenPassword(String username, String password, Long key);
}
