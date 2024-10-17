package com.rtechnologies.echofriend.models.companies.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanySignUpRequest {
    private String companyName;
    private String companyEmail;
    private String location;
    private Long category;
    private String password;
    private String subscriptiontype;
    private Date subscriptionexpiry;
}
