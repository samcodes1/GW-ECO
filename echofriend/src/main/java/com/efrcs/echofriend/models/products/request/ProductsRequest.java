package com.efrcs.echofriend.models.products.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductsRequest {
    private String productName;
    private float productPrice;
    private int productQuantity;
    private String productOfCompany;
}
