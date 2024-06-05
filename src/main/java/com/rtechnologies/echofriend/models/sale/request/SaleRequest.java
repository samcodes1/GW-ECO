package com.rtechnologies.echofriend.models.sale.request;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaleRequest {
    private Long userid;
    private Map<Long, Integer> productidQuantity;
    private Float total;
    private Long voucherid;
    private String address;
    private String state;
}
