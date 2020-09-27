package com.example.Property.Management.controller;

import com.example.Property.Management.auth.UserService;
import com.example.Property.Management.entity.ConfirmationToken;
import com.example.Property.Management.entity.RegistrationForm;
import com.example.Property.Management.jwt.JwtConfig;
import com.example.Property.Management.service.ConfirmationTokenService;
import com.example.Property.Management.service.DataService;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.Date;
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
        String data = dataService.getUserFullInfo(authResult.getName());

        model.addAttribute("data", data);
        model.addAttribute("token", token);

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

        return "index";
    }

}
