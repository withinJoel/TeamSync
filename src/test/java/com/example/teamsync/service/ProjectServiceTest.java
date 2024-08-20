package com.example.teamsync.service;

import com.example.teamsync.controller.dto.ProjectDto;
import com.example.teamsync.model.Employee;
import com.example.teamsync.model.Project;
import com.example.teamsync.repository.EmployeeRepository;
import com.example.teamsync.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllProjects() {
        Project project = new Project();
        project.setName("Project A");
        when(projectRepository.findAll()).thenReturn(Collections.singletonList(project));

        List<Project> projects = projectService.getAllProjects();
        assertEquals(1, projects.size());
        assertEquals("Project A", projects.get(0).getName());
        verify(projectRepository, times(1)).findAll();
    }

    @Test
    public void testAddProject() {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setName("Project B");
        projectDto.setDescription("Description of Project B");
        projectDto.setEmployeeId(1L);

        Employee employee = new Employee();
        employee.setId(1L);

        Project project = new Project();
        project.setName("Project B");
        project.setDescription("Description of Project B");
        project.setEmployee(employee);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        Project result = projectService.addProject(projectDto);
        assertEquals("Project B", result.getName());
        assertEquals("Description of Project B", result.getDescription());
        assertEquals(employee, result.getEmployee());
        verify(employeeRepository, times(1)).findById(1L);
        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    public void testAddProjectEmployeeNotFound() {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setName("Project C");
        projectDto.setDescription("Description of Project C");
        projectDto.setEmployeeId(1L);

        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            projectService.addProject(projectDto);
        });
        assertEquals("Employee not found", thrown.getMessage());
        verify(employeeRepository, times(1)).findById(1L);
        verify(projectRepository, never()).save(any(Project.class));
    }

    @Test
    public void testGetProjectById() {
        Project project = new Project();
        project.setName("Project D");
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        Project result = projectService.getProjectById(1L);
        assertEquals("Project D", result.getName());
        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetProjectByIdNotFound() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            projectService.getProjectById(1L);
        });
        assertEquals("Project not found with id 1", thrown.getMessage());
        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateProject() {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setName("Updated Project");
        projectDto.setDescription("Updated Description");
        projectDto.setEmployeeId(1L);

        Employee employee = new Employee();
        employee.setId(1L);

        Project existingProject = new Project();
        existingProject.setName("Old Project");

        Project updatedProject = new Project();
        updatedProject.setName("Updated Project");
        updatedProject.setDescription("Updated Description");
        updatedProject.setEmployee(employee);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(existingProject));
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(projectRepository.save(existingProject)).thenReturn(updatedProject);

        Project result = projectService.updateProject(1L, projectDto);
        assertEquals("Updated Project", result.getName());
        assertEquals("Updated Description", result.getDescription());
        assertEquals(employee, result.getEmployee());
        verify(projectRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).findById(1L);
        verify(projectRepository, times(1)).save(existingProject);
    }

    @Test
    public void testUpdateProjectNotFound() {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setName("Project E");

        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            projectService.updateProject(1L, projectDto);
        });
        assertEquals("Project not found", thrown.getMessage());
        verify(projectRepository, times(1)).findById(1L);
        verify(projectRepository, never()).save(any(Project.class));
    }

    @Test
    public void testDeleteProject() {
        Long projectId = 1L;
        doNothing().when(projectRepository).deleteById(projectId);

        projectService.deleteProject(projectId);
        verify(projectRepository, times(1)).deleteById(projectId);
    }
}