package com.example.seatingarrangement.repository.service;

import com.example.seatingarrangement.entity.CompanyDetails;

import java.util.Optional;

public interface RegistrationRepoService {
    CompanyDetails save(CompanyDetails companyDetails);



    Optional<CompanyDetails> findBycompanyName(String username);

    Optional<CompanyDetails> findByEmail(String emailid);




    Optional<CompanyDetails> findByCompanyNameOrEmail(String companyName, String email);
}
