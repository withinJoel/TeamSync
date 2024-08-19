package com.example.teamsync.controller;

import com.example.teamsync.assembler.EmployeeResourceAssembler;
import com.example.teamsync.dto.EmployeeDto;
import com.example.teamsync.model.Employee;
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

    // List all employees using EmployeeDto
    @GetMapping
    public ResponseEntity<?> getAllEmployees() {
        try {
            List<Employee> employees = employeeService.getAllEmployees();
            List<EmployeeDto> employeeDtos = employees.stream()
                    .map(assembler::toModel)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(employeeDtos);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "An error occurred while fetching employees.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }

    // Get an employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(assembler.toModel(employee));
    }

    // Create a new employee using Employee entity
    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setName(employeeDto.getName());
        employee.setDepartment(employeeDto.getDepartment());
        employee.setEmail(employeeDto.getEmail());
        employee.setAge(employeeDto.getAge());

        Employee savedEmployee = employeeService.createEmployee(employee);
        return ResponseEntity.ok(assembler.toModel(savedEmployee));
    }


    // Delete an employee by ID using Employee entity
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting employee with ID " + id);
        }
    }
}