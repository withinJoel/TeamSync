package com.example.teamer.controller;

import com.example.teamer.dto.EmployeeDTO;
import com.example.teamer.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AppController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome.html";
    }
    @GetMapping("/loginauth")
    public String loginauth(@RequestBody EmployeeDTO employeeDTO) {
        if (employeeService.login(employeeDTO.getName(), employeeDTO.getEmail())) {
            return "feed.html";
        } else {
            return "login.html?message=Incorrect email or password";
        }
    }
    @GetMapping ("/registerauth")
    public String registerauth(@RequestBody EmployeeDTO employeeDTO) {
        if(employeeService.register(employeeDTO.getName(), employeeDTO.getEmail())) {
            return "feed.html";
        } else {
            return "login.html?message=Incorrect email or password";
        }
    }
}
