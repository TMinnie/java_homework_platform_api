package com.school.homework_platform.dto;

import java.time.LocalDateTime;

import com.school.homework_platform.model.Submission;

public class SubmissionResponseDTO {
    final String assignmentName;
    final String studentName;
    final LocalDateTime submittedAt;
    final Submission.Grade grade;
    final String teacherNotes;

    public SubmissionResponseDTO(Submission submission) {
        this.assignmentName = submission.getAssignment().getName();
        this.studentName = submission.getStudent().getName();
        this.submittedAt = submission.getSubmittedAt();
        this.grade = submission.getGrade();
        this.teacherNotes = submission.getTeacherNotes();
    }

    // Getters 
    public String getAssignmentName() { return assignmentName; }
    public String getStudentName() { return studentName; }
    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public Submission.Grade getGrade() { return grade; }
    public String getTeacherNotes() { return teacherNotes; }
}
