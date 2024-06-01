package com.rtechnologies.echofriend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.rtechnologies.echofriend.appconsts.AppConstants;
import com.rtechnologies.echofriend.entities.companies.CompaniesEntity;
import com.rtechnologies.echofriend.entities.products.ProductsEntity;
import com.rtechnologies.echofriend.exceptions.RecordNotFoundException;
import com.rtechnologies.echofriend.models.products.request.ProductsRequest;
import com.rtechnologies.echofriend.models.products.response.ProductsResponse;
import com.rtechnologies.echofriend.repositories.companies.CompaniesRepo;
import com.rtechnologies.echofriend.repositories.products.ProductsRepo;

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
            // response.setResponseCode(AppConstants.RECORD_DOES_NOT_EXISTS);
            response.setResponseMessage(AppConstants.COMPANY_DOES_NOT_EXISTS);
            // return response;
            throw new RecordNotFoundException("Record with compnay '" + productsRequestObj.getProductOfCompany() + "' does not exist.");
        }

        productsRepoObj.save(new ProductsEntity(
            null, productsRequestObj.getProductName(), productsRequestObj.getProductPrice(),
            productsRequestObj.getProductQuantity(), companiesdata.getCompanyid(),productsRequestObj.getCategoryidfk()
        ));

        // response.setResponseCode(AppConstants.SUCCESS);
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }

    public ProductsResponse deleteProduct(Long productId){
        productsRepoObj.deleteById(productId);
        ProductsResponse response = new ProductsResponse();
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }

    public ProductsResponse updateProduct(Long productId, ProductsRequest productsRequestObj){
        // productsRepoObj.deleteById(productId);
        Optional<ProductsEntity> productData = productsRepoObj.findById(productId);
        if(!productData.isPresent()){
            throw new RecordNotFoundException("Record of product '" + productId + "' does not exists");
        }
        ProductsEntity updateRecord = productData.get();
        updateRecord.setProductname(
            productsRequestObj.getProductName()==null || productsRequestObj.getProductName().isEmpty()?
            updateRecord.getProductname():productsRequestObj.getProductName()
        );
        updateRecord.setProductprice(
            productsRequestObj.getProductPrice()==null ? updateRecord.getProductprice() : productsRequestObj.getProductPrice()
        );
        updateRecord.setProductquantity(
            productsRequestObj.getProductQuantity()==null ? updateRecord.getProductquantity() : productsRequestObj.getProductQuantity()
        );
        ProductsResponse response = new ProductsResponse();
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }

    public ProductsResponse getProducts(Long productId, Long categoryId){
        ProductsResponse response = new ProductsResponse();
        if(productId==null && categoryId==null){
            response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
            response.setData(productsRepoObj.findProducts());
            return response;
        }

        if(productId==null && categoryId!=null){
            response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
            response.setData(productsRepoObj.findProductsByCategory(categoryId));
            return response;
        }

        List<ProductsEntity> product = new ArrayList<ProductsEntity>();
        product.add(productsRepoObj.findById(productId).orElseThrow(
            ()-> new RecordNotFoundException("Record of product '" + productId + "' does not exists")
        ));
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        response.setData(product);
        return response;
    }

    public ProductsResponse getPopularProducts(){
        ProductsResponse response = new ProductsResponse();
        // List<ProductsEntity> product = new ArrayList<ProductsEntity>();
        // product.add();
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        response.setData(productsRepoObj.findAll());
        return response;
    }

}
