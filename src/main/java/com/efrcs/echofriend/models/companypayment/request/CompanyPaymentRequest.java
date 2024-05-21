package com.efrcs.echofriend.models.companypayment.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyPaymentRequest {
    private String subscription;
    private String paymentDate;
    private String transactionid;
    private Float amount;
    private Long companyId;
    private String expiry;
}
