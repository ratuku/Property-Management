package com.example.Property.Management.controller;

import com.example.Property.Management.auth.User;
import com.example.Property.Management.auth.UserService;
import com.example.Property.Management.entity.ConfirmationToken;
import com.example.Property.Management.entity.RegistrationForm;
import com.example.Property.Management.service.ConfirmationTokenService;
import com.example.Property.Management.service.DataService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class HomeController {

    private final UserService userService;
    private final DataService dataService;
    private final ConfirmationTokenService confirmationTokenService;

    @GetMapping
    public String showHome() {
        return "home";
    }

    @PostMapping
    public String showHome2() {
        return "home";
    }

    @GetMapping("login")
    public String logIn() {return "login";}

    @GetMapping("register")
    public String register() {return "register";}

    @PostMapping("register")
    public String register(RegistrationForm registrationForm) {

        //dataService.registerUser(registrationForm);
        return "redirect:/register";
    }

    @GetMapping("/confirm")
    String confirmMail(@RequestParam("token") String token) {
        if (token.length()==36) {
            Optional<ConfirmationToken> optionalConfirmationToken = confirmationTokenService.findConfirmationTokenByToken(token);

            optionalConfirmationToken.ifPresent(userService::confirmUser);
        }

        return "/sign-in";
    }

}
