package com.example.demo.student;

import com.example.demo.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Date;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @Column(name = "student_id", nullable = false, length = 255)
    private String studentId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email_address", unique = true)
    private String emailAddress;

    @Column(name = "phone_number", length = 10)
    private String phoneNumber;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "enrollment_date")
    private Date enrollmentDate;

    @ManyToOne
    @JoinColumn(name = "user_id", unique = true, foreignKey = @ForeignKey(name = "fk_user_id"))
    private User user;
}
