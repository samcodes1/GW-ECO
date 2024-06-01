package com.rtechnologies.echofriend.models.products.response;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface ProductProjections {
    public Long getProductid();

    public String getProductname();
    public Float getProductprice();
    public Integer getProductquantity();
    public String getProductimage();
    public String getCategory();
    public String getProducttype();
    public Long getCompanyid();
    public String getCompanyname();
    public String getSubscriptiontype();
    public String getProducts();
    public Date getSubscriptionexpiry();
}
