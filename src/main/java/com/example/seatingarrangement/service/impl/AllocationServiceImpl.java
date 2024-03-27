package com.example.seatingarrangement.service.impl;

import com.example.seatingarrangement.constants.Constant;
import com.example.seatingarrangement.dto.*;
import com.example.seatingarrangement.entity.*;
import com.example.seatingarrangement.repository.AllocationRepository;
import com.example.seatingarrangement.repository.CompanyRepository;
import com.example.seatingarrangement.repository.service.AllocationRepoService;
import com.example.seatingarrangement.repository.service.CompanyRepoService;
import com.example.seatingarrangement.repository.TeamRepository;
import com.example.seatingarrangement.repository.service.TeamRepoService;
import com.example.seatingarrangement.service.AllocationAbstract;
import com.example.seatingarrangement.service.AllocationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Slf4j
@Service
public class AllocationServiceImpl implements AllocationService {
    @Autowired
    private AllocationRepository allocationRepository;
    @Autowired
    private AllocationRepoService allocationRepoService;
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeamRepoService teamRepoService;

    @Autowired
    private CompanyRepoService companyRepoService;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ResponseEntity<ResponseDto> addAllocation(TeamObjectDto teamObjectDto) throws BadRequestException {

        if (teamObjectDto.getAlgorithmPref() == 1) {
            GreedyImpl greedyImpl = new GreedyImpl(teamRepoService, companyRepoService, teamRepository, allocationRepoService, allocationRepository, modelMapper);

            log.info("h       i          " + companyRepository.findByLayoutId(teamObjectDto.getLayoutId()));
            return greedyImpl.createAllocation(teamObjectDto);

        } else if (teamObjectDto.getAlgorithmPref() == 2) {
            BacktrackingImpl backtracking = new BacktrackingImpl(teamRepoService, companyRepoService, teamRepository, allocationRepoService, allocationRepository, modelMapper);

            log.info("BTTTTTTTTTT");
            return backtracking.createAllocation(teamObjectDto);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("", "incorrect choice", HttpStatus.BAD_REQUEST));
    }

    @Override
    public CsvOutputDto convertCsvFile(InputStream inputStream) throws IOException {
        CsvOutputDto csvOutputDto = new CsvOutputDto();
        Integer spacesOccupied = 0;
        boolean flag = true;
        List<TeamDto> teamDtoList = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheet("Sheet1");
        int noOfColumns = 0;
        int noOfRows = 0;
        for (Row row : sheet) {
            if (noOfRows == 0) noOfColumns = row.getPhysicalNumberOfCells();
            else {
                TeamDto teamDto = new TeamDto();
                for (int i = 0; i < noOfColumns; i++) {
                    if (row.getCell(i) != null) {
                        if (i == 0) teamDto.setTeamName(row.getCell(i).toString());
                        else {
                            teamDto.setTeamCount(Integer.valueOf(String.valueOf(row.getCell(i)).split("\\.")[0]));
                            spacesOccupied += teamDto.getTeamCount();
                        }
                    } else {
                        flag = false;
                        break;
                    }
                }
                teamDtoList.add(teamDto);
            }
            ++noOfRows;
        }
        csvOutputDto.setTeamDtoList(teamDtoList);
        csvOutputDto.setFlag(flag);
        csvOutputDto.setSpacesOccupied(spacesOccupied);
        return csvOutputDto;
    }

    @Override
    public ResponseEntity<ResponseDto> getAllocations(String layoutId) {
        Optional<List<GetAllocationDto>> getAllocationDto = allocationRepoService.findByDefaultLayoutId(layoutId);  //dto to be created

        if (getAllocationDto.isPresent()) {
            log.info(getAllocationDto.get().stream().toString() + " values");
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(getAllocationDto.get(), Constant.ALLOCATION_NOT_RETRIEVED, HttpStatus.OK));
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto("", Constant.ALLOCATION_NOT_FOUND, HttpStatus.OK));

    }
}
