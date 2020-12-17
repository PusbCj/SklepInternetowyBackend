package com.example.sklepinternetowy.services;

import com.example.sklepinternetowy.exception.*;
import com.example.sklepinternetowy.models.Address;
import com.example.sklepinternetowy.models.user.ChangePassword;
import com.example.sklepinternetowy.models.user.ChangeYourData;
import com.example.sklepinternetowy.models.user.UserApplication;
import com.example.sklepinternetowy.models.user.UserDtoRegister;
import com.example.sklepinternetowy.repositories.AddressRepository;
import com.example.sklepinternetowy.repositories.user.RoleRepository;
import com.example.sklepinternetowy.repositories.user.UserApplicationRepository;
import com.google.common.base.Strings;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

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
        UserApplication user = getUserApplicationFromDatabase(username);
        user.setKeyPassword(random.nextLong());
       mailService.sendForgetPasswordMail(user.getKeyPassword(), user);
       userApplicationRepository.save(user);

    }

    @Override
    public void changeForgottenPassword(String username, String password, Long key) {
        UserApplication user = getUserApplicationFromDatabase(username);
        if (user.getKeyPassword().equals(key)) {
            user.setPassword(passwordEncoder.encode(password));
            user.setKeyPassword(null);
            userApplicationRepository.save(user);
        }else throw new PasswordKeyIsInvalid("Klucz niepoprawny");
    }

    @Transactional
    @Override
    public void changePassword(ChangePassword changePassword) {
        UserApplication user = getUserApplicationFromDatabase(getUserFromContext());
        String hash = user.getPassword();
        if (passwordEncoder.matches(changePassword.getOldPassword(),hash)) {
            user.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
        }else{
            throw new PasswordIncorrect("Hasło nieprawidłowe");
        }

    }

    @Transactional
    @Override
    public void changeYourdate(ChangeYourData changeYourData) {
        UserApplication user = getUserApplicationFromDatabase(getUserFromContext());
        String hash = user.getPassword();
        if (passwordEncoder.matches(changeYourData.getCurrentPassword(),hash)) {
            changeUserData(changeYourData, user);

        }else{
            throw new PasswordIncorrect("Hasło nieprawidłowe");
        }

    }


    private void changeUserData(ChangeYourData changeYourData, UserApplication user) {

        if(!Strings.isNullOrEmpty(changeYourData.getEmail())) {
            user.setEmail(changeYourData.getEmail());
        }
        if(!Strings.isNullOrEmpty(changeYourData.getFirstname())) {
            user.setFirstName(changeYourData.getFirstname());
        }
        if(!Strings.isNullOrEmpty(changeYourData.getLastname())){
            user.setLastName(changeYourData.getLastname());
        }

        List<Address> addressList = user.getAddress();
        if(!Strings.isNullOrEmpty(changeYourData.getStreet())){
            addressList.get(0).setStreet(changeYourData.getStreet());
        }
        if (!Strings.isNullOrEmpty(changeYourData.getNumberHouse())) {
            addressList.get(0).setHouseNumber(changeYourData.getNumberHouse());
        }
        if (!Strings.isNullOrEmpty(changeYourData.getCity())) {
            addressList.get(0).setCity(changeYourData.getCity());
        }

        if (!Strings.isNullOrEmpty(changeYourData.getPostCode())) {
            addressList.get(0).setPostCode(changeYourData.getPostCode());
        }

    }

    @Override
    public ChangeYourData getUserData() {
        UserApplication user = getUserApplicationFromDatabase(getUserFromContext());

        ChangeYourData data = new ChangeYourData();
        data.setEmail(user.getEmail());
        data.setFirstname(user.getFirstName());
        data.setLastname(user.getLastName());
        data.setStreet(user.getAddress().get(0).getStreet());
        data.setCity(user.getAddress().get(0).getCity());
        data.setPostCode(user.getAddress().get(0).getPostCode());
        data.setNumberHouse(user.getAddress().get(0).getHouseNumber());
        return data;
    }

    private UserApplication getUserApplicationFromDatabase(String userFromContext) {
        return userApplicationRepository.findByUsername(userFromContext)
                .orElseThrow(() -> new UserNotExistInDatabase("Uzytkownik nie odnalezniony w bazie"));
    }

    private void checkIfUserOrEmailAlreadyExistInDatabase(UserDtoRegister userDtoRegister) {
        if (userApplicationRepository.findByUsername(userDtoRegister.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistInDatabaseException("Użytkownik o podanej nazwie już istnieje.");
        }
        if (userApplicationRepository.findByEmail(userDtoRegister.getEmail()).isPresent()) {
            throw new EmailAlreadyExistInDatabaseException("Podany email jest już przypisany do użytkownika.");
        }
    }

    public String getUserFromContext(){
        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();
        return  (String) authentication.getPrincipal();

    }
    public UserApplication getUserObjectLogged(){
        return getUserApplicationFromDatabase(getUserFromContext());
    }

    public Optional<UserApplication> getOptionalUserLogged(){
        return userApplicationRepository.findByUsername(getUserFromContext());
    }
}
