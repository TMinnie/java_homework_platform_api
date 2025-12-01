package com.school.homework_platform.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.homework_platform.model.Submission;
import com.school.homework_platform.repository.SubmissionRepository;

@Service
public class SubmissionService {
    private final SubmissionRepository submissionRepository;
    
    @Autowired
    public SubmissionService(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    //List All Submissions
    public List<Submission> getAllSubmissions() {
        return submissionRepository.findAll();
    }

    //Find Submission by ID
    public Optional<Submission> getSubmissionById(Long id) {
        return submissionRepository.findById(id);
    }

    //Save Submission
    public Submission saveSubmission(Submission submission) {
        return submissionRepository.save(submission);
    }

    //Delete Submission by ID
    public void deleteSubmission(Long id) {
        submissionRepository.deleteById(id);
    }

}