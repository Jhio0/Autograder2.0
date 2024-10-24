package com.example.demo.student;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private String studentId;
    private String firstName;
    private String lastName;
    private String emailAddress;

    private String phoneNumber;
    private Date dateOfBirth;
    private Date enrollmentDate;
    private Long userId; // Reference to User
}