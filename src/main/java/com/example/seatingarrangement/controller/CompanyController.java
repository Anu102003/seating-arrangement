package com.example.seatingarrangement.controller;

import com.example.seatingarrangement.api.CompanyApi;
import com.example.seatingarrangement.dto.CompanyDto;
import com.example.seatingarrangement.dto.LayoutDto;
import com.example.seatingarrangement.dto.ResponseDto;
import com.example.seatingarrangement.service.CompanyService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyController implements CompanyApi {

    @Autowired
    private CompanyService companyService;

    @Override
    public ResponseEntity<ResponseDto> getAllLayOut(String companyName) {
        return companyService.getAllLayOut(companyName);
    }

    @Override
    public ResponseEntity<ResponseDto> updateLayout(LayoutDto layoutDto) throws BadRequestException {
        return companyService.updateLayout(layoutDto);
    }

    @Override
    public ResponseEntity<ResponseDto> add(CompanyDto companyDto) {
        return companyService.add(companyDto);
    }
}
