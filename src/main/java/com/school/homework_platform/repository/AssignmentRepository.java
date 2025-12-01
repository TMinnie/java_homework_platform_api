package com.school.homework_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.homework_platform.model.Assignment;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
}