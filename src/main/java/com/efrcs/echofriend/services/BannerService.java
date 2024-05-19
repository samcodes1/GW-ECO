package com.efrcs.echofriend.services;

import java.text.ParseException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.efrcs.echofriend.appconsts.AppConstants;
import com.efrcs.echofriend.entities.banner.BannerEntity;
import com.efrcs.echofriend.entities.companies.CompaniesEntity;
import com.efrcs.echofriend.exceptions.RecordNotFoundException;
import com.efrcs.echofriend.models.banner.request.BannerRequest;
import com.efrcs.echofriend.models.banner.response.BannerResponse;
import com.efrcs.echofriend.repositories.banner.BannerRepo;
import com.efrcs.echofriend.repositories.companies.CompaniesRepo;
import com.efrcs.echofriend.utility.Utility;

@Service
public class BannerService {
    @Autowired
    BannerRepo bannerRepoObj;

    @Autowired
    CompaniesRepo companiesRepoObj;

    public BannerResponse addBannerServiceMethod(@RequestBody BannerRequest bannerRequestObj) throws ParseException{
        BannerResponse response = new BannerResponse();
        CompaniesEntity companiesdata = companiesRepoObj.findCompanyIdByName(bannerRequestObj.getCompanyBanner());
        if(companiesdata == null){
            // response.setResponseCode(AppConstants.RECORD_DOES_NOT_EXISTS);
            response.setResponseMessage(AppConstants.COMPANY_DOES_NOT_EXISTS);
            throw new RecordNotFoundException("Record with compnay '" + bannerRequestObj.getCompanyBanner() + "' does not exist.");
            
        }

        bannerRepoObj.save(new BannerEntity(
            null, bannerRequestObj.getBannerName(), bannerRequestObj.getBannerImage(), 
            Utility.convertToTimestamp(bannerRequestObj.getBannerExpiry()), companiesdata.getCompanyid()
        ));
        
        // response.setResponseCode(AppConstants.SUCCESS);
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }

    public BannerResponse updateBannerServiceMethod(Long bannerId, BannerRequest bannerUpdateRequestObj) throws ParseException{
        BannerResponse response = new BannerResponse();

        CompaniesEntity companiesdata = companiesRepoObj.findCompanyIdByNameAndBannerId(bannerUpdateRequestObj.getCompanyBanner(), bannerId);
        if(companiesdata == null){
            // response.setResponseCode(AppConstants.RECORD_DOES_NOT_EXISTS);
            response.setResponseMessage(AppConstants.COMPANY_DOES_NOT_EXISTS);
            throw new RecordNotFoundException("Record with compnay '" + bannerUpdateRequestObj.getCompanyBanner() + "' does not exist.");
            // return response;
        }

        Optional<BannerEntity> bannerData = bannerRepoObj.findById(bannerId);
        if(!bannerData.isPresent()){
            response.setResponseMessage(AppConstants.COMPANY_DOES_NOT_EXISTS);
            throw new RecordNotFoundException("Record with banner id '" + bannerId + "' does not exist.");
        }
        BannerEntity bannerEntity = bannerData.get();
        bannerEntity.setBannerexpiry(Utility.convertToTimestamp(bannerUpdateRequestObj.getBannerExpiry()));
        // response.setResponseCode(AppConstants.SUCCESS);
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }
    
}
