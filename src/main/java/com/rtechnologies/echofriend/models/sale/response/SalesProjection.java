package com.rtechnologies.echofriend.models.sale.response;

import java.sql.Date;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface SalesProjection {
    public Long getSaleid();
    public Long getUseridfk();
    public Date getDate();
    public Timestamp getSalestimestamp();
    public String getAddress();
    public String getState();
    public Long getSaleproductid();
    public Long getsaleidfk();
    public Long getProductidfk();
    public Integer getQuantity();
    public Float getTotal();
    public Long getVoucheridfk();
}
