package com.school.homework_platform.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.school.homework_platform.dto.StudentRequestDTO;
import com.school.homework_platform.dto.SubmissionRequestDTO;
import com.school.homework_platform.dto.SubmissionResponseDTO;
import com.school.homework_platform.model.Assignment;
import com.school.homework_platform.model.Student;
import com.school.homework_platform.model.Submission;
import com.school.homework_platform.model.Teacher;
import com.school.homework_platform.repository.AssignmentRepository;
import com.school.homework_platform.repository.StudentRepository;
import com.school.homework_platform.service.StudentService;
 
@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;
    private final StudentRepository studentRepository;
    private final AssignmentRepository assignmentRepository;


    @Autowired
    public StudentController(StudentService studentService,StudentRepository studentRepository,AssignmentRepository assignmentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
        this.assignmentRepository = assignmentRepository;

    }

    //Submit an Assignment
    @PostMapping("/submissions")
    public ResponseEntity<Submission> submitHomework(
            @Validated @ModelAttribute SubmissionRequestDTO request) throws IOException {

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("Student not found with id: " + request.getStudentId()));

        Assignment assignment = assignmentRepository.findById(request.getAssignmentId())
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found with id: " + request.getAssignmentId()));

        Teacher teacher = assignment.getTeacher(); 

        // Validate file
        MultipartFile file = request.getFile();

        String contentType = file.getContentType();
        if (!"application/pdf".equals(contentType) && !"image/jpeg".equals(contentType)) {
            throw new IllegalArgumentException("Only PDF or JPEG files are allowed");
        }

        if (file.getSize() > 5 * 1024 * 1024) { // 5 MB
            throw new IllegalArgumentException("File too large. Maximum allowed size is 5MB");
        }

        // Save file to /uploads/
        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path uploadPath = Paths.get("uploads");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Path filePath = uploadPath.resolve(filename);
        file.transferTo(filePath);

        // Create and save submission
        Submission submission = new Submission();
        submission.setStudent(student);
        submission.setAssignment(assignment);
        submission.setTeacher(teacher); 
        submission.setFilePath(filePath.toString());
        submission.setSubmittedAt(LocalDateTime.now());

        Submission savedSubmission = studentService.uploadSubmission(submission);

        return ResponseEntity.ok(savedSubmission);
    }

    // Show submissions w or w/o filters
    @GetMapping("/submissions")
    public ResponseEntity<List<SubmissionResponseDTO>> getSubmissions(
            @RequestParam Long studentId,
            @RequestParam(required = false) String assignmentName,
            @RequestParam(required = false) Submission.Grade grade) {

        List<Submission> submissions = studentService.getSubmissions(studentId, assignmentName, grade);

        // Convert to DTOs
        List<SubmissionResponseDTO> response = submissions.stream()
                .map(SubmissionResponseDTO::new)
                .toList();

        return ResponseEntity.ok(response);
    }

    //Serve File
    @GetMapping("/submissions/{id}/download")
    public ResponseEntity<Resource> downloadSubmission(@PathVariable Long id) throws IOException {

        Submission submission = studentService.getSubmissionById(id)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found with id: " + id));

        // Load file as Resource
        Path filePath = Paths.get(submission.getFilePath());
        if (!Files.exists(filePath)) {
            throw new IllegalArgumentException("File not found on server");
        }
        Resource resource = new UrlResource(filePath.toUri());

        // Determine content type
        String contentType = Files.probeContentType(filePath);
        if (contentType == null) contentType = "application/octet-stream";

        // Return file in response
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filePath.getFileName().toString() + "\"")
                .body(resource);
    }


    // Create new student
    @PostMapping
    public ResponseEntity<Student> createStudent(@Validated @RequestBody StudentRequestDTO dto) {
        Student student = new Student();
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        Student saved = studentRepository.save(student);
        return ResponseEntity.ok(saved);
    }

}
