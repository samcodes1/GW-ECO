package com.rtechnologies.echofriend.models.voucher.request;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VoucherRequest {
    private String voucherbarcode;
    private Long shopidfk;
    private String usedstatus;
    private Integer voucherpointscost;
    private Float discountpercentage;
    private Timestamp voucherexpiry;
    private String name;
    private String description;
}
