package com.example.demo.result_submissions;

import com.example.demo.submission.Submission;
import lombok.*;

import jakarta.persistence.*;
@Entity
@Table(name = "Result_Submissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultSubmissions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "result_submission_id")
    private Long resultSubmissionId;

    @ManyToOne
    @JoinColumn(name = "submissionid", foreignKey = @ForeignKey(name = "fk_submission_id"), nullable = false)
    private Submission submission;

}
