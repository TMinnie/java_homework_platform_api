package com.school.homework_platform.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.homework_platform.model.Submission;
import com.school.homework_platform.repository.SubmissionRepository;

@Service
public class TeacherService {
    private final SubmissionRepository submissionRepository;

    @Autowired
    public TeacherService(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    // Grade individual homework submissions (A-F, comments/notes)
    public Submission gradeSubmission(Long submissionID, Submission.Grade grade, String teacherNotes){
        
        Submission submission = submissionRepository.findById(submissionID)
        .orElseThrow(() -> new IllegalArgumentException("Submission not found with id: " + submissionID));

        submission.setGrade(grade);
        submission.setTeacherNotes(teacherNotes);
        submission.setGradedAt(LocalDateTime.now());

        return submissionRepository.save(submission);
    }
    
    //List Submissions with Filters - student name, assignment name, date range
    public List<Submission> getSubmissions(Long teacherID, String studentName, String assignmentName, LocalDateTime startDate, LocalDateTime endDate){

        List<Submission> submissions = submissionRepository.findByTeacherId(teacherID);

        if (studentName != null){
            submissions = submissions.stream()
                             .filter(s -> s.getStudent() != null && s.getStudent().getName().equalsIgnoreCase(studentName))
                             .collect(Collectors.toList());
        }
 
        if (assignmentName != null){
            submissions = submissions.stream()
                             .filter(s -> s.getAssignment() != null && s.getAssignment().getName().equalsIgnoreCase(assignmentName))
                             .collect(Collectors.toList());
        }

        if (startDate != null && endDate!= null){
            submissions = submissions.stream()
                            .filter(s -> s.getSubmittedAt() != null &&
                                        !s.getSubmittedAt().isBefore(startDate) &&
                                        !s.getSubmittedAt().isAfter(endDate))
                            .collect(Collectors.toList());
        }

        return submissions;
    }

}
