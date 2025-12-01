package com.school.homework_platform.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.school.homework_platform.model.Assignment;
import com.school.homework_platform.model.Student;
import com.school.homework_platform.model.Submission;
import com.school.homework_platform.repository.SubmissionRepository;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {

    @Mock
    private SubmissionRepository submissionRepository;

    @InjectMocks
    private TeacherService teacherService;

    @Test
    void gradeSubmission_ShouldSetGradeAndSave() {

        Submission submission = new Submission();
        submission.setId(1L);

        when(submissionRepository.findById(1L)).thenReturn(Optional.of(submission));
        when(submissionRepository.save(any(Submission.class))).thenAnswer(inv -> inv.getArgument(0));

        Submission result = teacherService.gradeSubmission(1L, Submission.Grade.A, "Excellent work!");

        assertEquals(Submission.Grade.A, result.getGrade());
        assertEquals("Excellent work!", result.getTeacherNotes());
        assertNotNull(result.getGradedAt(), "Graded date should be set");
        verify(submissionRepository, times(1)).save(submission);
    }

    @Test
    void gradeSubmission_ShouldThrowException_WhenNotFound() {
        when(submissionRepository.findById(999L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> teacherService.gradeSubmission(999L, Submission.Grade.B, "Good job")
        );

        assertEquals("Submission not found with id: 999", exception.getMessage());
        verify(submissionRepository, never()).save(any());
    }

    @Test
    void getSubmissions_ShouldFilterByStudentName_AssignmentName_And_DateRange() {

        Student student1 = new Student();
        student1.setName("Alice");

        Student student2 = new Student();
        student2.setName("Bob");

        Assignment assignment1 = new Assignment();
        assignment1.setName("Math Homework");

        Assignment assignment2 = new Assignment();
        assignment2.setName("Science Homework");

        Submission s1 = new Submission();
        s1.setStudent(student1);
        s1.setAssignment(assignment1);
        s1.setSubmittedAt(LocalDateTime.of(2025, 10, 1, 10, 0));

        Submission s2 = new Submission();
        s2.setStudent(student2);
        s2.setAssignment(assignment2);
        s2.setSubmittedAt(LocalDateTime.of(2025, 10, 5, 12, 0));

        List<Submission> mockList = Arrays.asList(s1, s2);
        when(submissionRepository.findByTeacherId(10L)).thenReturn(mockList);

        List<Submission> result = teacherService.getSubmissions(
                10L,
                "Alice",
                "Math Homework",
                LocalDateTime.of(2025, 9, 30, 0, 0),
                LocalDateTime.of(2025, 10, 3, 0, 0)
        );

        assertEquals(1, result.size());
        assertEquals("Alice", result.get(0).getStudent().getName());
        assertEquals("Math Homework", result.get(0).getAssignment().getName());
    }

    @Test
    void getSubmissions_ShouldReturnAll_WhenNoFilters() {
        Submission s1 = new Submission();
        Submission s2 = new Submission();

        when(submissionRepository.findByTeacherId(5L)).thenReturn(Arrays.asList(s1, s2));

        List<Submission> result = teacherService.getSubmissions(5L, null, null, null, null);

        assertEquals(2, result.size());
        verify(submissionRepository, times(1)).findByTeacherId(5L);
    }
}
