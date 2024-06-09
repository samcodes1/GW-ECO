package com.rtechnologies.echofriend.controller;

import com.rtechnologies.echofriend.appconsts.AppConstants;
import com.rtechnologies.echofriend.models.companies.request.CompaniesRequest;
import com.rtechnologies.echofriend.models.companies.request.CompanySignUpRequest;
import com.rtechnologies.echofriend.models.companies.response.CompaniesResponse;
import com.rtechnologies.echofriend.services.CompaniesService;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<CompaniesResponse> addCompany(@RequestBody CompaniesRequest companiesRequestObj) throws NoSuchAlgorithmException {
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
    public ResponseEntity<CompaniesResponse> chnagesubscription(@PathVariable Long companyId, @RequestBody CompaniesRequest companiesUpdateRequestObj) {
        CompaniesResponse response = companiesServiceObj.updateCompanySubscription(companyId, companiesUpdateRequestObj);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }

    @PutMapping("/updateCompany/{companyId}")
    public ResponseEntity<CompaniesResponse> updateCompnay(@PathVariable Long companyId, @ModelAttribute CompaniesRequest companiesUpdateRequestObj) {
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
    @GetMapping("/getCompanies")
    public ResponseEntity<CompaniesResponse> getMethodName(@RequestParam(required = false) Long companyId) {
        CompaniesResponse response = companiesServiceObj.getAllCompanies(companyId);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }


    @GetMapping("/getCompanyData")
    public ResponseEntity<CompaniesResponse> companyManagement(@RequestParam Long companyId) {
        CompaniesResponse response = companiesServiceObj.getCompanyDashboarddata(companyId);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }

    @PostMapping("/companySignUp")
    public ResponseEntity<CompaniesResponse> signup(@RequestBody CompanySignUpRequest companySignUpRequestObj) throws NoSuchAlgorithmException {
        CompaniesResponse response = companiesServiceObj.companysignup(companySignUpRequestObj);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }

    @GetMapping("/companyDashboard")
    public ResponseEntity<CompaniesResponse> companydashboard(@RequestParam Long companyId) {
        CompaniesResponse response = companiesServiceObj.companyDashboard(companyId);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }
    
    @DeleteMapping("/deleteCompany/{companyId}")
    public ResponseEntity<CompaniesResponse> deletecompany(@RequestParam Long companyId) {
        CompaniesResponse response = companiesServiceObj.deletecompany(companyId);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }
    
}
