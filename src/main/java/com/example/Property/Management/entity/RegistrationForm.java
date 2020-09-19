package com.example.Property.Management.entity;

import com.example.Property.Management.auth.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegistrationForm {

    private Owner owner;
    private User user;

}
