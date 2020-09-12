package com.example.Property.Management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String showHome() {
        return "home";
    }

    @GetMapping("login")
    public String logIn() {return "login";}

    @GetMapping("register")
    public String register() {return "register";}

}
