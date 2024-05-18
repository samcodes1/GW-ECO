package com.efrcs.echofriend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.efrcs.echofriend.appconsts.AppConstants;
import com.efrcs.echofriend.entities.companies.CompaniesEntity;
import com.efrcs.echofriend.entities.products.ProductsEntity;
import com.efrcs.echofriend.models.products.request.ProductsRequest;
import com.efrcs.echofriend.models.products.response.ProductsResponse;
import com.efrcs.echofriend.repositories.companies.CompaniesRepo;
import com.efrcs.echofriend.repositories.products.ProductsRepo;

@Service
public class ProductsService {
    
    @Autowired
    CompaniesRepo companiesRepoObj;

    @Autowired
    ProductsRepo productsRepoObj;

    public ProductsResponse addProductServiceMethod(@RequestBody ProductsRequest productsRequestObj){
        ProductsResponse response = new ProductsResponse();
        CompaniesEntity companiesdata = companiesRepoObj.findCompanyIdByName(productsRequestObj.getProductOfCompany());
        if(companiesdata == null){
            response.setResponseCode(AppConstants.RECORD_DOES_NOT_EXISTS);
            response.setResponseMessage(AppConstants.COMPANY_DOES_NOT_EXISTS);
            return response;
        }

        productsRepoObj.save(new ProductsEntity(
            null, productsRequestObj.getProductName(), productsRequestObj.getProductPrice(),
            productsRequestObj.getProductQuantity(), companiesdata.getCompanyid()
        ));

        response.setResponseCode(AppConstants.SUCCESS);
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }
}
