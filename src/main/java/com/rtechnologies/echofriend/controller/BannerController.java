package com.rtechnologies.echofriend.controller;

import java.text.ParseException;

import com.rtechnologies.echofriend.appconsts.AppConstants;
import com.rtechnologies.echofriend.models.banner.request.BannerRequest;
import com.rtechnologies.echofriend.models.banner.response.BannerResponse;
import com.rtechnologies.echofriend.services.BannerService;
import com.rtechnologies.echofriend.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;




@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class BannerController {

    @Autowired
    ProductsService productsServiceObj;

    @Autowired
    BannerService bannerServiceObj;

    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "sign up success."),
            @ApiResponse(code = 404, message = "record does not exists."),
            @ApiResponse(code = 500, message = "internal server error."),
        }
    )
    @PostMapping("/addBanner")
    public ResponseEntity<BannerResponse> addBanner(@RequestBody BannerRequest bannerRequestObj) throws ParseException {
        BannerResponse response = bannerServiceObj.addBannerServiceMethod(bannerRequestObj);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }

    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "sign up success."),
            @ApiResponse(code = 404, message = "record does not exists."),
            @ApiResponse(code = 500, message = "internal server error."),
        }
    )
    @PutMapping("/updateBannerExpiry/{bannerId}")
    public ResponseEntity<BannerResponse> putMethodName(@PathVariable Long bannerId, @RequestBody BannerRequest bannerUpdateRequestObj) throws ParseException {
        
        BannerResponse response = bannerServiceObj.updateBannerExpiryServiceMethod(bannerId, bannerUpdateRequestObj);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }

    @PutMapping("/updateBanner/{bannerId}")
    public ResponseEntity<BannerResponse> updateBanner(@PathVariable Long bannerId, @RequestBody BannerRequest bannerUpdateRequest) throws ParseException {
        //TODO: process PUT request
        BannerResponse response = bannerServiceObj.updateBannerServiceMethod(bannerId, bannerUpdateRequest);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }

    @DeleteMapping("/deleteBanner/{bannerId}")
    public ResponseEntity<BannerResponse> deleteBanner(@PathVariable Long bannerId){

        BannerResponse response = bannerServiceObj.deleteBanner(bannerId);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }

    @GetMapping("/getBanners")
    public ResponseEntity<BannerResponse> getBanner(@RequestParam(required = false) Long bannerId) {
        BannerResponse response = bannerServiceObj.getBannerServiceMethod(bannerId);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }
    
}
