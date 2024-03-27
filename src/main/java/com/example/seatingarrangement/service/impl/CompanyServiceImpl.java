package com.example.seatingarrangement.service.impl;

import com.example.seatingarrangement.constants.Constant;
import com.example.seatingarrangement.dto.CompanyDto;
import com.example.seatingarrangement.dto.LayoutDto;
import com.example.seatingarrangement.dto.ResponseDto;
import com.example.seatingarrangement.entity.Company;
import com.example.seatingarrangement.repository.CompanyRepository;
import com.example.seatingarrangement.repository.service.CompanyRepoService;
import com.example.seatingarrangement.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CompanyRepoService companyRepoService;
    @Autowired
    private CompanyRepository companyRepository;

    public ResponseEntity<ResponseDto> add(CompanyDto companyDto) {
        Company company = new Company();
        company.setCompanyId(UUID.randomUUID().toString());
        company.setCompanyName(companyDto.getCompanyName());
        List<Company.DefaultLayout> defaultLayoutList = new ArrayList<>();

        for (int[][] layout : companyDto.getCompanyLayoutList()) {
            Company.DefaultLayout defaultLayout = new Company.DefaultLayout();
            defaultLayout.setLayoutId(UUID.randomUUID().toString());
            defaultLayout.setCompanyLayout(layout);
            defaultLayout.setTotalSpace(availableSpacesCount(layout));
            defaultLayoutList.add(defaultLayout);
        }
        company.setCompanyLayout(defaultLayoutList);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(companyRepository.save(company), "company saved", HttpStatus.OK));
    }


    private int availableSpacesCount(int[][] layOut) {
        int total = 0;
        for (int[] value : layOut) {
            String m = Arrays.toString(value);
            String h = m.replace("1", "");
            total += m.length() - h.length();
        }
        return total;
    }

    @Override
    public ResponseEntity<ResponseDto> getAllLayOut(String companyName) {

        log.info(companyName);
        Optional<Company> company = companyRepository.findByCompanyName(companyName);
        //queryChange
        return company.map(out -> ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(out, "layout obtained", HttpStatus.OK))).orElseGet(() -> ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("", "Company not found", HttpStatus.OK)));
    }


    public ResponseEntity<ResponseDto> updateLayout(LayoutDto layoutDto) throws BadRequestException {
        LayoutDto responseLayoutDto = new LayoutDto();
        Company company = isValid(layoutDto);
        if (layoutDto.getDefaultLayout() == null) {
            int ind = 0;
            for (Company.DefaultLayout defaultLayout : company.getCompanyLayout()) {
                if (defaultLayout.getLayoutId().equals(layoutDto.getLayoutId())) {
                    company.getCompanyLayout().remove(ind);
                    break;
                }
                ind++;
            }
            company.setCompanyLayout(company.getCompanyLayout());
        } else {
            Company.DefaultLayout defaultLayout = new Company.DefaultLayout();
            defaultLayout.setCompanyLayout(layoutDto.getDefaultLayout());
            defaultLayout.setTotalSpace(availableSpacesCount(layoutDto.getDefaultLayout()));
            defaultLayout.setLayoutId(UUID.randomUUID().toString());
            modelMapper.map(defaultLayout, responseLayoutDto);
            if (layoutDto.getLayoutId() != null)
                changeBoolean(company, layoutDto);
            company.getCompanyLayout().add(defaultLayout);
        }
        responseLayoutDto.setCompanyName(layoutDto.getCompanyName());
        companyRepository.save(company);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(responseLayoutDto, "updates done", HttpStatus.OK));

    }

    void changeBoolean(Company company, LayoutDto layoutDto) {
        int ind = 0;
        for (Company.DefaultLayout layout : company.getCompanyLayout()) {
            if (layout.getLayoutId().equals(layoutDto.getLayoutId())) {
                layout.setChanged(true);
                company.getCompanyLayout().set(ind, layout);
                break;
            }
            ind++;
        }
    }

    Company isValid(LayoutDto layoutDto) throws BadRequestException {
        Optional<Company> company = companyRepository.findByCompanyName(layoutDto.getCompanyName());
        if (company.isEmpty())
            throw new BadRequestException(Constant.COMPANY_NOT_FOUND);
        if (layoutDto.getLayoutId() == null && layoutDto.getDefaultLayout() == null)
            throw new BadRequestException("data not present");
        if (layoutDto.getLayoutId() != null && companyRepoService.findByLayoutId(layoutDto.getLayoutId()) == null)
            throw new BadRequestException(Constant.LAYOUT_NOT_FOUND);
        return company.get();
    }
}
