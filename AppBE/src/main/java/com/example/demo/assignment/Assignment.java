package com.example.demo.assignment;

import com.example.demo.course.Course;
import lombok.*;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "assignments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private Long assignmentId;

    @Column(name = "assignment_title", nullable = false)
    private String assignmentTitle;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "due_date")
    private Date dueDate;

    @ManyToOne
    @JoinColumn(name = "course_id", foreignKey = @ForeignKey(name = "fk_course_id"))
    private Course course;

    @Lob
    @Column(name = "assignment_file")
    private byte[] assignmentFile;

    @Lob
    @Column(name = "solution_file")
    private byte[] solutionFile;
}
