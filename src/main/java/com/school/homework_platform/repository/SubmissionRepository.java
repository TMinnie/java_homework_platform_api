package com.school.homework_platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.homework_platform.model.Submission;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    List<Submission> findByStudentId(Long studentId);
    List<Submission> findByTeacherId(Long teacherID);

    List<Submission> findByStudentIdAndAssignmentNameAndGrade(Long studentId, String assignmentName, Submission.Grade grade);

}