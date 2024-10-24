package com.example.demo.submission;

import lombok.*;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionDTO {
    private Long submissionId;
    private Date submissionDate;
    private String studentId;
    private Long assignmentId;
    private byte[] fileSubmission;
    private Double totalGrade;
}
