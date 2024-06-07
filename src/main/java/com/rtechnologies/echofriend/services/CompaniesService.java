package com.rtechnologies.echofriend.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rtechnologies.echofriend.appconsts.AppConstants;
import com.rtechnologies.echofriend.entities.companies.CompaniesEntity;
import com.rtechnologies.echofriend.exceptions.RecordNotFoundException;
import com.rtechnologies.echofriend.models.companies.request.CompaniesRequest;
import com.rtechnologies.echofriend.models.companies.response.CompaniesResponse;
import com.rtechnologies.echofriend.repositories.companies.CompaniesRepo;
import com.rtechnologies.echofriend.repositories.products.ProductsRepo;
import com.rtechnologies.echofriend.utility.Utility;

import javax.transaction.Transactional;

@Service
public class CompaniesService {
    
    @Autowired
    CompaniesRepo companiesRepoObj;

    @Autowired
    ProductsRepo productsRepoObj;

    public CompaniesResponse addCompanyServiceMethod(CompaniesRequest companiesRequestObj){
        companiesRepoObj.save(new CompaniesEntity(
            null, companiesRequestObj.getCompanyName(), companiesRequestObj.getSubscriptionType(), 
            companiesRequestObj.getProducts(), companiesRequestObj.getSubscriptionExpiry(), Utility.getcurrentDate(), 
            companiesRequestObj.getRole(), companiesRequestObj.getCompanyEmail()
        ));
        CompaniesResponse response = new CompaniesResponse();
        // response.setResponseCode(AppConstants.SUCCESS);
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }

    public CompaniesResponse getAllCompanies(Long companyId){
        CompaniesResponse response = new CompaniesResponse();
        if(companyId==null){
            response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
            response.setData(companiesRepoObj.findAll());
            return response;
        }
        List<CompaniesEntity> company = new ArrayList<>();
        company.add(companiesRepoObj.findById(companyId).orElseThrow(
            ()-> new RecordNotFoundException("Record of company '" + companyId + "' does not exists")
        ));

        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        response.setData(company);
        return response;
    }

    @Transactional
    public CompaniesResponse updateCompanySubscription(Long companyId, CompaniesRequest companiesUpdateRequestObj){
        Optional<CompaniesEntity> companyObj = companiesRepoObj.findById(companyId);
        CompaniesResponse response = new CompaniesResponse();
        if(!companyObj.isPresent()){
            // response.setResponseCode(AppConstants.RECORD_DOES_NOT_EXISTS);
            response.setResponseMessage(AppConstants.RECORD_DOES_NOT_EXISTS_MESSAGE);
            // return response;
            throw new RecordNotFoundException("Record with compnayid '" + companyId + "' does not exist.");
            
        }

        CompaniesEntity companyEntity = companyObj.get();
        companyEntity.setSubscriptiontype(
            companiesUpdateRequestObj.getSubscriptionType()==null?companyEntity.getSubscriptiontype():companiesUpdateRequestObj.getSubscriptionType()
        );
        companyEntity.setSubscriptionexpiry(
            companiesUpdateRequestObj.getSubscriptionExpiry()==null?companyEntity.getSubscriptionexpiry():companiesUpdateRequestObj.getSubscriptionExpiry()
        );
        companyEntity.setCompanyname(
            companiesUpdateRequestObj.getCompanyName()==null?companyEntity.getCompanyname():companiesUpdateRequestObj.getCompanyName()
        );
        // response.setResponseCode(AppConstants.SUCCESS);
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }

    public CompaniesResponse getCompanyDashboarddata(Long companyId){
        CompaniesResponse response = new CompaniesResponse();
        Map<String, Object> companyData =  new HashMap<>();
        CompaniesEntity company = companiesRepoObj.findById(companyId).get();
        companyData.put("poductCount", productsRepoObj.countCompanyProducts(companyId));
        companyData.put("vouchersCreated", productsRepoObj.countCompanyVoucher(companyId));
        companyData.put("subscriptionExpiry", company.getSubscriptionexpiry());
        companyData.put("companyemail", company.getCompanyEmail());
        companyData.put("joiningdate", company.getJoindate());
        companyData.put("role", company.getRole());
        companyData.put("subscription", company.getSubscriptiontype());
        
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        response.setData(companyData);
        return response;
    }

}
