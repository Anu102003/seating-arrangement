package com.example.seatingarrangement.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;


public interface JWTService {

    public String generateToken(String companyName,String sessionId);
    public String extractUsername(String token);
    public Date extractExpiration(String token);
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    public Boolean validateToken(String token, UserDetails userDetails);
    public Claims extractAllClaims(String token);
    String extractSubscriptionId(String token);
    public String generateRefreshToken(String companyName,String sessionId);

}
