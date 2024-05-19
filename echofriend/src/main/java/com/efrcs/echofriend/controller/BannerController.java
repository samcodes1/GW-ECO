package com.efrcs.echofriend.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.efrcs.echofriend.models.banner.request.BannerRequest;
import com.efrcs.echofriend.models.banner.response.BannerResponse;
import com.efrcs.echofriend.models.products.request.ProductsRequest;
import com.efrcs.echofriend.models.products.response.ProductsResponse;
import com.efrcs.echofriend.services.BannerService;
import com.efrcs.echofriend.services.ProductsService;
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

    @PostMapping("/addProduct")
    public ProductsResponse addProduct(@RequestBody ProductsRequest productsRequestObj) {
        return productsServiceObj.addProductServiceMethod(productsRequestObj);
    }
    
    @PostMapping("/addBanner")
    public BannerResponse addBanner(@RequestBody BannerRequest bannerRequestObj) throws ParseException {
        return bannerServiceObj.addBannerServiceMethod(bannerRequestObj);
    }

    @PutMapping("/updateBannerExpiry/{bannerId}")
    public BannerResponse putMethodName(@PathVariable Long bannerId, @RequestBody BannerRequest bannerUpdateRequestObj) throws ParseException {
        
        return bannerServiceObj.updateBannerServiceMethod(bannerId, bannerUpdateRequestObj);
    }
    
}
