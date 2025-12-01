package com.school.homework_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.homework_platform.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{

}
