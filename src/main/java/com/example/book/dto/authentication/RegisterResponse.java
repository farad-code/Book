package com.example.book.dto.authentication;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String dob;
    private String email;
    private String phone;
    private String role;

}
