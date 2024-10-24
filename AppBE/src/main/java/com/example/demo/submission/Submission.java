package com.example.demo.submission;

import com.example.demo.assignment.Assignment;
import com.example.demo.student.Student;
import lombok.*;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "submissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "submission_id")
    private Long submissionId;

    @Column(name = "submission_date", nullable = false)
    private Date submissionDate;

    @ManyToOne
    @JoinColumn(name = "student_id", foreignKey = @ForeignKey(name = "fk_student_id"), nullable = false)
    private Student student;

    @Column(name = "assignment_id")
    private Long assignmentID;

 
    @Column(name = "file_submission", columnDefinition = "BYTEA")
    private byte[] fileSubmission;

    @Column(name = "total_grade", precision = 5)
    private Double totalGrade;
}
