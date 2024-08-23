package com.example.teamsync.service;

import com.example.teamsync.controller.dto.DepartmentDto;
import com.example.teamsync.model.Department;
import com.example.teamsync.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }

    public DepartmentDto createDepartment(DepartmentDto departmentDto) {
        Department department = convertToEntity(departmentDto);
        Department savedDepartment = departmentRepository.save(department);
        return convertToDto(savedDepartment);
    }

    public DepartmentDto updateDepartment(Long id, DepartmentDto departmentDto) {
        return departmentRepository.findById(id)
                .map(department -> {
                    department.setName(departmentDto.getName());
                    Department updatedDepartment = departmentRepository.save(department);
                    return convertToDto(updatedDepartment);
                })
                .orElseThrow(() -> new RuntimeException("Department not found with id " + id));
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    // Conversion methods
    public DepartmentDto convertToDto(Department department) {
        DepartmentDto dto = new DepartmentDto();
        dto.setName(department.getName());
        return dto;
    }

    private Department convertToEntity(DepartmentDto dto) {
        Department department = new Department();
        department.setName(dto.getName());
        return department;
    }
}