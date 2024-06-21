package com.rtechnologies.echofriend.services;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.rtechnologies.echofriend.appconsts.AppConstants;
import com.rtechnologies.echofriend.entities.banner.BannerEntity;
import com.rtechnologies.echofriend.entities.companies.CompaniesEntity;
import com.rtechnologies.echofriend.exceptions.RecordNotFoundException;
import com.rtechnologies.echofriend.models.banner.request.BannerRequest;
import com.rtechnologies.echofriend.models.banner.response.BannerResponse;
import com.rtechnologies.echofriend.repositories.banner.BannerRepo;
import com.rtechnologies.echofriend.repositories.companies.CompaniesRepo;
import com.rtechnologies.echofriend.utility.Utility;

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
            Utility.convertISOToTimestamp(bannerRequestObj.getBannerExpiry()), companiesdata.getCompanyid()
        ));
        
        // response.setResponseCode(AppConstants.SUCCESS);
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }

    public BannerResponse updateBannerExpiryServiceMethod(Long bannerId, BannerRequest bannerUpdateRequestObj) throws ParseException{
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
        bannerEntity.setBannerexpiry(Utility.convertISOToTimestamp(bannerUpdateRequestObj.getBannerExpiry()));
        bannerRepoObj.save(bannerEntity);
        
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }

    public BannerResponse updateBannerServiceMethod(Long bannerId, BannerRequest bannerUpdateRequestObj) throws ParseException{
        BannerResponse response = new BannerResponse();
        Optional<BannerEntity> bannerData = bannerRepoObj.findById(bannerId);
        if(!bannerData.isPresent()){
            throw new RecordNotFoundException("Record of bannerId '" + bannerId + "' does not exist.");
        }
        BannerEntity bannerEntity = bannerData.get();
        bannerEntity.setBannerexpiry(
            (bannerUpdateRequestObj.getBannerExpiry()==null || bannerUpdateRequestObj.getBannerExpiry().isEmpty())?
            bannerEntity.getBannerexpiry():Utility.convertISOToTimestamp(bannerUpdateRequestObj.getBannerExpiry())
        );
        bannerEntity.setBannerimage(
            bannerUpdateRequestObj.getBannerImage()==null || bannerUpdateRequestObj.getBannerImage().isEmpty()?
            bannerEntity.getBannerimage() :bannerUpdateRequestObj.getBannerImage()
        );
        bannerEntity.setBannername(
            (bannerUpdateRequestObj.getBannerName()==null || bannerUpdateRequestObj.getBannerName().isEmpty())?
            bannerEntity.getBannername():bannerUpdateRequestObj.getBannerName()
        );
        bannerRepoObj.save(bannerEntity);
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }

    public BannerResponse deleteBanner(Long bannerId){
        BannerResponse response = new BannerResponse();
        bannerRepoObj.deleteById(bannerId);
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }

    public BannerResponse getBannerServiceMethod(Long bannerId){
        BannerResponse response = new BannerResponse();
        if(bannerId==null){
            response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
            response.setData(bannerRepoObj.findAll());
            return response;
        }

        List<BannerEntity> banner = new ArrayList<BannerEntity>();
        banner.add(bannerRepoObj.findById(bannerId).orElseThrow(
            ()-> new RecordNotFoundException("Record of banner '" + bannerId + "' does not exists")
        ));
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        response.setData(banner);
        return response;
    }
    
}
