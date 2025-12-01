package com.school.homework_platform.service;

import com.school.homework_platform.model.Submission;
import com.school.homework_platform.repository.SubmissionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SubmissionServiceTest {

    @Mock
    private SubmissionRepository submissionRepository;

    @InjectMocks
    private SubmissionService submissionService;

    private Submission submission;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        submission = new Submission();
        submission.setId(1L);
    }

    @Test
    void testGetAllSubmissions() {
        when(submissionRepository.findAll()).thenReturn(List.of(submission));

        List<Submission> result = submissionService.getAllSubmissions();

        assertEquals(1, result.size());
        assertEquals(submission, result.get(0));
        verify(submissionRepository, times(1)).findAll();
    }

    @Test
    void testGetSubmissionById_Found() {
        when(submissionRepository.findById(1L)).thenReturn(Optional.of(submission));

        Optional<Submission> result = submissionService.getSubmissionById(1L);

        assertTrue(result.isPresent());
        assertEquals(submission, result.get());
        verify(submissionRepository, times(1)).findById(1L);
    }

    @Test
    void testGetSubmissionById_NotFound() {
        when(submissionRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Submission> result = submissionService.getSubmissionById(2L);

        assertTrue(result.isEmpty());
        verify(submissionRepository, times(1)).findById(2L);
    }

    @Test
    void testSaveSubmission() {
        when(submissionRepository.save(submission)).thenReturn(submission);

        Submission result = submissionService.saveSubmission(submission);

        assertEquals(submission, result);
        verify(submissionRepository, times(1)).save(submission);
    }

    @Test
    void testDeleteSubmission() {
        doNothing().when(submissionRepository).deleteById(1L);

        submissionService.deleteSubmission(1L);

        verify(submissionRepository, times(1)).deleteById(1L);
    }
}
