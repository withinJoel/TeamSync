package com.example.teamsync.model;

import jakarta.persistence.*;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Added to auto-generate IDs
    private Long id;
    private String name;
    private int age;
    private String email;
    private String department;

    // Getters and Setters
    public Long getId() {
        return id; // Return Long
    }

    public void setId(Long id) {
        this.id = id; // Accept Long
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age){
        this.age = age;
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