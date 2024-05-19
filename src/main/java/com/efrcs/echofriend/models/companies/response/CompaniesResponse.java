package com.efrcs.echofriend.models.companies.response;

import com.efrcs.echofriend.models.baseresponse.EcoFriendlyBaseResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CompaniesResponse extends EcoFriendlyBaseResponse{
    private Object responseData;
}
