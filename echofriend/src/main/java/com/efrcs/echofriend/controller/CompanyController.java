package com.efrcs.echofriend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.efrcs.echofriend.models.companies.request.CompaniesRequest;
import com.efrcs.echofriend.models.companies.response.CompaniesResponse;
import com.efrcs.echofriend.services.CompaniesService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class CompanyController {

    @Autowired
    CompaniesService companiesServiceObj;
    
    @PostMapping("/addCompany")
    public CompaniesResponse addCompany(@RequestBody CompaniesRequest companiesRequestObj) {
        return companiesServiceObj.addCompanyServiceMethod(companiesRequestObj);
    }

    @PutMapping("/changeSubscription/{companyId}")
    public CompaniesResponse updateCompanySubscription(@PathVariable Long companyId, @RequestBody CompaniesRequest companiesUpdateRequestObj) {
        
        return companiesServiceObj.updateCompanySubscription(companyId, companiesUpdateRequestObj);
    }

    @GetMapping("/getAllCompanies")
    public CompaniesResponse getMethodName() {
        return companiesServiceObj.getAllCompanies();
    }
}
