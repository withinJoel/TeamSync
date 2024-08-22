package com.example.teamsync.repository;

import com.example.teamsync.model.Employee;
import com.example.teamsync.model.Project;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByEmployee(Employee employee);

    //Experimental Features
    //(Not For production)(Logic might be choppy)
    @Query ("select p from Project p where p.description = :department and p.id = :id")
    Project findByDescriptionAndId(String department, Long id);
}