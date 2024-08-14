package com.example.teamsync.assembler;

import com.example.teamsync.controller.EmployeeController;
import com.example.teamsync.dto.EmployeeDto;
import com.example.teamsync.model.Employee;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EmployeeResourceAssembler extends RepresentationModelAssemblerSupport<Employee, EmployeeDto> {

    public EmployeeResourceAssembler() {
        super(EmployeeController.class, EmployeeDto.class);
    }

    @Override
    public EmployeeDto toModel(Employee employee) {
        EmployeeDto dto = new EmployeeDto();
        dto.setId(employee.getId().intValue());
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        dto.setDepartment(employee.getDepartment());

        // Add self link to the DTO
        dto.add(linkTo(methodOn(EmployeeController.class).getEmployeeById(employee.getId())).withSelfRel());

        return dto;
    }
}
