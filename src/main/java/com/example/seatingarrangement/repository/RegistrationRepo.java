package com.example.seatingarrangement.repository;

import com.example.seatingarrangement.entity.CompanyDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegistrationRepo extends JpaRepository<CompanyDetails,String> {


    Optional<CompanyDetails> findBycompanyName(String username);

    Optional<CompanyDetails> findByEmail(String emailid);




    Optional<CompanyDetails> findByCompanyNameOrEmail(String companyName, String email);
}
