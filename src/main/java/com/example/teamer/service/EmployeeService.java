package com.example.teamer.service;

import com.example.teamer.model.Employee;
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
            Employee employee = new Employee(name, email);
            employeeRepository.save(employee);
            return true;
        } catch (Exception e) {
            // Log the exception instead of printing
            e.printStackTrace();
            return false;
        }
    }

    public boolean login(String email, String password) {
        try {
            Employee employee = employeeRepository.findByEmail(email);
            return employee != null && employee.getEmail().equals(email);
        } catch (Exception e) {
            // Log the exception instead of printing
            e.printStackTrace();
            return false;
        }
    }
}