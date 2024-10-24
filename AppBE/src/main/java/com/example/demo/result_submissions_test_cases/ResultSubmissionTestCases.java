package com.example.demo.result_submissions_test_cases;

import com.example.demo.result_submissions.ResultSubmissions;
import lombok.*;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "Result_Submission_Test_Cases")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultSubmissionTestCases {

    @Id
    @Column(name = "testcaseid")
    private Long testCaseId; // Unique identifier for the test case

    @Column(name = "result_submission_id", nullable = false)
    private Long resultSubmissionId; // This will reference Result_Submissions

    @JdbcTypeCode(SqlTypes.JSON) // Map testResults as a JSONB type in the database
    @Column(name = "test_results", columnDefinition = "jsonb")
    private String testResults; // Storing test results as JSONB

    @ManyToOne
    @JoinColumn(name = "result_submission_id", insertable = false, updatable = false)
    private ResultSubmissions resultSubmission; // Association to ResultSubmissions
}
