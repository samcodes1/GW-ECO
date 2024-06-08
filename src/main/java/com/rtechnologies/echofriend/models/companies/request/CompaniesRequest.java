package com.rtechnologies.echofriend.models.companies.request;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompaniesRequest {
    private String companyName;
    private String subscriptionType;
    private String  products;
    private Date subscriptionExpiry;
    private String role;
    private String companyEmail;
    private MultipartFile companylogo;
    private Long companycategoryfk;
    private String location;
    private String password;
}
