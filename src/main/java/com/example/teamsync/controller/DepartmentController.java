package com.example.teamsync.controller;

import com.example.teamsync.controller.dto.DepartmentDto;
import com.example.teamsync.service.DepartmentService;
import com.example.teamsync.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.teamsync.model.Department;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    //Supports API
    @GetMapping("/{id}")
    public ResponseEntity<?> getDepartmentById(@PathVariable Long id, @RequestHeader("API-Key") String apiKey) {
        if (isValidApiKey(apiKey)) {
            return departmentService.getDepartmentById(id)
                    .map(department -> ResponseEntity.ok(departmentService.convertToDto(department)))
                    .orElse(ResponseEntity.notFound().build());
        }
        return ResponseEntity.status(401).body("Invalid API Key");
    }

    //Supports API
    @PostMapping
    public ResponseEntity<?> createDepartment(@RequestBody DepartmentDto departmentDto, @RequestHeader("API-Key") String apiKey) {
        if (isValidApiKey(apiKey)) {
            return ResponseEntity.ok(departmentService.createDepartment(departmentDto));
        }
        return ResponseEntity.status(401).body("Invalid API Key");
    }

    //Supports API
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDepartment(@PathVariable Long id, @RequestBody DepartmentDto departmentDto, @RequestHeader("API-Key") String apiKey) {
        if (isValidApiKey(apiKey)) {
            return ResponseEntity.ok(departmentService.updateDepartment(id, departmentDto));
        }
        return ResponseEntity.status(401).body("Invalid API Key");
    }

    //Supports API
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable Long id, @RequestHeader("API-Key") String apiKey) {
        if (isValidApiKey(apiKey)) {
            departmentService.deleteDepartment(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(401).body("Invalid API Key");
    }

    private boolean isValidApiKey(String apiKey) {
        return employeeService.isValidToken(apiKey);
    }
}