package com.example.seatingarrangement.repository.service.impl;

import com.example.seatingarrangement.dto.GetLayoutDto;
import com.example.seatingarrangement.entity.Company;
import com.example.seatingarrangement.repository.CompanyRepository;
import com.example.seatingarrangement.repository.service.CompanyRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyRepoServiceImpl implements CompanyRepoService {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public void insert(Company company) {
        companyRepository.save(company);

    }

    @Override
    public Optional<Company> findByCompanyName(String companyName) {
        return companyRepository.findByCompanyName(companyName);
    }

    @Override
    public GetLayoutDto findByLayoutId(String layoutId) {
        return companyRepository.findByLayoutId(layoutId);
    }
}
