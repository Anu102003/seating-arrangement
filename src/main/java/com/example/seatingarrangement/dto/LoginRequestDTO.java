package com.example.seatingarrangement.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDTO {
    @NotBlank(message = "Email ID must not be blank")
    @Email(message = "Invalid email format")
    private String emailid;
    @NotBlank(message = "Password must not be blank")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[_]).{8,}$", message = "Password must have at least 8 characters, one uppercase letter, one lowercase letter, one digit, and one underscore")
    @Size(min = 8, max = 15, message = "Password must be between 8 and 15 characters")
    private String password;
}
