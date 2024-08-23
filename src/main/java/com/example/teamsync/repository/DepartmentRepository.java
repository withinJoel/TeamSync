// DepartmentRepository.java
package com.example.teamsync.repository;

import com.example.teamsync.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

}