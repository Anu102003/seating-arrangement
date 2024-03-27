package com.example.seatingarrangement.api;

import com.example.seatingarrangement.constants.ApiConstant;
import com.example.seatingarrangement.dto.*;
import com.example.seatingarrangement.entity.CompanyDetails;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


