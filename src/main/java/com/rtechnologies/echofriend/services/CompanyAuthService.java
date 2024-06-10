package com.rtechnologies.echofriend.services;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import com.rtechnologies.echofriend.entities.companies.CompaniesEntity;
import com.rtechnologies.echofriend.models.security.CustomUserDetails;
import com.rtechnologies.echofriend.repositories.companies.CompaniesRepo;

@Service
public class CompanyAuthService {
    @Autowired
    CompaniesRepo companiesRepoObj;

    public CustomUserDetails loadUserByUsername(String username)  {
        // Try to load a mentor
        CompaniesEntity company = companiesRepoObj.findByCompanyEmail(username)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + username));
        if (company!=null) {
            return CustomUserDetails.builder()
                    .username(company.getCompanyEmail())
                    .password(company.getPassword())
                    .roles(Set.of("ROLE_COMPANY"))
                    // .admin(null)
                    // .userEntity(company)
                    .authorities(Collections.singletonList(() -> "ROLE_COMPANY")) // Assuming "ROLE_TEACHER" as the authority for teachers
                    .build();
        }
        throw new NotFoundException("User not found with email: " + username);
    }
}
