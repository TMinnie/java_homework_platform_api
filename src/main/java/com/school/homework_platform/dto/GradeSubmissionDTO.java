package com.school.homework_platform.dto;

import com.school.homework_platform.model.Submission;

import jakarta.validation.constraints.NotNull;

public class GradeSubmissionDTO {

    @NotNull(message = "Grade is required")
    private Submission.Grade grade;

    private String teacherNotes;

    // Constructors
    public GradeSubmissionDTO() {}

    public GradeSubmissionDTO(Submission.Grade grade, String teacherNotes) {
        this.grade = grade;
        this.teacherNotes = teacherNotes;
    }

    // Getters and Setters
    public Submission.Grade getGrade() {
        return grade;
    }
    public void setGrade(Submission.Grade grade) {
        this.grade = grade;
    }

    public String getTeacherNotes() {
        return teacherNotes;
    }
    public void setTeacherNotes(String teacherNotes) {
        this.teacherNotes = teacherNotes;
    }
}
