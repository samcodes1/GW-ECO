package com.rtechnologies.echofriend.models.voucher.request;

import org.springframework.web.multipart.MultipartFile;

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
    private MultipartFile voucherimage;
    private Integer voucherpointscost;
    private Float discountpercentage;
}
