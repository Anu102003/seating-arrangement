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
    private String password;
}
