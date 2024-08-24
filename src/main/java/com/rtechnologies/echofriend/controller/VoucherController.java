package com.rtechnologies.echofriend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.rtechnologies.echofriend.appconsts.AppConstants;
import com.rtechnologies.echofriend.models.voucher.request.VoucherRequest;
import com.rtechnologies.echofriend.models.voucher.response.VoucherResponse;
import com.rtechnologies.echofriend.services.VoucherService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class VoucherController {

    @Autowired
    VoucherService voucherServiceObj;

    @PostMapping("/createVoucher")
    public ResponseEntity<VoucherResponse> createVoucher(@RequestBody VoucherRequest voucherEntityObj) {
        VoucherResponse response = voucherServiceObj.createVoucherService(voucherEntityObj);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE) ? 200 : 500).body(response);
    }

    @PutMapping("/updateVoucher/{voucherId}")
    public ResponseEntity<VoucherResponse> updateVoucher(@PathVariable Long voucherId, @RequestBody VoucherRequest voucherEntityObj) {
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
    
    @GetMapping("/voucherstats")
    public ResponseEntity<VoucherResponse> voucherstats() {
        VoucherResponse response = voucherServiceObj.getVoucherStats();
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE) ? 200 : 500).body(response);
    }

    @DeleteMapping("/deleteVoucher/{voucherid}")
    public ResponseEntity<VoucherResponse> voucherDelete(@PathVariable Long voucherid) {
        VoucherResponse response = voucherServiceObj.deleteVoucher(voucherid);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE) ? 200 : 500).body(response);
    }

    @GetMapping("/getCompanyVouchers")
    public ResponseEntity<VoucherResponse> getCompanyVouchers(@RequestParam(required = false) Long companyid) {
        VoucherResponse response = voucherServiceObj.getCompanyVouchers(companyid);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE) ? 200 : 500).body(response);
    }

    @GetMapping("/getCompanyVoucher")
    public ResponseEntity<VoucherResponse> gtevoucherByid(@RequestParam(required = false) Long voucherid) {
        VoucherResponse response = voucherServiceObj.getCompanyVoucherById(voucherid);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE) ? 200 : 500).body(response);
    }

    @GetMapping("/getUsedVoucher")
    public ResponseEntity<VoucherResponse> getUsedVouchers(@RequestParam(required = false) Long userid) {
        VoucherResponse response = voucherServiceObj.getUsedVouchers(userid);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE) ? 200 : 500).body(response);
    }

    @PutMapping("/setVoucherUsed/{voucherid}/{email}")
    public ResponseEntity<VoucherResponse> putMethodName(@PathVariable Long voucherid, @PathVariable String email) {
        VoucherResponse response = voucherServiceObj.updateVoucherToUsed(voucherid, email);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE) ? 200 : 500).body(response);
    }
}
