package com.example.sklepinternetowy.security;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.sklepinternetowy.repositories.user.UserApplicationRepository;


@Service
public class UserDetailServiceImpl implements UserDetailsService {

    final UserApplicationRepository userApplicationRepository;

    public UserDetailServiceImpl(UserApplicationRepository userApplicationRepository) {
        this.userApplicationRepository = userApplicationRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userApplicationRepository.findByUsername(s)
                .orElseThrow(()-> new UsernameNotFoundException("Username "+ s + " nt found"));
    }
}
