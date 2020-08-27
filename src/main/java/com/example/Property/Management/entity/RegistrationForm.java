package com.example.Property.Management.entity;

import com.example.Property.Management.auth.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegistrationForm {
    private String username;
    private String password;
    private String name;
    private String surname;
    private String phonenumber;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(
                username, passwordEncoder.encode(password),
                true, true,
                true, true);
    }


}
