package com.example.teamsync.controller;

import com.example.teamsync.dto.EmployeeDto;
import com.example.teamsync.model.Employee;
import com.example.teamsync.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // List all employees using EmployeeDto
    @GetMapping
    public List<EmployeeDto> getAllEmployees() {
        // Fetch all employees
        List<Employee> employees = employeeService.getAllEmployees();

        // Convert Employee entities to EmployeeDto
        return employees.stream()
                .map(employee -> {
                    EmployeeDto dto = new EmployeeDto();
                    dto.setId(employee.getId().intValue()); // Convert Long to int
                    dto.setName(employee.getName());
                    dto.setEmail(employee.getEmail());
                    dto.setDepartment(employee.getDepartment());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // Create a new employee using Employee entity
    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) {
        // Convert EmployeeDto to Employee entity
        Employee employee = new Employee();
        employee.setName(employeeDto.getName());
        employee.setEmail(employeeDto.getEmail());
        employee.setDepartment(employeeDto.getDepartment());

        // Create the employee and convert it back to EmployeeDto
        Employee savedEmployee = employeeService.createEmployee(employee);

        EmployeeDto savedEmployeeDto = new EmployeeDto();
        savedEmployeeDto.setId(savedEmployee.getId().intValue());
        savedEmployeeDto.setName(savedEmployee.getName());
        savedEmployeeDto.setEmail(savedEmployee.getEmail());
        savedEmployeeDto.setDepartment(savedEmployee.getDepartment());

        return ResponseEntity.ok(savedEmployeeDto);
    }

    // Delete an employee by ID using Employee entity
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting employee with ID " + id);
        }
    }
}