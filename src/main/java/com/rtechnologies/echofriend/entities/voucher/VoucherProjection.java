package com.rtechnologies.echofriend.entities.voucher;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface VoucherProjection {
    public Long getVoucheruserid();
    public Long getVoucheridfk();
    public Long getUseridfk();
    public Timestamp getVoucheruserexpry();
    public Boolean getIsused();
    public Long getVoucherid();
    public byte[] getVoucherbarcode();
    public Long getShopidfk();
    public String getUsedstatus();
    public String getVoucherimageurl();
    public Integer getVoucherpointscost();
    public Float getDiscountpercentage();
    public String getCompanylogo();
    public String getCompany_email();
    public String getSubscriptiontype();
    public Timestamp getSubscriptionexpiry();
    public String getCompanyname();
    public String getCompanyid();
    public String getName();
    public String getDescription();
    public Timestamp getVocuhercreatedat();
    public Timestamp getVoucherexpiry();

}
