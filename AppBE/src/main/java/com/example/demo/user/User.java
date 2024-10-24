package com.example.demo.user;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "email_address", unique = true)
    private String emailAddress;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @Column(name = "role", nullable = false)
    private String role; // Indicates user type (student or instructor)
}
