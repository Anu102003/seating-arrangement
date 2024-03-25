package com.example.seatingarrangement.configuration;

import com.example.seatingarrangement.entity.CompanyDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CompanyInfoDetails implements UserDetails {

    private final String companyName;
    private final String password;
    private List<GrantedAuthority> authorities;

    public CompanyInfoDetails(CompanyDetails companyDetails){
        companyName = companyDetails.getCompanyName();
        password= companyDetails.getPassword();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return companyName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
