package com.example.demo.user;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long userId;
    private String emailAddress;
    private Boolean status;
    private String role;


}