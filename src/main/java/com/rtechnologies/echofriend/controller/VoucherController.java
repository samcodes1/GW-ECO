package com.rtechnologies.echofriend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.rtechnologies.echofriend.appconsts.AppConstants;
import com.rtechnologies.echofriend.models.voucher.request.VoucherRequest;
import com.rtechnologies.echofriend.models.voucher.response.VoucherResponse;
import com.rtechnologies.echofriend.services.VoucherService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class VoucherController {

    @Autowired
    VoucherService voucherServiceObj;

    @PostMapping("/createVoucher")
    public ResponseEntity<VoucherResponse> createVoucher(@ModelAttribute VoucherRequest voucherEntityObj) {
        VoucherResponse response = voucherServiceObj.createVoucherService(voucherEntityObj);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE) ? 200 : 500).body(response);
    }

    @PutMapping("/updateVoucher/{voucherId}")
    public ResponseEntity<VoucherResponse> updateVoucher(@PathVariable Long voucherId, @ModelAttribute VoucherRequest voucherEntityObj) {
        System.err.println("LANDED IN UPDATE");
        VoucherResponse response = voucherServiceObj.updateVoucherService(voucherId, voucherEntityObj);
        System.err.println("LANDED IN UPDATE");
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE) ? 200 : 500).body(response);
    }
    
    @GetMapping("/getVoucherData")
    public ResponseEntity<VoucherResponse> getVocuherData(@RequestParam(required = false) Long voucherId) {
        VoucherResponse response = voucherServiceObj.getVoucherService(voucherId);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE) ? 200 : 500).body(response);
    }
    
    @GetMapping("/getNonRedeemedVouchersForUser")
    public ResponseEntity<VoucherResponse> getMethodName(@RequestParam Long userid) {
        VoucherResponse response = voucherServiceObj.getNonRedeemedVouchersForUserservice(userid);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE) ? 200 : 500).body(response);
    }
    
    
}