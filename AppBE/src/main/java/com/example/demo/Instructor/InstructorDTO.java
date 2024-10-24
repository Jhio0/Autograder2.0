package com.example.demo.Instructor;

import lombok.*;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstructorDTO {
    private Long instructorId;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private Date joiningDate;
    private Long userId;  // Reference to User
}