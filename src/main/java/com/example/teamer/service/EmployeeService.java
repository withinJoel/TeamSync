package com.example.teamer.service;

import com.example.teamer.repository.EmployeeRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;


    public boolean register(String name, String email) {
        try {
            User User = new User(name, email);
            employeeRepository.save(User);
            return true;
        } catch (Exception e) {
            // Log the exception instead of printing
            e.printStackTrace();
            return false;
        }
    }

    public boolean login(String email, String password) {
        try {
            User user = employeeRepository.findByEmail(email);
            return user != null && user.getPassword().equals(password);
        } catch (Exception e) {
            // Log the exception instead of printing
            e.printStackTrace();
            return false;
        }
    }
}