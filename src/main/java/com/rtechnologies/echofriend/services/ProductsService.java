package com.rtechnologies.echofriend.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
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

    @Autowired
    private Cloudinary cloudinary;

    public ProductsResponse addProductServiceMethod(@RequestBody ProductsRequest productsRequestObj){
        ProductsResponse response = new ProductsResponse();
        CompaniesEntity companiesdata = companiesRepoObj.findCompanyIdByName(productsRequestObj.getProductOfCompany());
        if(companiesdata == null){
            // response.setResponseCode(AppConstants.RECORD_DOES_NOT_EXISTS);
            response.setResponseMessage(AppConstants.COMPANY_DOES_NOT_EXISTS);
            // return response;
            throw new RecordNotFoundException("Record with compnay '" + productsRequestObj.getProductOfCompany() + "' does not exist.");
        }

        String profilePicUrl = "";
        if(productsRequestObj.getImagefile() !=null){
            try {
                String folder = "product-logo-pics"; // Change this to your preferred folder name
                String uniqueFileName = UUID.randomUUID().toString() + "_" + productsRequestObj.getImagefile().getName();

                String publicId = folder + "/" + uniqueFileName;
                Map data = cloudinary.uploader().upload(productsRequestObj.getImagefile().getBytes(), ObjectUtils.asMap("public_id", publicId));
                profilePicUrl = data.get("secure_url").toString();
            } catch (IOException ioException) {
                throw new RuntimeException("File uploading failed");
            }
        }
        productsRepoObj.save(new ProductsEntity(
            null, productsRequestObj.getProductName(), productsRequestObj.getProductPrice(),
            productsRequestObj.getProductQuantity(), profilePicUrl, 
            companiesdata.getCompanyid(),productsRequestObj.getCategoryidfk(), productsRequestObj.getProducttypeidfk(),productsRequestObj.getProductdescription()
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

        String profilePicUrl = "";
        if(productsRequestObj.getImagefile() !=null){
            try {
                String folder = "product-logo-pics"; // Change this to your preferred folder name

                String uniqueFileName = UUID.randomUUID().toString() + "_" + productsRequestObj.getImagefile().getName();

                String publicId = folder + "/" + uniqueFileName;
                System.out.println("file name:> "+publicId);
                Map data = cloudinary.uploader().upload(productsRequestObj.getImagefile().getBytes(), ObjectUtils.asMap("public_id", publicId));
                profilePicUrl = data.get("secure_url").toString();
            } catch (IOException ioException) {
                throw new RuntimeException("File uploading failed");
            }
        }
        updateRecord.setProductimage(
            (profilePicUrl==null || profilePicUrl.isEmpty()) ? updateRecord.getProductimage():profilePicUrl
        );
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
        updateRecord.setCategoryidfk(
            productsRequestObj.getCategoryidfk()==null?updateRecord.getCategoryidfk():productsRequestObj.getCategoryidfk()
        );
        updateRecord.setProductdescription(
            productsRequestObj.getProductdescription()==null?updateRecord.getProductdescription():productsRequestObj.getProductdescription()
        );
        updateRecord.setProducttypeidfk(
            productsRequestObj.getProducttypeidfk()==null?updateRecord.getProducttypeidfk():productsRequestObj.getProducttypeidfk()
        );

        if(productsRequestObj.getProductOfCompany()!=null && !productsRequestObj.getProductOfCompany().isEmpty()){
            CompaniesEntity company = companiesRepoObj.findCompanyIdByName(productsRequestObj.getProductOfCompany());
            updateRecord.setCompanyidfk(company.getCompanyid());
        }

        productsRepoObj.save(updateRecord);
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

        // product.add(productsRepoObj.findProducts(productId));
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        response.setData(productsRepoObj.findProducts(productId));
        return response;
    }

    public ProductsResponse getPopularProducts(){
        ProductsResponse response = new ProductsResponse();
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        response.setData(productsRepoObj.findAll());
        return response;
    }

    public ProductsResponse getcompanyProducts(Long companyid){
        ProductsResponse response = new ProductsResponse();
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        response.setData(productsRepoObj.getCompanyProductsbyCompanyId(companyid));
        return response;
    }

}
