package com.example.teamsync.dto;

import org.springframework.hateoas.RepresentationModel;

public class EmployeeDto extends RepresentationModel<EmployeeDto> {
    private long id;
    private String name;
    private int age;  // Ensure this is the correct type
    private String email;
    private String department;

    // Getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;  // Getter must be public
    }

    public void setAge(int age) {
        this.age = age;  // Setter must be public
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}