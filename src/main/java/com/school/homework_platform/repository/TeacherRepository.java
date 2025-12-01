package com.school.homework_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.homework_platform.model.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
