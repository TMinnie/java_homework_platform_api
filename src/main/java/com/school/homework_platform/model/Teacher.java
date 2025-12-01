package com.school.homework_platform.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Assignment> assignments = new ArrayList<>();

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Submission> gradedSubmissions = new ArrayList<>();

    // Constructors
    public Teacher() {}
    public Teacher(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Getters and setters
    public Long getId() { 
        return id; 
    }
    public void setId(Long id) { 
        this.id = id; 
    }
    public String getName() { 
        return name; 
    }
    public void setName(String name) { 
        this.name = name; 
    }
    public String getEmail() { 
        return email; 
    }
    public void setEmail(String email) { 
        this.email = email; 
    }
    public List<Assignment> getAssignment() { 
        return assignments; 
    }
    public void setAssignment(List<Assignment> assignments) { 
        this.assignments = assignments; 
    }
    public List<Submission> getSubmission() { 
        return gradedSubmissions; 
    }
    public void setSubmission(List<Submission> gradedSubmissions) { 
        this.gradedSubmissions = gradedSubmissions; 
    }
    
}