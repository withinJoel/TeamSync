package com.example.teamsync.controller;

import com.example.teamsync.controller.assembler.EmployeeResourceAssembler;
import com.example.teamsync.controller.dto.EmployeeDto;
import com.example.teamsync.model.Employee;
import com.example.teamsync.controller.assembler.resource.EmployeeResource;
import com.example.teamsync.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeResourceAssembler assembler;

    //Supports API
    @GetMapping
    public ResponseEntity<?> getAllEmployees(@RequestHeader("API-Key") String apiKey) {
        if (isValidApiKey(apiKey)){
            try {
                List<Employee> employees = employeeService.getAllEmployees();
                List<EmployeeResource> employeeResources = employees.stream()
                        .map(assembler::toModel)
                        .collect(Collectors.toList());

                return ResponseEntity.ok(employeeResources);
            } catch (Exception e) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "An error occurred while fetching employees.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(errorResponse);
            }
        }
        return ResponseEntity.status(401).body("Invalid API Key");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(assembler.toModel(employee));
    }

    //Supports API
    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeDto employeeDto, @RequestHeader("API-Key") String apiKey) {
        if (isValidApiKey(apiKey)){
            Employee employee = new Employee();
            employee.setName(employeeDto.getName());
            employee.setDepartment(employeeDto.getDepartment());
            employee.setEmail(employeeDto.getEmail());
            employee.setToken(employeeDto.getToken());
            employee.setAge(employeeDto.getAge());

            Employee savedEmployee = employeeService.createEmployee(employee);
            return ResponseEntity.ok(assembler.toModel(savedEmployee));
        }
        return ResponseEntity.status(401).body("Invalid API Key");
    }

    //Supports API
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id, @RequestHeader("API-Key") String apiKey) {
        if (isValidApiKey(apiKey)){
            try {
                employeeService.deleteEmployee(id);
                return ResponseEntity.noContent().build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("An error occurred while deleting employee with ID " + id);
            }
        }
        return ResponseEntity.status(401).body("Invalid API Key");
    }
    private boolean isValidApiKey(String apiKey) {
        return employeeService.isValidToken(apiKey);
    }
}
