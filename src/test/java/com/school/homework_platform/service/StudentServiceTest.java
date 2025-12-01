package com.school.homework_platform.service;

import com.school.homework_platform.model.Assignment;
import com.school.homework_platform.model.Student;
import com.school.homework_platform.model.Submission;
import com.school.homework_platform.repository.SubmissionRepository;

import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private SubmissionRepository submissionRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    void uploadSubmission_ShouldSaveSubmissionWithDefaultFields() {
        Submission submission = new Submission();
        when(submissionRepository.save(any(Submission.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Submission saved = studentService.uploadSubmission(submission);

        assertNotNull(saved.getSubmittedAt(), "submittedAt should be set");
        assertNull(saved.getGradedAt(), "gradedAt should be null initially");
        assertNull(saved.getGrade(), "grade should be null initially");
        assertNull(saved.getTeacherNotes(), "teacherNotes should be null initially");

        verify(submissionRepository, times(1)).save(any(Submission.class));
    }

    @Test
    void getSubmissions_ShouldReturnFilteredByAssignmentNameAndGrade() {
        // Arrange
        Student student = new Student();
        student.setId(1L);

        Assignment assignment1 = new Assignment();
        assignment1.setName("Math Homework");

        Assignment assignment2 = new Assignment();
        assignment2.setName("Science Homework");

        Submission submission1 = new Submission();
        submission1.setStudent(student);
        submission1.setAssignment(assignment1);
        submission1.setGrade(Submission.Grade.A);

        Submission submission2 = new Submission();
        submission2.setStudent(student);
        submission2.setAssignment(assignment2);
        submission2.setGrade(Submission.Grade.B);

        List<Submission> mockSubmissions = Arrays.asList(submission1, submission2);

        when(submissionRepository.findByStudentId(1L)).thenReturn(mockSubmissions);

        // Act
        List<Submission> filtered = studentService.getSubmissions(1L, "Math Homework", Submission.Grade.A);

        // Assert
        assertEquals(1, filtered.size(), "Should only return one submission");
        assertEquals("Math Homework", filtered.get(0).getAssignment().getName());
        assertEquals(Submission.Grade.A, filtered.get(0).getGrade());
    }

    @Test
    void getSubmissions_ShouldReturnAllWhenNoFilters() {
        Submission submission1 = new Submission();
        Submission submission2 = new Submission();
        when(submissionRepository.findByStudentId(1L)).thenReturn(Arrays.asList(submission1, submission2));

        List<Submission> result = studentService.getSubmissions(1L, null, null);

        assertEquals(2, result.size(), "Should return all submissions when no filters are applied");
        verify(submissionRepository, times(1)).findByStudentId(1L);
    }

    @Test
    void getSubmissionById_ShouldReturnSubmission_WhenExists() {
        Submission submission = new Submission();
        submission.setId(5L);
        when(submissionRepository.findById(5L)).thenReturn(Optional.of(submission));

        Optional<Submission> found = studentService.getSubmissionById(5L);

        assertTrue(found.isPresent());
        assertEquals(5L, found.get().getId());
    }

    @Test
    void getSubmissionById_ShouldReturnEmpty_WhenNotFound() {
        when(submissionRepository.findById(10L)).thenReturn(Optional.empty());

        Optional<Submission> result = studentService.getSubmissionById(10L);

        assertTrue(result.isEmpty());
    }
}
