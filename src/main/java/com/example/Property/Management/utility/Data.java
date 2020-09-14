package com.example.Property.Management.utility;

import com.example.Property.Management.auth.User;
import com.example.Property.Management.auth.UserService;
import com.example.Property.Management.dto.UserDto;
import com.example.Property.Management.entity.Owner;
import com.example.Property.Management.entity.RegistrationForm;
import com.example.Property.Management.jwt.UsernameAndPasswordAuthenticationRequest;
import com.example.Property.Management.repository.OwnerRepository;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Data {

    public UsernameAndPasswordAuthenticationRequest registerUser(UserService userService, OwnerRepository ownerRepository, RegistrationForm form){

        log.info("form: " + form);
        Owner owner = form.getOwner();
        owner = ownerRepository.save(owner);

        User user = form.getUser();
        String originalPassword = user.getPassword();
        user.encodePassword();
        user.setOwner(owner);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user = userService.saveUser(user);

        return new UsernameAndPasswordAuthenticationRequest(user.getUsername(), originalPassword);
        }
}
