package com.example.demo.result_submissions;

import com.example.demo.result_submissions_test_cases.ResultSubmissionTestCases;
import com.example.demo.result_submissions_test_cases.ResultSubmissionTestCasesService; // Assuming this is the service for test cases
import com.example.demo.submission.Submission;
import com.example.demo.submission.SubmissionRepository;
import com.example.demo.uploads.ResponseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ResultSubmissionsService {

    @Autowired
    private ResultSubmissionsRepository resultSubmissionsRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private ResponseService responseService;

    @Autowired
    private ResultSubmissionTestCasesService resultSubmissionTestCasesService;  // Injecting the test cases service

    Logger logger;
    // Get all result submissions
    public List<ResultSubmissionsDTO> getAllResultSubmissions() {
        return resultSubmissionsRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get a result submission by ID
    public ResultSubmissionsDTO getResultSubmissionById(Long id) {
        Optional<ResultSubmissions> resultSubmission = resultSubmissionsRepository.findById(id);
        return resultSubmission.map(this::convertToDTO).orElse(null);
    }
    private final ObjectMapper objectMapper = new ObjectMapper();
    // Create a new result submission
    public ResultSubmissionsDTO createResultSubmission(ResultSubmissionsDTO dto) {
        Optional<Submission> submission = submissionRepository.findById(dto.getSubmissionId());
        if (submission.isPresent()) {
            ResultSubmissions resultSubmissions = new ResultSubmissions();
            resultSubmissions.setSubmission(submission.get());
            resultSubmissions = resultSubmissionsRepository.save(resultSubmissions);

            // Fetch test result data
            List<Map<String, Object>> testResultData = responseService.getTestResultData();

            for (int i = 0; i < testResultData.size(); i++) {
                Map<String, Object> queryResult = testResultData.get(i);
                String queryKey = "query" + (i + 1);

                if (queryResult.containsKey(queryKey)) {
                    Map<String, Object> resultDetails = (Map<String, Object>) queryResult.get(queryKey);
                    if (resultDetails.containsKey("actualResultTable")) {
                        try {
                            // Extract only the 'actualResultTable' from resultDetails
                            Object actualResultTable = resultDetails.get("actualResultTable");

                            // Convert the actualResultTable to a JSON string
                            String jsonResult = objectMapper.writeValueAsString(actualResultTable);

                            // Create and save the test case
                            ResultSubmissionTestCases testCase = new ResultSubmissionTestCases();
                            testCase.setResultSubmissionId(resultSubmissions.getResultSubmissionId());
                            testCase.setTestResults(jsonResult); // This should be a JSON string

                            // Use a Long type for testCaseId
                            testCase.setTestCaseId((long) i);
                            resultSubmissionTestCasesService.saveTestCase(testCase);
                        } catch (JsonProcessingException e) {
                            // Handle JSON processing exception
                            throw new RuntimeException("Failed to serialize test result details to JSON", e);
                        }
                    }
                }
            }
            return convertToDTO(resultSubmissions);
        } else {
            throw new RuntimeException("Submission not found");
        }
    }


    // Delete a result submission
    public void deleteResultSubmission(Long id) {
        resultSubmissionsRepository.deleteById(id);
    }

    // DTO to Entity conversion
    private ResultSubmissionsDTO convertToDTO(ResultSubmissions resultSubmissions) {
        return new ResultSubmissionsDTO(
                resultSubmissions.getResultSubmissionId(),
                resultSubmissions.getSubmission().getSubmissionId() // Assuming Submission has a getSubmissionId method
        );
    }

    public ResultSubmissions saveResultSubmission(ResultSubmissions resultSubmission) {
        return resultSubmissionsRepository.save(resultSubmission);
    }
}
