package com.rtechnologies.echofriend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rtechnologies.echofriend.appconsts.AppConstants;
import com.rtechnologies.echofriend.entities.companies.CompaniesEntity;
import com.rtechnologies.echofriend.exceptions.RecordNotFoundException;
import com.rtechnologies.echofriend.models.companies.request.CompaniesRequest;
import com.rtechnologies.echofriend.models.companies.response.CompaniesResponse;
import com.rtechnologies.echofriend.repositories.companies.CompaniesRepo;

import javax.transaction.Transactional;

@Service
public class CompaniesService {
    
    @Autowired
    CompaniesRepo companiesRepoObj;

    public CompaniesResponse addCompanyServiceMethod(CompaniesRequest companiesRequestObj){
        companiesRepoObj.save(new CompaniesEntity(
            null, companiesRequestObj.getCompanyName(), companiesRequestObj.getSubscriptionType(), 
            companiesRequestObj.getProducts(), companiesRequestObj.getSubscriptionExpiry()
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
        companyEntity.setSubscriptiontype(companiesUpdateRequestObj.getSubscriptionType());
        companyEntity.setSubscriptionexpiry(companiesUpdateRequestObj.getSubscriptionExpiry());
        // response.setResponseCode(AppConstants.SUCCESS);
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }

}
