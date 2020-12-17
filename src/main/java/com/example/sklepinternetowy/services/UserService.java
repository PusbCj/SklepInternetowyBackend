package com.example.sklepinternetowy.services;

import com.example.sklepinternetowy.models.user.ChangePassword;
import com.example.sklepinternetowy.models.user.ChangeYourData;
import com.example.sklepinternetowy.models.user.UserApplication;
import com.example.sklepinternetowy.models.user.UserDtoRegister;

import java.util.Optional;

public interface UserService {


    void signup(UserDtoRegister userDtoRegister);

    void activateUser(String key, String username);

    void generateForgetKey(String username);

    void changeForgottenPassword(String username, String password, Long key);

    void changePassword(ChangePassword changePassword);

    void changeYourdate(ChangeYourData changeYourData);

    ChangeYourData getUserData();

    UserApplication getUserObjectLogged();

    Optional<UserApplication> getOptionalUserLogged();
}
