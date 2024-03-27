package com.example.seatingarrangement.api;

import com.example.seatingarrangement.constants.ApiConstant;
import com.example.seatingarrangement.dto.CompanyDto;
import com.example.seatingarrangement.dto.LayoutDto;
import com.example.seatingarrangement.dto.ResponseDto;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiConstant.BASIC_API_URL)
@CrossOrigin()
public interface CompanyApi {
    @GetMapping(ApiConstant.COMPANY_LAYOUT)
        // query to be added
    ResponseEntity<ResponseDto> getAllLayOut(@PathVariable("company_name") String companyName);

    @PostMapping(ApiConstant.UPDATE_LAYOUT)
        //to be checked
    ResponseEntity<ResponseDto> updateLayout(@RequestBody LayoutDto layoutDto) throws BadRequestException;

    @PostMapping(ApiConstant.ADD_COMPANY)
        //checked
    ResponseEntity<ResponseDto> add(@RequestBody CompanyDto companyDto);

}
