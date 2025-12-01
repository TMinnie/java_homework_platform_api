package com.school.homework_platform.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.homework_platform.model.Submission;
import com.school.homework_platform.repository.SubmissionRepository;

@Service
public class StudentService {

    private final SubmissionRepository submissionRepository;

    @Autowired
    public StudentService(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    //Submit Homework
    public Submission uploadSubmission(Submission submission){
        submission.setSubmittedAt(LocalDateTime.now());
        submission.setGradedAt(null);
        submission.setGrade(null);
        submission.setTeacherNotes(null);
        return submissionRepository.save(submission);
    }

    //List Submissions with Filters - grade, assignment name
    public List<Submission> getSubmissions(Long studentId, String assignmentName, Submission.Grade grade){

        List<Submission> submissions = submissionRepository.findByStudentId(studentId);

        if (assignmentName != null){
            submissions = submissions.stream()
                             .filter(s -> s.getAssignment() != null && s.getAssignment().getName().equalsIgnoreCase(assignmentName))
                             .collect(Collectors.toList());
        }

        if (grade != null){
            submissions = submissions.stream()
                             .filter(s -> s.getGrade() == grade)
                             .collect(Collectors.toList());
        }

        return submissions;
    }

    //Get specific submission
    public Optional<Submission> getSubmissionById(Long id) {
    return submissionRepository.findById(id);
}


}
