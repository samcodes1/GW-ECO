package com.efrcs.echofriend.models.companies.response;

import java.sql.Date;

import com.efrcs.echofriend.models.baseresponse.EcoFriendlyBaseResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CompaniesResponse extends EcoFriendlyBaseResponse{
    private Long companyId;
    private String companyName;
    private String subscriptionType;
    private String products;
    private Date expiryDate;
}
