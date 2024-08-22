package com.example.teamsync.service;

import com.example.teamsync.controller.dto.ProjectDto;
import com.example.teamsync.model.Employee;
import com.example.teamsync.model.Project;
import com.example.teamsync.repository.EmployeeRepository;
import com.example.teamsync.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public List<Project> getProjectsByEmployeeId(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id " + employeeId));

        return projectRepository.findByEmployee(employee);
    }

    public Project addProject(ProjectDto projectDto) {
        Project project = new Project();
        project.setName(projectDto.getName());
        project.setStatus(projectDto.getStatus());
        project.setStart(projectDto.getStart());
        project.setEnd(projectDto.getEnd());
        project.setDescription(projectDto.getDescription());

        if (projectDto.getEmployeeId() != null) {
            Employee employee = employeeRepository.findById(projectDto.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));
            project.setEmployee(employee);
        }

        return projectRepository.save(project);
    }

    public Project getProjectById(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            return project.get();
        } else {
            throw new RuntimeException("Project not found with id " + id);
        }
    }

    public Project updateProject(Long id, ProjectDto projectDto) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());

        if (projectDto.getEmployeeId() != null) {
            Employee employee = employeeRepository.findById(projectDto.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));
            project.setEmployee(employee);
        }

        return projectRepository.save(project);
    }

    public Project test (String department) {
        Project project = projectRepository.findByDescriptionAndId(department, 1L);
        return project;
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}