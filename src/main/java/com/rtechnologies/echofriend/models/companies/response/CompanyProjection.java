package com.rtechnologies.echofriend.models.companies.response;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface CompanyProjection {
    public Long getProductid();
    public String getUsername();
    public String getCompanyname();
    public Float getProductprice();
    public Long getTransactionid();
    public Float getAmount();
    public String getState();
    public Timestamp getSalestimestamp();
    public String getProductname();
    public String getCategory();
    public String getProductdescription();
    public String getProductimage();
}
