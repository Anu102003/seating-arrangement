package com.example.seatingarrangement.controller;

import com.example.seatingarrangement.api.RegistrationAPI;
import com.example.seatingarrangement.constants.Constant;
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
        return ResponseEntity.ok().body(new ResponseDto(companyDetails, Constant.REGISTER_SUCCESSFULLY, HttpStatus.OK));
    }

    @Override
    public ResponseEntity<ResponseDto> login(LoginRequestDTO loginRequestDTO) {

        LoginResponseDTO login = registrationService.login(loginRequestDTO);
        return ResponseEntity.ok().body(new ResponseDto(login, Constant.LOGIN_SUCCESSFULLY, HttpStatus.OK));
    }


    @Override
    public ResponseEntity<ResponseDto> logout(TokenDto tokenDto) {

        return registrationService.logout(tokenDto);


    }


}
