package com.example.sklepinternetowy.repositories.user;

import com.example.sklepinternetowy.models.user.UserApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserApplicationRepository extends JpaRepository<UserApplication,Long> {


    Optional<UserApplication> findByUsername(String username);
    Optional<UserApplication> findByEmail(String email);
}
