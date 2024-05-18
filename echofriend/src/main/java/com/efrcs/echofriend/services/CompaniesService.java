package com.efrcs.echofriend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efrcs.echofriend.appconsts.AppConstants;
import com.efrcs.echofriend.entities.companies.CompaniesEntity;
import com.efrcs.echofriend.models.companies.request.CompaniesRequest;
import com.efrcs.echofriend.models.companies.response.CompaniesResponse;
import com.efrcs.echofriend.repositories.companies.CompaniesRepo;

import jakarta.transaction.Transactional;

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
        response.setResponseCode(AppConstants.SUCCESS);
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }

    public CompaniesResponse getAllCompanies(){
        List<CompaniesEntity> allCompaniesList = companiesRepoObj.findAll();
        CompaniesResponse response = new CompaniesResponse();
        response.setResponseCode(AppConstants.SUCCESS);
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        response.setResponseData(allCompaniesList);
        return response;
    }

    @Transactional
    public CompaniesResponse updateCompanySubscription(Long companyId, CompaniesRequest companiesUpdateRequestObj){
        Optional<CompaniesEntity> companyObj = companiesRepoObj.findById(companyId);
        CompaniesResponse response = new CompaniesResponse();
        if(companyObj.isPresent()){
            CompaniesEntity companyEntity = companyObj.get();
            companyEntity.setSubscriptiontype(companiesUpdateRequestObj.getSubscriptionType());
            companyEntity.setSubscriptionexpiry(companiesUpdateRequestObj.getSubscriptionExpiry());
            response.setResponseCode(AppConstants.SUCCESS);
            response.setResponseMessage(AppConstants.UPDATE_SUCCESS_MESSAGE);
            return response;
        }

        response.setResponseCode(AppConstants.RECORD_DOES_NOT_EXISTS);
        response.setResponseMessage(AppConstants.RECORD_DOES_NOT_EXISTS_MESSAGE);
        return response;
    }

}
