package com.example.demo.result_submissions;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultSubmissionsRepository extends JpaRepository<ResultSubmissions, Long> {
    // Additional query methods can be defined if necessary
}
