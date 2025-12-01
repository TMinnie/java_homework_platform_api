package com.school.homework_platform.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "submissions")
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filePath;

    private LocalDateTime submittedAt;

    private LocalDateTime gradedAt;

    public enum Grade {
        A, B, C, D, E, F
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Grade grade; //(A-F, nullable initially)

    @Column(nullable = true)
    private String teacherNotes; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Student student;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    @JsonIgnore
    private Teacher teacher;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Assignment assignment;

    public Submission() {}

    // Constructors
    public Submission(String filePath, LocalDateTime submittedAt, LocalDateTime gradedAt,
                  Grade grade, String teacherNotes, Student student, Teacher teacher, Assignment assignment) {
        this.filePath = filePath;
        this.submittedAt = submittedAt;
        this.gradedAt = gradedAt;
        this.grade = grade;
        this.teacherNotes = teacherNotes;
        this.student = student;
        this.teacher = teacher;
        this.assignment = assignment;
    }


    // Getters and setters
    public Long getId() { 
        return id; 
    }
    public void setId(Long id) { 
        this.id = id; 
    }
    public String getFilePath() { 
        return filePath; 
    }
    public void setFilePath(String filePath) { 
        this.filePath = filePath; 
    }
    public LocalDateTime getSubmittedAt() { 
        return submittedAt; 
    }
    public void setSubmittedAt(LocalDateTime submittedAt) { 
        this.submittedAt = submittedAt; 
    }
    public LocalDateTime getGradedAt() { 
        return gradedAt; 
    }
    public void setGradedAt(LocalDateTime gradedAt) { 
        this.gradedAt = gradedAt; 
    }
    public Grade getGrade() { 
        return grade; 
    }
    public void setGrade(Grade grade) { 
        this.grade = grade; 
    } 
    public String getTeacherNotes() { 
        return teacherNotes; 
    }
    public void setTeacherNotes(String teacherNotes) { 
        this.teacherNotes = teacherNotes; 
    }
    public Student getStudent() { 
        return student; 
    }
    public void setStudent(Student student) { 
        this.student = student; 
    }
    public Teacher getTeacher() { 
        return teacher; 
    }
    public void setTeacher(Teacher teacher) { 
        this.teacher = teacher; 
    }
    public Assignment getAssignment() { 
        return assignment; 
    }
    public void setAssignment(Assignment assignment) { 
        this.assignment = assignment; 
    }

}