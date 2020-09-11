package com.example.Property.Management.auth;

import com.example.Property.Management.auth.User;
import com.example.Property.Management.auth.UserRepository;
import com.example.Property.Management.dto.UserDto;
import com.example.Property.Management.utility.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserAPI {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/{email}")
    public UserDto getUserByUsername(@PathVariable("email") String email){

        User user= userRepository.findByUsername(email);

        System.out.println("Got user: " + user);

        if (user!= null) {
            UserDto userDto = new Converter().userToDto(user);
            System.out.println("userDto: " + userDto);
            return userDto;
        }
        return null;
    }

}
