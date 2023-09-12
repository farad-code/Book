package com.example.book.dto.authentication;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {

//    @NotBlank(message = "First name is required")
    private String firstName;
//    @NotBlank(message = "last name is required")
    private String lastName;
//    @NotBlank(message = "dob is required")
    private String dob;
//    @NotBlank(message = "Password is required")
    private String password;
//    @NotBlank(message = "Email is required")
    private String email;
//    @NotBlank(message = "Phone is required")
    private String phone;
//    @NotBlank(message = "Role is required")
    private Long roleId;
}
