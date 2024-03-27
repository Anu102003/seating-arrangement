package com.example.seatingarrangement.api;


import com.example.seatingarrangement.constants.ApiConstant;
import com.example.seatingarrangement.dto.ResponseDto;
import com.example.seatingarrangement.dto.TeamObjectDto;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(ApiConstant.BASIC_API_URL)
@CrossOrigin
public interface AllocationApi {

    @PostMapping(ApiConstant.ADD_ALLOCATION) //to be
    ResponseEntity<ResponseDto> addAllocation(@RequestBody TeamObjectDto teamObjectDto) throws BadRequestException;

    @PostMapping(ApiConstant.CSV_FILE)  //checked
    ResponseEntity<ResponseDto> convertCsvFile(@RequestBody MultipartFile file) throws IOException;

    @GetMapping(ApiConstant.LAYOUT_ALLOCATION)
    ResponseEntity<ResponseDto> getAllocations(@PathVariable String layoutId);
}
