package com.rtechnologies.echofriend.services;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.rtechnologies.echofriend.appconsts.AppConstants;
import com.rtechnologies.echofriend.entities.companies.CompaniesEntity;
import com.rtechnologies.echofriend.entities.companies.CompaniesEntity.CompaniesEntityBuilder;
import com.rtechnologies.echofriend.exceptions.RecordAlreadyExistsException;
import com.rtechnologies.echofriend.exceptions.RecordNotFoundException;
import com.rtechnologies.echofriend.models.companies.request.CompaniesRequest;
import com.rtechnologies.echofriend.models.companies.request.CompanySignUpRequest;
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

    @Autowired
    private Cloudinary cloudinary;

    public CompaniesResponse addCompanyServiceMethod(CompaniesRequest companiesRequestObj) throws NoSuchAlgorithmException{
        Optional<CompaniesEntity> companydata = companiesRepoObj.findByCompanyEmail(companiesRequestObj.getCompanyEmail());
        if(companydata.isPresent()){
            throw new RecordAlreadyExistsException("Record already exists in the database.");
        }
        companiesRepoObj.save(new CompaniesEntity(
            null, companiesRequestObj.getCompanyName(), companiesRequestObj.getSubscriptionType(), 
            companiesRequestObj.getProducts(), companiesRequestObj.getSubscriptionExpiry(), Utility.getcurrentDate(), 
            companiesRequestObj.getRole(), companiesRequestObj.getCompanyEmail(), "",-1l,"", Utility.hashPassword(companiesRequestObj.getPassword())
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
    public CompaniesResponse updateCompanySubscription(Long companyId, CompaniesRequest companiesUpdateRequestObj) throws NoSuchAlgorithmException{
        Optional<CompaniesEntity> companyObj = companiesRepoObj.findById(companyId);
        CompaniesResponse response = new CompaniesResponse();
        if(!companyObj.isPresent()){
            // response.setResponseCode(AppConstants.RECORD_DOES_NOT_EXISTS);
            response.setResponseMessage(AppConstants.RECORD_DOES_NOT_EXISTS_MESSAGE);
            // return response;
            throw new RecordNotFoundException("Record with compnayid '" + companyId + "' does not exist.");
            
        }

        String profilePicUrl = "";
        if(companiesUpdateRequestObj.getCompanylogo()!=null){
            try {
                String folder = "company-logo-pics"; // Change this to your preferred folder name
                String publicId = folder + "/" + companiesUpdateRequestObj.getCompanylogo().getName();
                Map data = cloudinary.uploader().upload(companiesUpdateRequestObj.getCompanylogo().getBytes(), ObjectUtils.asMap("public_id", publicId));
                profilePicUrl = data.get("secure_url").toString();
            } catch (IOException ioException) {
                throw new RuntimeException("File uploading failed");
            }
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
        companyEntity.setCompanylogo(
            profilePicUrl.isEmpty()?companyEntity.getCompanylogo():profilePicUrl
        );
        companyEntity.setLocation(
            companiesUpdateRequestObj.getLocation()==null?companyEntity.getLocation():companiesUpdateRequestObj.getLocation()
        );

        companyEntity.setPassword(
            companiesUpdateRequestObj.getPassword()==null?companyEntity.getPassword():Utility.hashPassword(companiesUpdateRequestObj.getPassword())
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

    public CompaniesResponse companysignup(CompanySignUpRequest companySignUpRequestObj) throws NoSuchAlgorithmException{
        CompaniesResponse response = new CompaniesResponse();
        CompaniesEntityBuilder companyEntity = CompaniesEntity.builder()
        .companyEmail(companySignUpRequestObj.getCompanyEmail())
        .companyname(companySignUpRequestObj.getCompanyName())
        .location(companySignUpRequestObj.getLocation())
        .companycategoryfk(companySignUpRequestObj.getCategory())
        .joindate(Utility.getcurrentDate())
        .password(Utility.hashPassword(companySignUpRequestObj.getPassword()));

        companiesRepoObj.save(companyEntity.build());
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }

    public CompaniesResponse companyDashboard(Long companyid){
        CompaniesResponse response = new CompaniesResponse();
        Map<String, Object> companyData =  new HashMap<>();
        companyData.put("products", productsRepoObj.countCompanyProducts(companyid));
        companyData.put("orders", productsRepoObj.countCompanyOrderToday(companyid));
        companyData.put("totalOrders", productsRepoObj.countCompanyTotalOrder(companyid));
        companyData.put("customers", productsRepoObj.countCompanyCustomer(companyid));
        companyData.put("recentOrders", productsRepoObj.recentOrder(companyid));
        companyData.put("listOfOrders", productsRepoObj.orders(companyid));

        response.setData(companyData);
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }

    public CompaniesResponse deletecompany(Long companyid){
        CompaniesResponse response = new CompaniesResponse();
        companiesRepoObj.deleteById(companyid);
        // response.setData(companyData);
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }
}
