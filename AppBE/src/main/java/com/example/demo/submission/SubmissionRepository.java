package com.example.demo.submission;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    // Additional query methods can be defined if necessary
}
