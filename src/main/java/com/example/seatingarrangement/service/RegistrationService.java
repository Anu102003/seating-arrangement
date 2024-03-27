package com.example.seatingarrangement.service;

import com.example.seatingarrangement.dto.*;
import com.example.seatingarrangement.entity.CompanyDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface RegistrationService {
    CompanyDetails register(SignUpRequestDTO signUpRequestDTO);

    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);

    ResponseEntity<ResponseDto> logout(TokenDto tokenDto);
}
