package com.example.Property.Management.api;

import com.example.Property.Management.auth.User;
import com.example.Property.Management.auth.UserService;
import com.example.Property.Management.dto.UserDto;
import com.example.Property.Management.entity.Owner;
import com.example.Property.Management.entity.RegistrationForm;
import com.example.Property.Management.repository.OwnerRepository;
import com.example.Property.Management.utility.Converter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequestMapping(path = "api", produces = "application/json")
@RestController
public class RegisterAPI {

    private final UserService userService;
    private final OwnerRepository ownerRepository;

    @Autowired
    public RegisterAPI(UserService userService, OwnerRepository ownerRepository) {
        this.userService = userService;
        this.ownerRepository = ownerRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> addNewUser(@RequestBody RegistrationForm form) {

        Map<String, Object> mapResponse = new HashMap<>();
        String errorMessage = "Error while trying to save new user";
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        try {
            log.info("form: " + form);
            Owner owner = form.getOwner();
            User user = form.getUser();

            // Check that email is unique
            if(!userService.isEmailUnique(user.getUsername())) {
                errorMessage = "Email already exist";
                httpStatus = HttpStatus.CONFLICT;
                throw new Exception();
            }

            owner = ownerRepository.save(owner);
            mapResponse.put("owner", owner);

            user.encodePassword();
            user.setOwner(owner);
            user = userService.saveUser(user);
            UserDto userDto = Converter.userToDto(user);
            mapResponse.put("user", userDto);

            return new ResponseEntity<>(mapResponse, HttpStatus.OK);
        } catch (Exception exception) {
            log.error(exception.toString());
            mapResponse = new HashMap<>();
            mapResponse.put("Error", errorMessage);
            return new ResponseEntity<>(mapResponse, httpStatus);
        }
    }
}
