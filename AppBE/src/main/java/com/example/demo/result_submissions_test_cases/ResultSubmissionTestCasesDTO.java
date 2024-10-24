package com.example.demo.result_submissions_test_cases;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultSubmissionTestCasesDTO {
    private Long testCaseId;
    private Long resultSubmissionId;
    private String testResults; // Store JSON as String for simplicity
}
