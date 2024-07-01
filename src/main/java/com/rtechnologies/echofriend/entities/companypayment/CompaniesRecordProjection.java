package com.rtechnologies.echofriend.entities.companypayment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface CompaniesRecordProjection {
    public Long getCompanyrecordid();
    public String getCity();
    public Long getCompanyidfk();
    public String getCountry();
    public String getFirst_address();
    public String getFirst_name();
    public String getLast_name();
    public String getSecond_address();
    public String getState();
    public String getZipcode();
    public String getCompanyname();
}
