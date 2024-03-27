package com.example.seatingarrangement.service;


import com.example.seatingarrangement.dto.*;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public interface AllocationService {

    ResponseEntity<ResponseDto> addAllocation(TeamObjectDto teamObjectDto) throws BadRequestException;

    CsvOutputDto convertCsvFile(InputStream inputStream) throws IOException;

    ResponseEntity<ResponseDto> getAllocations(String companyName);
}
