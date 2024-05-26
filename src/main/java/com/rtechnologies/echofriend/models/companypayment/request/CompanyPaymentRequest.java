package com.rtechnologies.echofriend.models.companypayment.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyPaymentRequest {
    private String subscription;
    private String paymentDate;
    private String transactionid;
    private Float amount;
    private Long companyId;
    private String expiry;
}
