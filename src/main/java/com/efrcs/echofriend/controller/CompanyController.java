package com.efrcs.echofriend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.efrcs.echofriend.appconsts.AppConstants;
import com.efrcs.echofriend.models.companies.request.CompaniesRequest;
import com.efrcs.echofriend.models.companies.response.CompaniesResponse;
import com.efrcs.echofriend.services.CompaniesService;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class CompanyController {

    @Autowired
    CompaniesService companiesServiceObj;
    
    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "sign up success."),
            @ApiResponse(code = 400, message = "invalid request data."),
            // @ApiResponse(code = 404, message = "record does not exists."),
            // @ApiResponse(code = 403, message = "operation not allowed."),
            @ApiResponse(code = 500, message = "internal server error."),
        }
    )
    @PostMapping("/addCompany")
    public ResponseEntity<CompaniesResponse> addCompany(@RequestBody CompaniesRequest companiesRequestObj) {
        CompaniesResponse response = companiesServiceObj.addCompanyServiceMethod(companiesRequestObj);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }

    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "sign up success."),
            @ApiResponse(code = 400, message = "invalid request data."),
            @ApiResponse(code = 404, message = "record does not exists."),
            // @ApiResponse(code = 403, message = "operation not allowed."),
            @ApiResponse(code = 500, message = "internal server error."),
        }
    )
    @PutMapping("/changeSubscription/{companyId}")
    public ResponseEntity<CompaniesResponse> updateCompanySubscription(@PathVariable Long companyId, @RequestBody CompaniesRequest companiesUpdateRequestObj) {
        CompaniesResponse response = companiesServiceObj.updateCompanySubscription(companyId, companiesUpdateRequestObj);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }

    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "sign up success."),
            @ApiResponse(code = 400, message = "invalid request data."),
            @ApiResponse(code = 404, message = "record does not exists."),
            // @ApiResponse(code = 403, message = "operation not allowed."),
            @ApiResponse(code = 500, message = "internal server error."),
        }
    )
    @GetMapping("/getAllCompanies")
    public ResponseEntity<CompaniesResponse> getMethodName() {
        CompaniesResponse response = companiesServiceObj.getAllCompanies();
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }
}
