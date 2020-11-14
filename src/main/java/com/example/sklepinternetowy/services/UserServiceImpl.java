package com.example.sklepinternetowy.services;

import com.example.sklepinternetowy.exception.EmailAlreadyExistInDatabaseException;
import com.example.sklepinternetowy.exception.UsernameAlreadyExistInDatabaseException;
import com.example.sklepinternetowy.models.user.UserApplication;
import com.example.sklepinternetowy.models.user.UserDtoRegister;
import com.example.sklepinternetowy.repositories.user.RoleRepository;
import com.example.sklepinternetowy.repositories.user.UserApplicationRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.example.sklepinternetowy.mappers.UserMapper.userApplicationFromUserDtoRegistration;

@Service
public class UserServiceImpl implements UserService{

    private final UserApplicationRepository userApplicationRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserApplicationRepository userApplicationRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userApplicationRepository = userApplicationRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public void signup(UserDtoRegister userDtoRegister) {
        checkIfUserOrEmailAlreadyExistInDatabase(userDtoRegister);
        UserApplication userApplication = userApplicationFromUserDtoRegistration(userDtoRegister);
        userApplication.setPassword(passwordEncoder.encode(userApplication.getPassword()));
        userApplication.getRoleList().add(roleRepository.findById(1L).get());//Always exist UserRole therefore We don't check.

        userApplicationRepository.save(userApplication);
    }

    private void checkIfUserOrEmailAlreadyExistInDatabase(UserDtoRegister userDtoRegister) {
        if (userApplicationRepository.findByUsername(userDtoRegister.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistInDatabaseException("Użytkownik o podanej nazwie już istnieje.");
        }
        if (userApplicationRepository.findByEmail(userDtoRegister.getEmail()).isPresent()) {
            throw new EmailAlreadyExistInDatabaseException("Podany email jest już przypisany do użytkownika.");
        }
    }
}
