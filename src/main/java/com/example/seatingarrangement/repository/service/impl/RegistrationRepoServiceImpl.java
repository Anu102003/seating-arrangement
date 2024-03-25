package com.example.seatingarrangement.repository.service.impl;


import com.example.seatingarrangement.entity.CompanyDetails;
import com.example.seatingarrangement.repository.RegistrationRepo;
import com.example.seatingarrangement.repository.service.RegistrationRepoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistrationRepoServiceImpl implements RegistrationRepoService {
    private final RegistrationRepo registrationRepo;
    @Override
    public CompanyDetails save(CompanyDetails companyDetails) {
        return registrationRepo.save(companyDetails);
    }

    @Override
    public Optional<CompanyDetails> findBycompanyName(String username) {
        return registrationRepo.findBycompanyName(username);
    }

    @Override
    public Optional<CompanyDetails> findByEmail(String emailid) {
        return registrationRepo.findByEmail(emailid);
    }

    @Override
    public Optional<CompanyDetails> findByCompanyNameOrEmail(String companyName, String email) {
        return registrationRepo.findByCompanyNameOrEmail(companyName,email);
    }




}



