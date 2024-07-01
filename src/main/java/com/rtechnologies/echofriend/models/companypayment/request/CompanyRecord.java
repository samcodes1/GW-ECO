package com.rtechnologies.echofriend.models.companypayment.request;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyRecord {
    private String companyEmail;
    private String firstName;
    private String lastName;
    private String firstAddress;
    private String secondAddress;
    private String city;
    private String state;
    private String zipcode;
    private String country;
}
