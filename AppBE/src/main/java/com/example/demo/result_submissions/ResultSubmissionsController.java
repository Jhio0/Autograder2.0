package com.example.demo.result_submissions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/result-submissions")
public class ResultSubmissionsController {

    @Autowired
    private ResultSubmissionsService resultSubmissionsService;

    // Get all result submissions
    @GetMapping
    public List<ResultSubmissionsDTO> getAllResultSubmissions() {
        return resultSubmissionsService.getAllResultSubmissions();
    }

    // Get a result submission by ID
    @GetMapping("/{id}")
    public ResponseEntity<ResultSubmissionsDTO> getResultSubmissionById(@PathVariable Long id) {
        ResultSubmissionsDTO resultSubmission = resultSubmissionsService.getResultSubmissionById(id);
        return resultSubmission != null ? ResponseEntity.ok(resultSubmission) : ResponseEntity.notFound().build();
    }

    // Create a new result submission
    @PostMapping
    public ResultSubmissionsDTO createResultSubmission(@RequestBody ResultSubmissionsDTO dto) {
        return resultSubmissionsService.createResultSubmission(dto);
    }

    // Delete a result submission
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResultSubmission(@PathVariable Long id) {
        resultSubmissionsService.deleteResultSubmission(id);
        return ResponseEntity.noContent().build();
    }
}