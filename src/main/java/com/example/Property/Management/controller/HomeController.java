package com.example.Property.Management.controller;

import com.example.Property.Management.auth.User;
import com.example.Property.Management.auth.UserService;
import com.example.Property.Management.dto.UserDto;
import com.example.Property.Management.entity.ConfirmationToken;
import com.example.Property.Management.entity.Owner;
import com.example.Property.Management.entity.RegistrationForm;
import com.example.Property.Management.jwt.JwtConfig;
import com.example.Property.Management.repository.OwnerRepository;
import com.example.Property.Management.service.ConfirmationTokenService;
import com.example.Property.Management.service.DataService;
import com.example.Property.Management.utility.Converter;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/")
@AllArgsConstructor
@Slf4j
public class HomeController {

    private final UserService userService;
    private final DataService dataService;
    private final ConfirmationTokenService confirmationTokenService;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;
    private final OwnerRepository ownerRepository;

    @GetMapping("/home")
    public String showHome(Authentication authResult,
                           Model model) {
        log.info("Post signin");

        //Create Token
        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
                .signWith(secretKey)
                .compact();

        dataService.setUserJWTToken(token, authResult.getName());
        //String data = dataService.getUserFullInfo(authResult.getName());

        //model.addAttribute("data", data);
        model.addAttribute("token", token);
        model.addAttribute("email", authResult.getName());

        return "index";
    }

    @GetMapping("login")
    public String logIn() {

        log.info("here");

        return "login";}

    @GetMapping
    public String landingPage(){
        return "landingPage";
    }

 /*   @GetMapping
    public String landingPage() {return "login2";}
*/
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("form", new RegistrationForm());
        return "register";}

    @PostMapping("/register")
    public String register(@ModelAttribute("form")  RegistrationForm form) throws Exception {

        log.info("form API: ");

        log.info("form: " + form);

        Map<String, Object> mapResponse = new HashMap<>();
        String errorMessage = "Error while trying to save new user";
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        log.info("form API: " + form);
        Owner owner = form.getOwner();
        User user = form.getUser();

        log.info("form API: " + owner);
        log.info("form API: " + user);

        // Check that email is unique
        if(!userService.isEmailUnique(user.getUsername())) {
            errorMessage = "Email already exist";
            httpStatus = HttpStatus.CONFLICT;
            throw new Exception(errorMessage);
        }

        owner = ownerRepository.save(owner);
        user.setOwner(owner);
        user.encodePassword();
        user.setOwner(owner);
        user = userService.saveUser(user);

        //Save confirmation token
        log.info("create confirmationToken");
        final ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        log.info("confirmationToken: " + confirmationToken.toString());

        log.info("sendEmail");
        dataService.sendEmail(user.getUsername(), confirmationToken.getToken());

        //dataService.registerUser(registrationForm);
        return "registrationWaitingForConfirmation";
    }

    @GetMapping("/register/confirm")
    String confirmMail(@RequestParam("token") String token) {
        if (token.length()==36) {
            Optional<ConfirmationToken> optionalConfirmationToken = confirmationTokenService.findConfirmationTokenByToken(token);

            optionalConfirmationToken.ifPresent(userService::confirmUser);
        }

        return "registrationConfirmed";
    }

}
