package com.example.teamsync.repository;

import com.example.teamsync.model.Employee;
import com.example.teamsync.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByEmployee(Employee employee);
}