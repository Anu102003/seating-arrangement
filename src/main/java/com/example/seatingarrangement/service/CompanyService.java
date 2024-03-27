package com.example.seatingarrangement.service;

import com.example.seatingarrangement.dto.CompanyDto;
import com.example.seatingarrangement.dto.LayoutDto;
import com.example.seatingarrangement.dto.ResponseDto;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CompanyService {
    ResponseEntity<ResponseDto> add(CompanyDto companyDto);

    ResponseEntity<ResponseDto> getAllLayOut(String companyName);

    ResponseEntity<ResponseDto> updateLayout(LayoutDto layoutDto) throws BadRequestException;

}
