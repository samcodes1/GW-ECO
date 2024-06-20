package com.rtechnologies.echofriend.services;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import com.rtechnologies.echofriend.models.companypayment.request.CompanyPaymentRequest;
import com.rtechnologies.echofriend.models.companypayment.response.CompanyPaymentResponse;
import com.rtechnologies.echofriend.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rtechnologies.echofriend.appconsts.AppConstants;
import com.rtechnologies.echofriend.entities.companypayment.CompanyPaymentEntity;
import com.rtechnologies.echofriend.models.companies.request.CompaniesRequest;
import com.rtechnologies.echofriend.repositories.companypayment.CompanyPaymentRepo;

import javax.transaction.Transactional;


@Service
public class CompanyPaymentService {

    @Autowired
    CompanyPaymentRepo companyPaymentRepoObj;

    @Autowired
    private CompaniesService companiesServiceObj;
    
    @Transactional
    public CompanyPaymentResponse companyPaymentServiceMethod(CompanyPaymentRequest companyPaymentRequestObj) throws ParseException, NoSuchAlgorithmException{
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
