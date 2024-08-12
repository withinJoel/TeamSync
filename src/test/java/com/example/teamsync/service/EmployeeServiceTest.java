package com.example.teamsync.service;

import com.example.teamsync.model.Employee;
import com.example.teamsync.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllEmployees() {
        Employee employee = new Employee();
        employee.setName("John Doe");
        when(employeeRepository.findAll()).thenReturn(Collections.singletonList(employee));

        List<Employee> employees = employeeService.getAllEmployees();
        assertEquals(1, employees.size());
        assertEquals("John Doe", employees.get(0).getName());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    public void testCreateEmployee() {
        Employee employee = new Employee();
        employee.setName("Jane Doe");
        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee result = employeeService.createEmployee(employee);
        assertEquals("Jane Doe", result.getName());
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    public void testDeleteEmployee() {
        Long employeeId = 1L;
        doNothing().when(employeeRepository).deleteById(employeeId);

        employeeService.deleteEmployee(employeeId);
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }
}
