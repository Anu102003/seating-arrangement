package com.example.seatingarrangement.service.impl;

import com.example.seatingarrangement.dto.*;
import com.example.seatingarrangement.entity.CompanyDetails;
import com.example.seatingarrangement.entity.Session;
import com.example.seatingarrangement.exception.*;
import com.example.seatingarrangement.repository.service.RegistrationRepoService;
import com.example.seatingarrangement.repository.service.SessionRepoService;
import com.example.seatingarrangement.service.JWTService;
import com.example.seatingarrangement.service.RegistrationService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final ModelMapper modelMapper;
    private final RegistrationRepoService registrationRepoService;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final SessionRepoService sessionRepoService;
//    private final SessionRepo sessionRepo;


    @Override
    public CompanyDetails register(SignUpRequestDTO signUpRequestDTO) {
       Optional<CompanyDetails> companyDetails= registrationRepoService.findByCompanyNameOrEmail(signUpRequestDTO.getCompanyName(),signUpRequestDTO.getEmail());
        if(companyDetails.isPresent()){

                if(companyDetails.get().getCompanyName().equals(signUpRequestDTO.getCompanyName()) && companyDetails.get().getEmail().equals(signUpRequestDTO.getEmail())){
                    throw new DuplicateRegistrationException("Company name and email already exist.");
                } else if (companyDetails.get().getCompanyName().equals(signUpRequestDTO.getCompanyName())) {

                    throw new DuplicateRegistrationException("Company name already exists.");
                } else if (companyDetails.get().getEmail().equals(signUpRequestDTO.getEmail())) {
                    throw new DuplicateRegistrationException("Email already exists.");
                }
        }
        CompanyDetails companyInfo =  modelMapper.map(signUpRequestDTO, CompanyDetails.class);
        String password= companyInfo.getPassword();
        companyInfo.setPassword(passwordEncoder.encode(password));
        return registrationRepoService.save(companyInfo);
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {

            Optional<CompanyDetails> companyDetails= registrationRepoService.findByEmail(loginRequestDTO.getEmailid());

        String encodedPasswordFromRequest = passwordEncoder.encode(loginRequestDTO.getPassword());

        if(passwordEncoder.matches(loginRequestDTO.getPassword(), companyDetails.get().getPassword())){
            String sessionId = UUID.randomUUID().toString();
            Session session = new Session();
            session.setSessionId(sessionId);
            session.setCreateDate(LocalDateTime.now());
            session.setLastmodifiedDate(LocalDateTime.now());
            session.setCompanyDetails(companyDetails.get());
            sessionRepoService.save(session);
            var token=jwtService.generateToken(companyDetails.get().getCompanyName(),sessionId);
            String refreshToken= jwtService.generateRefreshToken(companyDetails.get().getCompanyName(),sessionId);
               LoginResponseDTO loginResponse = LoginResponseDTO.builder()
                       .email(companyDetails.get().getEmail())
                       .companyName(companyDetails.get().getCompanyName())
                       .accessToken(token)
                       .refreshToken(refreshToken)

                       .build();
               return loginResponse;


           } else if (loginRequestDTO.getPassword()!= companyDetails.get().getPassword()) {
               throw new  IncorrectPasswordException("Incorrect password");

           }
           else{
               throw new UserNotFoundException("User not found");


           }

           }

    @Override
    public ResponseEntity<ResponseDto> logout(String accessToken) {
        try{
            Claims claims= jwtService.extractAllClaims(accessToken);
            String sessionId= claims.get("sessionId", String.class);
            Optional<Session> sessionData= sessionRepoService.findBySessionId(sessionId);
            Session session = sessionData.get();
            System.out.println(session.getLogoutTime());
            if(session.getLogoutTime() != null){
                throw new AlreadyLogOutException("Already Logged Out");
            }
            session.setLogoutTime(LocalDateTime.now());
            sessionRepoService.save(session);

            return ResponseEntity.ok().body(new ResponseDto(null,"Logged Out",HttpStatus.OK));
        }
        catch (Exception e){
            throw new ResourceNotFoundException("Invalid Token ");
        }
    }


}
