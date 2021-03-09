package com.example.sklepinternetowy.security;


import com.example.sklepinternetowy.repositories.user.UserApplicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailServiceImpl implements UserDetailsService {

    final private Logger LOGGER = LoggerFactory.getLogger(UserDetailServiceImpl.class);
    final UserApplicationRepository userApplicationRepository;

    public UserDetailServiceImpl(UserApplicationRepository userApplicationRepository) {
        this.userApplicationRepository = userApplicationRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userApplicationRepository.findByUsername(s)
                .orElseThrow(() -> {
                    LOGGER.error("Username " + s + " not found");
                    return new UsernameNotFoundException("Username " + s + " not found");
                });
    }
}
