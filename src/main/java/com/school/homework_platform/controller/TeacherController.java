package com.school.homework_platform.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.school.homework_platform.dto.GradeSubmissionDTO;
import com.school.homework_platform.dto.SubmissionResponseDTO;
import com.school.homework_platform.dto.TeacherRequestDTO;
import com.school.homework_platform.model.Submission;
import com.school.homework_platform.model.Teacher;
import com.school.homework_platform.repository.TeacherRepository;
import com.school.homework_platform.service.TeacherService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;

    }
    @Autowired
    private TeacherRepository teacherRepository;

    // Get overview with filters
    @GetMapping("/submissions")
    public ResponseEntity<List<SubmissionResponseDTO>> getSubmissions(
            @RequestParam Long teacherId,
            @RequestParam(required = false) String assignmentName,
            @RequestParam(required = false) String studentName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate) {

        List<Submission> submissions = teacherService.getSubmissions(
            teacherId, assignmentName, studentName, fromDate, toDate
        );

        List<SubmissionResponseDTO> response = submissions.stream()
                .map(SubmissionResponseDTO::new)
                .toList();

        return ResponseEntity.ok(response);
    }

    // Grade submission
    @PatchMapping("/submissions/{id}/grade")
    public ResponseEntity<SubmissionResponseDTO> gradeSubmission(
            @PathVariable Long id,
            @RequestBody @Validated GradeSubmissionDTO request) {

        Submission graded = teacherService.gradeSubmission(id, request.getGrade(), request.getTeacherNotes());

        // Convert to DTO and return
        SubmissionResponseDTO responseDTO = new SubmissionResponseDTO(graded);
        return ResponseEntity.ok(responseDTO);
    }

    //Create a new teacher
    @PostMapping
    public Teacher createTeacher(@Valid @RequestBody TeacherRequestDTO dto) {
        Teacher teacher = new Teacher();
        teacher.setName(dto.getName());
        teacher.setEmail(dto.getEmail());

        return teacherRepository.save(teacher);
    }
}
