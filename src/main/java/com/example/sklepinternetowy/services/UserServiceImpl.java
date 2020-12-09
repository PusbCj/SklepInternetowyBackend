package com.example.sklepinternetowy.services;

import com.example.sklepinternetowy.exception.*;
import com.example.sklepinternetowy.models.user.UserApplication;
import com.example.sklepinternetowy.models.user.UserDtoRegister;
import com.example.sklepinternetowy.repositories.AddressRepository;
import com.example.sklepinternetowy.repositories.user.RoleRepository;
import com.example.sklepinternetowy.repositories.user.UserApplicationRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Optional;
import java.util.Random;

import static com.example.sklepinternetowy.mappers.UserMapper.userApplicationFromUserDtoRegistration;

@Service
public class UserServiceImpl implements UserService{

    private final UserApplicationRepository userApplicationRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;
    private final MailService mailService;

    public UserServiceImpl(UserApplicationRepository userApplicationRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AddressRepository addressRepository, MailService mailService) {
        this.userApplicationRepository = userApplicationRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.addressRepository = addressRepository;
        this.mailService = mailService;
    }

    @Transactional
    @Override
    public void signup(UserDtoRegister userDtoRegister) {
        checkIfUserOrEmailAlreadyExistInDatabase(userDtoRegister);
        UserApplication userApplication = userApplicationFromUserDtoRegistration(userDtoRegister);
        userApplication.setPassword(passwordEncoder.encode(userApplication.getPassword()));
        userApplication.getRoleList().add(roleRepository.findFirstByName("User").get());//Always exist UserRole therefore We don't check.
        addressRepository.saveAll(userApplication.getAddress());
        userApplicationRepository.save(userApplication);
        mailService.sendActivationMail(userApplication);
    }

    @Override
    @Transactional
    public void activateUser(String key, String username) {
        Optional<UserApplication> user = userApplicationRepository.findByActivateFalseAndKeyActivationAndUsername(key,username);
        if(user.isPresent()){
            user.get().setActivate(Boolean.TRUE);
            user.get().setKeyActivation("");
        }else
            throw new ActivationKeyIsInvalid("Nie odnaleziono użytkownika do aktywacji");
    }

    @Override
    public void generateForgetKey(String username) {
        Random random= new Random();
       UserApplication user =userApplicationRepository.findByUsername(username)
               .orElseThrow(()-> new UserNotExistInDatabase("Uzytkownik nie odnalezniony w bazie"));
       user.setKeyPassword(random.nextLong());
       mailService.sendForgetPasswordMail(user.getKeyPassword(), user);
       userApplicationRepository.save(user);

    }

    @Override
    public void changeForgottenPassword(String username, String password, Long key) {
        UserApplication user =userApplicationRepository.findByUsername(username)
                .orElseThrow(()-> new UserNotExistInDatabase("Uzytkownik nie odnalezniony w bazie"));
        if (user.getKeyPassword().equals(key)) {
            user.setPassword(passwordEncoder.encode(password));
        }else throw new PasswordKeyIsInvalid("Klucz niepoprawny");
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
