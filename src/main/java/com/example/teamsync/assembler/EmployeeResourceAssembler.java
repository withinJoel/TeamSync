package com.example.teamsync.assembler;

import com.example.teamsync.controller.EmployeeController;
import com.example.teamsync.dto.EmployeeDto;
import com.example.teamsync.model.Employee;
import org.springframework.beans.BeanUtils;
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
        // Automatically copy properties from Employee to EmployeeDto
        EmployeeDto dto = new EmployeeDto();
        BeanUtils.copyProperties(employee, dto);

        // Add self link to the DTO
        dto.add(linkTo(methodOn(EmployeeController.class).getEmployeeById(employee.getId())).withSelfRel());

        return dto;
    }
}