package com.example.sklepinternetowy.models.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserDtoRegister {

    private Long id;


    @NotBlank
    @Size(min= 5, max=50)
    private String username;

    @Size(max=50)
    private String firstName;

    @Size(max=50)
    private String lastName;

    @NotBlank
    @Size(min=5, max=50)
    private String password;

    @Email
    @Size(min=5, max=100)
    private String email;

    public UserDtoRegister() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
