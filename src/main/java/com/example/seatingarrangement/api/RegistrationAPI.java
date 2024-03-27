package com.example.seatingarrangement.api;

import com.example.seatingarrangement.constants.ApiConstant;
import com.example.seatingarrangement.dto.LoginRequestDTO;
import com.example.seatingarrangement.dto.ResponseDto;
import com.example.seatingarrangement.dto.SignUpRequestDTO;
import com.example.seatingarrangement.dto.TokenDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin()
@RequestMapping(ApiConstant.BASIC_API_URL)
public interface RegistrationAPI {

    @PostMapping(ApiConstant.REGISTER)
    public ResponseEntity<ResponseDto> register(@Valid @RequestBody SignUpRequestDTO signUpRequestDTO);

    @PostMapping(ApiConstant.LOGIN)
    public ResponseEntity<ResponseDto> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO);

    @PostMapping(ApiConstant.LOGOUT)
    public ResponseEntity<ResponseDto> logout(@RequestBody TokenDto tokenDto);

}


