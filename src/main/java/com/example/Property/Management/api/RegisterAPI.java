package com.example.Property.Management.api;

import com.example.Property.Management.auth.User;
import com.example.Property.Management.auth.UserService;
import com.example.Property.Management.dto.UserDto;
import com.example.Property.Management.entity.ConfirmationToken;
import com.example.Property.Management.entity.Owner;
import com.example.Property.Management.entity.RegistrationForm;
import com.example.Property.Management.repository.OwnerRepository;
import com.example.Property.Management.service.ConfirmationTokenService;
import com.example.Property.Management.service.DataService;
import com.example.Property.Management.utility.Converter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequestMapping(path = "api", produces = "application/json")
@RestController
@AllArgsConstructor
public class RegisterAPI {

    private final UserService userService;
    private final OwnerRepository ownerRepository;
    private final DataService dataService;
    ConfirmationTokenService confirmationTokenService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> addNewUser(@RequestBody RegistrationForm form) {

        log.info("form API: ");

        Map<String, Object> mapResponse = new HashMap<>();
        String errorMessage = "Error while trying to save new user";
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        try {
            log.info("form API: " + form);
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

            //Save confirmation token
            log.info("create confirmationToken");
            final ConfirmationToken confirmationToken = new ConfirmationToken(user);
            confirmationTokenService.saveConfirmationToken(confirmationToken);

            log.info("confirmationToken: " + confirmationToken.toString());

            log.info("sendEmail");
            dataService.sendEmail(user.getUsername(), confirmationToken.getToken());

            return new ResponseEntity<>(mapResponse, HttpStatus.OK);
        } catch (Exception exception) {
            log.error(exception.toString());
            mapResponse = new HashMap<>();
            mapResponse.put("Error", errorMessage);
            return new ResponseEntity<>(mapResponse, httpStatus);
        }
    }

    @PostMapping("/register/confirm")
    public ArrayList<String> confirmUser(@RequestParam("token") String token){
        if (token.length()==36) {
            Optional<ConfirmationToken> optionalConfirmationToken = confirmationTokenService.findConfirmationTokenByToken(token);

            optionalConfirmationToken.ifPresent(userService::confirmUser);
        }
        return new ArrayList<String>();
    }
}
