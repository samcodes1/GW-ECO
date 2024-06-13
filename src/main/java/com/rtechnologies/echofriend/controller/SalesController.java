package com.rtechnologies.echofriend.controller;

import java.sql.Timestamp;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




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

    @GetMapping("/getOrderData")
    public ResponseEntity<SaleResponse> getMethodName(@RequestParam(required = false) Long userId, @RequestParam(required = false) Timestamp ordertimestamp) {
        SaleResponse response = salesServiceobj.getOrder(userId, ordertimestamp);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }
    
    @PutMapping("/updateSale/{saleid}")
    public ResponseEntity<SaleResponse> updateSales(@PathVariable Long saleid, @RequestBody SaleRequest saleRequestObj) {
        SaleResponse response = salesServiceobj.updateState(saleid, saleRequestObj);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }

    @GetMapping("/getCompanyOrders")
    public ResponseEntity<SaleResponse> getOrders(@RequestParam(required = false) Long userId) {
        SaleResponse response = salesServiceobj.getOrders(userId);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }
    
    @GetMapping("/getUsersInvoice")
    public ResponseEntity<SaleResponse> getInvoiceOfUser(@RequestParam Long userid) {
        SaleResponse response = salesServiceobj.getInvouceOfUser(userid);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }

    @GetMapping("/getIvoiceProducts")
    public ResponseEntity<SaleResponse> getIvoiceProducts(@RequestParam Long invoiceid) {
        SaleResponse response = salesServiceobj.getIvoiceProducts(invoiceid);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }

    
}
