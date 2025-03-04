package com.example.demo.student;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {
    Student findByStudentId(String studentId);
    Student findByEmailAddress(String emailAddress);
}