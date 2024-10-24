package com.example.demo.result_submissions_test_cases;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/result-submission-test-cases")
public class ResultSubmissionTestCasesController {

    @Autowired
    private ResultSubmissionTestCasesService resultSubmissionTestCasesService;

    // Convert entity to DTO
    private ResultSubmissionTestCasesDTO convertToDTO(ResultSubmissionTestCases testCase) {
        return new ResultSubmissionTestCasesDTO(
                testCase.getTestCaseId(),
                testCase.getResultSubmissionId(),
                testCase.getTestResults()
        );
    }

    // Convert DTO to entity
    private ResultSubmissionTestCases convertToEntity(ResultSubmissionTestCasesDTO dto) {
        ResultSubmissionTestCases testCase = new ResultSubmissionTestCases();
        testCase.setResultSubmissionId(dto.getResultSubmissionId());
        testCase.setTestResults(dto.getTestResults());
        return testCase;
    }

    @GetMapping
    public List<ResultSubmissionTestCasesDTO> getAllTestCases() {
        return resultSubmissionTestCasesService.getAllTestCases().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultSubmissionTestCasesDTO> getTestCaseById(@PathVariable Long id) {
        Optional<ResultSubmissionTestCases> testCase = resultSubmissionTestCasesService.getTestCaseById(id);
        return testCase.map(tc -> ResponseEntity.ok(convertToDTO(tc)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResultSubmissionTestCasesDTO createTestCase(@RequestBody ResultSubmissionTestCasesDTO dto) {
        ResultSubmissionTestCases testCase = convertToEntity(dto);
        ResultSubmissionTestCases savedTestCase = resultSubmissionTestCasesService.saveTestCase(testCase);
        return convertToDTO(savedTestCase);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestCase(@PathVariable Long id) {
        resultSubmissionTestCasesService.deleteTestCase(id);
        return ResponseEntity.noContent().build();
    }
}