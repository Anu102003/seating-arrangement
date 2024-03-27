package com.example.seatingarrangement.controller;

import com.example.seatingarrangement.api.AllocationApi;
import com.example.seatingarrangement.constants.Constant;
import com.example.seatingarrangement.dto.CsvOutputDto;
import com.example.seatingarrangement.dto.ResponseDto;
import com.example.seatingarrangement.dto.TeamObjectDto;
import com.example.seatingarrangement.service.AllocationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;


@RestController
@Slf4j
public class AllocationController implements AllocationApi {

    @Autowired
    private AllocationService allocationService;

    @Override
    public ResponseEntity<ResponseDto> addAllocation(TeamObjectDto teamObjectDto) throws BadRequestException {

        return allocationService.addAllocation(teamObjectDto);
    }


    @Override
    public ResponseEntity<ResponseDto> getAllocations(String layoutId) {
        return allocationService.getAllocations(layoutId);
    }


    @Override
    public ResponseEntity<ResponseDto> convertCsvFile(MultipartFile file) throws IOException {
        log.info(String.valueOf(file));
        InputStream inputStream = file.getInputStream();
        CsvOutputDto csvOutputDto = allocationService.convertCsvFile(inputStream);
        if (csvOutputDto.isFlag())
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(csvOutputDto, Constant.FILE_CONVERTED, HttpStatus.OK));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("", Constant.FILE_NOT_CONVERTED, HttpStatus.OK));

    }


}
