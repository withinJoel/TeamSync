package com.example.teamsync.service;

import com.example.teamsync.model.Department;
import com.example.teamsync.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.Collections;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllDepartments() {
        Department department = new Department();
        department.setName("HR");
        when(departmentRepository.findAll()).thenReturn(Collections.singletonList(department));

        List<Department> departments = departmentService.getAllDepartments();
        assertEquals(1, departments.size());
        assertEquals("HR", departments.get(0).getName());
        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    public void testGetDepartmentById() {
        Department department = new Department();
        department.setName("IT");
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        Optional<Department> result = departmentService.getDepartmentById(1L);
        assertEquals("IT", result.get().getName());
        verify(departmentRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateDepartment() {
        Department department = new Department();
        department.setName("Finance");
        when(departmentRepository.save(department)).thenReturn(department);

        Department result = departmentService.createDepartment(department);
        assertEquals("Finance", result.getName());
        verify(departmentRepository, times(1)).save(department);
    }

    @Test
    public void testUpdateDepartment() {
        Department existingDepartment = new Department();
        existingDepartment.setName("Marketing");
        Department updatedDepartment = new Department();
        updatedDepartment.setName("Sales");

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(existingDepartment));
        when(departmentRepository.save(existingDepartment)).thenReturn(existingDepartment);

        Department result = departmentService.updateDepartment(1L, updatedDepartment);
        assertEquals("Sales", result.getName());
        verify(departmentRepository, times(1)).findById(1L);
        verify(departmentRepository, times(1)).save(existingDepartment);
    }

    @Test
    public void testUpdateDepartmentNotFound() {
        Department department = new Department();
        department.setName("Logistics");

        when(departmentRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            departmentService.updateDepartment(1L, department);
        });
        assertEquals("Department not found with id 1", thrown.getMessage());
        verify(departmentRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteDepartment() {
        Long departmentId = 1L;
        doNothing().when(departmentRepository).deleteById(departmentId);

        departmentService.deleteDepartment(departmentId);
        verify(departmentRepository, times(1)).deleteById(departmentId);
    }
}