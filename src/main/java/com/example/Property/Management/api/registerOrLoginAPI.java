package com.example.Property.Management.api;

import com.example.Property.Management.auth.User;
import com.example.Property.Management.auth.UserRepository;
import com.example.Property.Management.auth.UserService;
import com.example.Property.Management.dto.UserDto;
import com.example.Property.Management.entity.Owner;
import com.example.Property.Management.entity.RegistrationForm;
import com.example.Property.Management.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import com.example.Property.Management.repository.OwnerRepository;
import com.example.Property.Management.utility.Converter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
@Slf4j
public class registerOrLoginAPI {

    @Autowired
    UserService userService;
    OwnerRepository ownerRepository;

    @PostMapping("/register")
    public Map<String, Object> addNewUser(@RequestBody RegistrationForm form, HttpServletRequest request, HttpServletResponse response)  {

        Map<String, Object> mapResponse = new HashMap<>();
        try {
            log.info("form: "+ form);
            Owner owner = form.getOwner();
            //owner = ownerRepository.save(owner);
            mapResponse.put("owner", owner);

            User user = form.getUser();
            log.info("user: " + user);
            user.encodePassword();
            user.setOwner(owner);
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
            user.setEnabled(true);
            log.info("user after encoding: \n" + user);
            //user = userService.saveUser(user);
            log.info("user after saving: \n" + user);
            UserDto userDto = Converter.userToDto(user);
            mapResponse.put("user",userDto);

            return mapResponse;
        } catch (Exception exception){

            log.error(exception.toString());
            mapResponse = new HashMap<>();
            mapResponse.put("Error", new String("Error while trying to save new user."));
            return mapResponse;
        }
    }



}
