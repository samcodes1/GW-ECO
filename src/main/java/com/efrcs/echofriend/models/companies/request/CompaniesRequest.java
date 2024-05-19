package com.efrcs.echofriend.models.companies.request;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompaniesRequest {
    private String companyName;
    private String subscriptionType;
    private String  products;
    private Date subscriptionExpiry;
}
