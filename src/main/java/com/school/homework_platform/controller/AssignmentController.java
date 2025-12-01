package com.school.homework_platform.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.homework_platform.dto.AssignmentRequestDTO;
import com.school.homework_platform.model.Assignment;
import com.school.homework_platform.model.Teacher;
import com.school.homework_platform.repository.AssignmentRepository;
import com.school.homework_platform.repository.TeacherRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @PostMapping
    public Assignment createAssignment(@Valid @RequestBody AssignmentRequestDTO dto) {
        Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));

        Assignment assignment = new Assignment();
        assignment.setName(dto.getTitle());
        assignment.setDescription(dto.getDescription());
        assignment.setTeacher(teacher);
        assignment.setCreatedAt(LocalDateTime.now());

        return assignmentRepository.save(assignment);
    }
}
