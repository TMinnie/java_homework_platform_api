package com.school.homework_platform.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "assignments")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description; 

    private LocalDateTime createdAt; 

    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Submission> submissions = new ArrayList<>();

    @ManyToOne
    @JsonIgnore
    private Teacher teacher;

    // Constructors
    public Assignment() {}
    public Assignment(String name, String description, LocalDateTime createdAt) {
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
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
    public String getDescription() { 
        return description; 
    }
    public void setDescription(String description) { 
        this.description = description; 
    }
    public LocalDateTime getCreatedAt() { 
        return createdAt; 
    }
    public void setCreatedAt(LocalDateTime createdAt) { 
        this.createdAt = createdAt; 
    }
    public List<Submission> getSubmissions() { 
        return submissions; 
    }
    public void setSubmissions(List<Submission> submissions) { 
        this.submissions = submissions; 
    }
    public Teacher getTeacher() { 
        return teacher; 
    }
    public void setTeacher(Teacher teacher) { 
        this.teacher = teacher; 
    }
}
