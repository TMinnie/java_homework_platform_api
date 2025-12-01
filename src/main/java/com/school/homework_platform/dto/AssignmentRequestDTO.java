package com.school.homework_platform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AssignmentRequestDTO {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Teacher ID is required")
    private Long teacherId;

    // Constructors
    public AssignmentRequestDTO() {}

    public AssignmentRequestDTO(String title, String description, Long teacherId) {
        this.title = title;
        this.description = description;
        this.teacherId = teacherId;
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }
}
