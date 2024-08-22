package com.example.teamsync.controller.assembler;

import com.example.teamsync.controller.EmployeeController;
import com.example.teamsync.controller.assembler.resource.EmployeeResource;
import com.example.teamsync.model.Employee;
import com.example.teamsync.controller.assembler.resource.ProjectResource;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EmployeeResourceAssembler extends RepresentationModelAssemblerSupport<Employee, EmployeeResource> {

    private final ProjectResourceAssembler projectResourceAssembler;

    public EmployeeResourceAssembler(ProjectResourceAssembler projectResourceAssembler) {
        super(EmployeeController.class, EmployeeResource.class);
        this.projectResourceAssembler = projectResourceAssembler;
    }

    @Override
    public EmployeeResource toModel(Employee employee) {
        EmployeeResource resource = new EmployeeResource();
        resource.setId(employee.getId());
        resource.setName(employee.getName());
        resource.setAge(employee.getAge());
        resource.setEmail(employee.getEmail());
        resource.setDepartment(employee.getDepartment());
        resource.setToken(employee.getToken());

        // Populate the projects field
        List<ProjectResource> projectResources = employee.getProjects().stream()
                .map(projectResourceAssembler::toModel)
                .collect(Collectors.toList());
        resource.setProjects(projectResources);

        // Add self link
        resource.add(linkTo(methodOn(EmployeeController.class).getEmployeeById(employee.getId())).withSelfRel());

        return resource;
    }
}
