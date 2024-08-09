package com.example.teamer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
    @GetMapping("/welcome")
    public String welcome() {
        return "welcome.html";
    }
}
