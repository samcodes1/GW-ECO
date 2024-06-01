package com.rtechnologies.echofriend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rtechnologies.echofriend.appconsts.AppConstants;
import com.rtechnologies.echofriend.entities.userpayment.UserPaymentEntity;
import com.rtechnologies.echofriend.models.userpayment.response.UserPaymentResponse;
import com.rtechnologies.echofriend.services.UserPaymentService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/userpayment")
public class UserPayment {
    @Autowired
    UserPaymentService userPaymentServiceObj;

    @PostMapping("/createUserPayment")
    public ResponseEntity<UserPaymentResponse> postMethodName(@RequestBody UserPaymentEntity userPaymentEntityObj) {
        UserPaymentResponse response = userPaymentServiceObj.createpaymentRecord(userPaymentEntityObj);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE) ? 200 : 500).body(response);
    }

    @PostMapping("/getPaymentHistory")
    public ResponseEntity<UserPaymentResponse> getPaymentHistory(@RequestParam(required = false) Long productId) {
        UserPaymentResponse response = userPaymentServiceObj.getpaymentHistory(productId);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE) ? 200 : 500).body(response);
    }
    
}
