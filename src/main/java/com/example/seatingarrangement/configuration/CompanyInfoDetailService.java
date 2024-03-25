package com.example.seatingarrangement.configuration;

import com.example.seatingarrangement.entity.CompanyDetails;
import com.example.seatingarrangement.repository.service.RegistrationRepoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CompanyInfoDetailService implements UserDetailsService {

    private final RegistrationRepoService registrationRepoService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<CompanyDetails> companyDetails= registrationRepoService.findBycompanyName(username);
         return companyDetails.map(CompanyInfoDetails::new).orElseThrow(()->new UsernameNotFoundException("Company Not Found"));
    }
}
