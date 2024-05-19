package com.efrcs.echofriend.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.efrcs.echofriend.appconsts.AppConstants;
import com.efrcs.echofriend.models.banner.request.BannerRequest;
import com.efrcs.echofriend.models.banner.response.BannerResponse;
import com.efrcs.echofriend.services.BannerService;
import com.efrcs.echofriend.services.ProductsService;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



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
        
        BannerResponse response = bannerServiceObj.updateBannerServiceMethod(bannerId, bannerUpdateRequestObj);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }
    
}
