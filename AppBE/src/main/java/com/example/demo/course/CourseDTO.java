package com.example.demo.course;

import lombok.*;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private Long courseId;
    private String courseTitle;
    private String courseDescription;
    private Date startDate;
    private Date endDate;
    private String studentId; // Reference to Student
    private Long instructorId; // Reference to Instructor
}