package com.example.demo.Instructor;

import com.example.demo.Instructor.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    // Optional: Add custom query methods if needed
}
