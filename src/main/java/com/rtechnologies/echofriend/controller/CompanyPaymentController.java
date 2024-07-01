package com.rtechnologies.echofriend.controller;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import com.rtechnologies.echofriend.models.companypayment.request.CompanyPaymentRequest;
import com.rtechnologies.echofriend.models.companypayment.request.CompanyRecord;
import com.rtechnologies.echofriend.models.companypayment.response.CompanyPaymentResponse;
import com.rtechnologies.echofriend.services.CompanyPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.rtechnologies.echofriend.appconsts.AppConstants;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class CompanyPaymentController {

    @Autowired
    CompanyPaymentService companyPaymentServiceObj;
    
    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "sign up success."),
            @ApiResponse(code = 400, message = "invalid request data."),
            @ApiResponse(code = 500, message = "internal server error."),
        }
    )
    @PostMapping("/subscriptionPayment")
    public ResponseEntity<CompanyPaymentResponse> subscriptionPayment(@RequestBody CompanyPaymentRequest companyPaymentRequestObj) throws ParseException, NoSuchAlgorithmException {
        CompanyPaymentResponse response = companyPaymentServiceObj.companyPaymentServiceMethod(companyPaymentRequestObj);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }
    

    @GetMapping("/getCompaniesPaymentRecord")
    public ResponseEntity<CompanyPaymentResponse> getCompaniesPaymentRecord(@RequestParam(required = false) Long companyId) {
        CompanyPaymentResponse response = companyPaymentServiceObj.getcompanyPaymentServiceMethod(companyId);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }

    @PostMapping("/companyRecord")
    public ResponseEntity<CompanyPaymentResponse> enterCompnayRecors(@RequestBody CompanyRecord companyRecord) {
        CompanyPaymentResponse response = companyPaymentServiceObj.enterCompanyRecord(companyRecord);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }
    
    @GetMapping("/getCompanyRecord")
    public ResponseEntity<CompanyPaymentResponse> getCompanyRecord(Long companyId) {
        CompanyPaymentResponse response = companyPaymentServiceObj.getCompanyRecord(companyId);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }
    
    
}
