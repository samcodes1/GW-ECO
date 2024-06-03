package com.rtechnologies.echofriend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.rtechnologies.echofriend.appconsts.AppConstants;
import com.rtechnologies.echofriend.models.sale.request.SaleRequest;
import com.rtechnologies.echofriend.models.sale.response.SaleResponse;
import com.rtechnologies.echofriend.services.SalesService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class SalesController {

    @Autowired
    SalesService salesServiceobj;

    @PostMapping("/placeOrder")
    public ResponseEntity<SaleResponse> placeOrder(@RequestBody SaleRequest saleRequestObj) {
        SaleResponse response = salesServiceobj.placeOrder(saleRequestObj);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }
    
}
