package com.school.homework_platform.dto;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.school.homework_platform.model.Assignment;
import com.school.homework_platform.model.Student;
import com.school.homework_platform.model.Submission;
import com.school.homework_platform.repository.AssignmentRepository;

import jakarta.validation.constraints.NotNull;

public class SubmissionRequestDTO {

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Assignment ID is required")
    private Long assignmentId;

    @NotNull(message = "File is required")
    private MultipartFile file;

    // Constructors
    public SubmissionRequestDTO() {}

    public SubmissionRequestDTO(Long studentId, Long assignmentId, MultipartFile file) {
        this.studentId = studentId;
        this.assignmentId = assignmentId;
        this.file = file;
    }

    // Getters and Setters
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public Long getAssignmentId() { return assignmentId; }
    public void setAssignmentId(Long assignmentId) { this.assignmentId = assignmentId; }


    public MultipartFile getFile() { return file; }
    public void setFile(MultipartFile file) { this.file = file; }

    // Conversion method
    @Autowired
    private AssignmentRepository assignmentRepository;

    public Submission toSubmission(Student student) {
        Submission submission = new Submission();
        submission.setStudent(student);

        Assignment assignment = assignmentRepository.findById(this.assignmentId)
            .orElseThrow(() -> new IllegalArgumentException("Assignment not found"));
        submission.setAssignment(assignment);

        submission.setFilePath(this.file.getOriginalFilename());
        
        submission.setSubmittedAt(LocalDateTime.now());
        return submission;
    }

}
