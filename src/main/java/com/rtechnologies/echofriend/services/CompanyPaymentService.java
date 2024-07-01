package com.rtechnologies.echofriend.services;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Optional;

import com.rtechnologies.echofriend.models.companypayment.request.CompanyPaymentRequest;
import com.rtechnologies.echofriend.models.companypayment.request.CompanyRecord;
import com.rtechnologies.echofriend.models.companypayment.response.CompanyPaymentResponse;
import com.rtechnologies.echofriend.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import com.rtechnologies.echofriend.appconsts.AppConstants;
import com.rtechnologies.echofriend.entities.companies.CompaniesEntity;
import com.rtechnologies.echofriend.entities.companies.CompanyRecordEntity;
import com.rtechnologies.echofriend.entities.companypayment.CompanyPaymentEntity;
import com.rtechnologies.echofriend.models.companies.request.CompaniesRequest;
import com.rtechnologies.echofriend.repositories.companies.CompaniesRepo;
import com.rtechnologies.echofriend.repositories.companies.CompanyRecordRepo;
import com.rtechnologies.echofriend.repositories.companypayment.CompanyPaymentRepo;

import javax.transaction.Transactional;


@Service
public class CompanyPaymentService {

    @Autowired
    CompanyPaymentRepo companyPaymentRepoObj;

    @Autowired
    private CompaniesService companiesServiceObj;

    @Autowired
    CompaniesRepo companiesRepoObj;

    @Autowired
    CompanyRecordRepo crpRepo;
    
    @Transactional
    public CompanyPaymentResponse companyPaymentServiceMethod(CompanyPaymentRequest companyPaymentRequestObj) throws ParseException, NoSuchAlgorithmException{
        companyPaymentRepoObj.save(
            new CompanyPaymentEntity(
                null, companyPaymentRequestObj.getSubscription(), Utility.convertISOToTimestamp(companyPaymentRequestObj.getPaymentDate()),
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

    public CompanyPaymentResponse enterCompanyRecord(CompanyRecord companyRecordObj){
        CompanyPaymentResponse response = new CompanyPaymentResponse();
        Optional<CompaniesEntity> companyData= companiesRepoObj.findByCompanyEmail(companyRecordObj.getCompanyEmail());
        if(!companyData.isPresent()){
            new NotFoundException("Company not found");
        }
        CompaniesEntity company = companyData.get();
        CompanyRecordEntity record = new CompanyRecordEntity(
                null,company.getCompanyid(),companyRecordObj.getFirstName(),companyRecordObj.getLastName(),
                companyRecordObj.getFirstAddress(),companyRecordObj.getSecondAddress(),companyRecordObj.getCity(),
                companyRecordObj.getState(),companyRecordObj.getZipcode(),companyRecordObj.getCountry()
            );
        crpRepo.save(
            record
        );
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        response.setData(record);
        return response;
    }

    public CompanyPaymentResponse getCompanyRecord(Long companyid){
        CompanyPaymentResponse response = new CompanyPaymentResponse();
        
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        response.setData(crpRepo.getCompanyRecord(companyid));
        return response;
    }
}
