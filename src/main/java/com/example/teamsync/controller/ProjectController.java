package com.example.teamsync.controller;

import com.example.teamsync.controller.assembler.ProjectResourceAssembler;
import com.example.teamsync.controller.assembler.resource.EmployeeResource;
import com.example.teamsync.controller.dto.ProjectDto;
import com.example.teamsync.model.Employee;
import com.example.teamsync.model.Project;
import com.example.teamsync.controller.assembler.resource.ProjectResource;
import com.example.teamsync.service.EmployeeService;
import com.example.teamsync.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ProjectResourceAssembler assembler;

    //Requires Support
    @GetMapping
    public ResponseEntity<CollectionModel<ProjectResource>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        List<ProjectResource> projectResources = projects.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<ProjectResource> projectResourceCollection = CollectionModel.of(projectResources);
        projectResourceCollection.add(linkTo(methodOn(ProjectController.class).getAllProjects()).withSelfRel());

        return ResponseEntity.ok(projectResourceCollection);
    }

    //Requires Support
    @GetMapping("/{id}")
    public ResponseEntity<ProjectResource> getProjectById(@PathVariable Long id) {
            Project project = projectService.getProjectById(id);
            return ResponseEntity.ok(assembler.toModel(project));
    }

    //Supports API
    @PostMapping
    public ResponseEntity<?> addProject(@RequestBody ProjectDto projectDto, @RequestHeader("API-Key") String apiKey) {
        if (isValidApiKey(apiKey)) {
            Project project = projectService.addProject(projectDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(project));
        }
        return ResponseEntity.status(401).body("Invalid API Key");
    }

    //Supports API
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(@PathVariable Long id, @RequestBody ProjectDto projectDto,  @RequestHeader("API-Key") String apiKey) {
        if (isValidApiKey(apiKey)){
            Project project = projectService.updateProject(id, projectDto);
            return ResponseEntity.ok(assembler.toModel(project));
        }
        return ResponseEntity.status(401).body("Invalid API Key");
    }

    //Supports API
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id, @RequestHeader("API-Key") String apiKey) {
        if (isValidApiKey(apiKey)){
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(401).body("Invalid API Key");
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<CollectionModel<ProjectResource>> getProjectsByEmployeeId(@PathVariable Long employeeId) {
        List<Project> projects = projectService.getProjectsByEmployeeId(employeeId);
        List<ProjectResource> projectResources = projects.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<ProjectResource> projectResourceCollection = CollectionModel.of(projectResources);
        projectResourceCollection.add(linkTo(methodOn(ProjectController.class).getProjectsByEmployeeId(employeeId)).withSelfRel());

        return ResponseEntity.ok(projectResourceCollection);
    }

    //Experimental Features
    @GetMapping("/departments/{department}")
    public ResponseEntity<ProjectResource> getProjectByDepartment(@PathVariable String department) {
        Project project = projectService.findByDescription(department);
        return ResponseEntity.ok(assembler.toModel(project));
    }

    @GetMapping ("/departments/skip/{department}")
    public ResponseEntity<ProjectResource> getProjectByDepartmentSkip(@PathVariable String department) {
        Project project = projectService.findBySkippedDescription(department);
        return ResponseEntity.ok(assembler.toModel(project));
    }

    @GetMapping("/departments/skip/{department}/{name}")
    public ResponseEntity<ProjectResource> findBySkippedDescriptionAndName(@PathVariable String department, @PathVariable String name) {
        System.out.println(department);
        System.out.println(name);
        Project project = projectService.findBySkippedDescriptionAndName(department, name);
        return ResponseEntity.ok(assembler.toModel(project));
    }

    private boolean isValidApiKey(String apiKey) {
        return employeeService.isValidToken(apiKey);
    }
}