package com.example.demo.assignment;

import lombok.*;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentDTO {
    private Long assignmentId;
    private String assignmentTitle;
    private String description;
    private Date dueDate;
    private Long courseId;
    private byte[] assignmentFile;
    private byte[] solutionFile;
}

