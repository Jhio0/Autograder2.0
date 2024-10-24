package com.example.demo.Instructor;


import com.example.demo.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "instructors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instructor_id")
    private Long instructorId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email_address", unique = true)
    private String emailAddress;

    @Column(name = "phone_number", length = 10)
    private String phoneNumber;

    @Column(name = "joining_date", nullable = false)
    private java.sql.Date joiningDate;

    @ManyToOne
    @JoinColumn(name = "user_id", unique = true, foreignKey = @ForeignKey(name = "fk_user_id"))
    private User user;
}