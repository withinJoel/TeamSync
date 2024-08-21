package com.example.teamsync.controller.assembler;

import com.example.teamsync.controller.ProjectController;
import com.example.teamsync.model.Project;
import com.example.teamsync.controller.assembler.resource.ProjectResource;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProjectResourceAssembler implements RepresentationModelAssembler<Project, ProjectResource> {

    @Override
    public ProjectResource toModel(Project project) {
        ProjectResource resource = new ProjectResource();
        resource.setId(project.getId());
        resource.setName(project.getName());
        resource.setStatus(project.getStatus());
        resource.setDescription(project.getDescription());

        // Set employeeId if needed
        if (project.getEmployee() != null) {
            resource.setEmployeeId(project.getEmployee().getId());
        }

        // Add self link
        resource.add(linkTo(methodOn(ProjectController.class).getProjectById(project.getId())).withSelfRel());

        return resource;
    }
}
