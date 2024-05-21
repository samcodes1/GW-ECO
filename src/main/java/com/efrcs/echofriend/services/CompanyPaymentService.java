package com.efrcs.echofriend.services;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efrcs.echofriend.appconsts.AppConstants;
import com.efrcs.echofriend.entities.companypayment.CompanyPaymentEntity;
import com.efrcs.echofriend.models.companies.request.CompaniesRequest;
import com.efrcs.echofriend.models.companypayment.request.CompanyPaymentRequest;
import com.efrcs.echofriend.models.companypayment.response.CompanyPaymentResponse;
import com.efrcs.echofriend.repositories.companypayment.CompanyPaymentRepo;
import com.efrcs.echofriend.utility.Utility;

import jakarta.transaction.Transactional;

@Service
public class CompanyPaymentService {

    @Autowired
    CompanyPaymentRepo companyPaymentRepoObj;

    @Autowired
    private CompaniesService companiesServiceObj;
    
    @Transactional
    public CompanyPaymentResponse companyPaymentServiceMethod(CompanyPaymentRequest companyPaymentRequestObj) throws ParseException{
        companyPaymentRepoObj.save(
            new CompanyPaymentEntity(
                null, companyPaymentRequestObj.getSubscription(), Utility.convertToTimestamp(companyPaymentRequestObj.getPaymentDate()),
                companyPaymentRequestObj.getTransactionid(), companyPaymentRequestObj.getCompanyId(), 
                companyPaymentRequestObj.getAmount()
            )
        );
        CompaniesRequest updateReq = new CompaniesRequest();

        updateReq.setSubscriptionType(companyPaymentRequestObj.getSubscription());
        updateReq.setSubscriptionExpiry(Utility.stringToSqlDate(companyPaymentRequestObj.getExpiry()));

        companiesServiceObj.updateCompanySubscription(companyPaymentRequestObj.getCompanyId(), 
        updateReq);

        CompanyPaymentResponse response = new CompanyPaymentResponse();
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }

    public CompanyPaymentResponse getcompanyPaymentServiceMethod(Long companyId){
        CompanyPaymentResponse response = new CompanyPaymentResponse();
        if(companyId==null){
            response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
            response.setData(companyPaymentRepoObj.findCompanyAndPayment());
            return response;
        }
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        response.setData(companyPaymentRepoObj.findCompanyAndPaymentById(companyId));
        return response;
    }
}
