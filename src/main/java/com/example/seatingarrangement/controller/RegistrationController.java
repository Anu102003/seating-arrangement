package com.example.seatingarrangement.controller;

import com.example.seatingarrangement.api.RegistrationAPI;
import com.example.seatingarrangement.dto.*;
import com.example.seatingarrangement.entity.CompanyDetails;
import com.example.seatingarrangement.service.JWTService;
import com.example.seatingarrangement.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegistrationController implements RegistrationAPI {

    private final RegistrationService registrationService;
    private final JWTService jwtService;
    @Override
    public ResponseEntity<ResponseDto> register(SignUpRequestDTO signUpRequestDTO) {
        CompanyDetails companyDetails = registrationService.register(signUpRequestDTO);
        return ResponseEntity.ok().body(new ResponseDto(companyDetails, "Register Successfully", HttpStatus.OK));
    }

    @Override
    public ResponseEntity<ResponseDto> login(LoginRequestDTO loginRequestDTO) {

        LoginResponseDTO login = registrationService.login(loginRequestDTO);
        return ResponseEntity.ok().body(new ResponseDto(login, "Login successful", HttpStatus.OK));
    }


    @Override
    public ResponseEntity<ResponseDto> logout(String accessToken) {

    return registrationService.logout(accessToken);


    }


}
