package com.rtechnologies.echofriend.models.products.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductsRequest {
    private String productName;
    private Float productPrice;
    private Integer productQuantity;
    private String productOfCompany;
    private Long categoryidfk;
    private Long producttypeidfk;
    private String productimage;
}
