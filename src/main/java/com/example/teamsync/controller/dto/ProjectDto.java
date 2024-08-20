package com.example.teamsync.controller.dto;

import org.springframework.hateoas.RepresentationModel;

public class ProjectDto extends RepresentationModel<ProjectDto> {
    private String name;
    private String description;
    private Long employeeId;  // Ensure this field exists and is of type Long

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
}
