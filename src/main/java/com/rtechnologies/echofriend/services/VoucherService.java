package com.rtechnologies.echofriend.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.zxing.BarcodeFormat;
import com.rtechnologies.echofriend.appconsts.AppConstants;
import com.rtechnologies.echofriend.entities.voucher.VoucherEntity;
import com.rtechnologies.echofriend.exceptions.RecordNotFoundException;
import com.rtechnologies.echofriend.models.voucher.request.VoucherRequest;
import com.rtechnologies.echofriend.models.voucher.response.VoucherResponse;
import com.rtechnologies.echofriend.repositories.voucher.VoucherRepo;
import com.rtechnologies.echofriend.utility.Utility;

@Service
public class VoucherService {
    @Autowired
    VoucherRepo VoucherRepoObj;

    @Autowired
    private Cloudinary cloudinary;

    public VoucherResponse createVoucherService(VoucherRequest voucherObj){
        VoucherEntity ve = new VoucherEntity();
        String barcode = Utility.generateBarcodeDigits(
            voucherObj.getShopidfk()+""+voucherObj.getUsedstatus()+""+voucherObj.getVoucherpointscost()+""+voucherObj.getDiscountpercentage()+""+voucherObj.getVoucherexpiry(), 
            BarcodeFormat.CODE_128, 300, 100
        );
        ve.setVoucherbarcode(barcode);
        ve.setShopidfk(voucherObj.getShopidfk());
        ve.setUsedstatus(voucherObj.getUsedstatus());
        ve.setVoucherpointscost(voucherObj.getVoucherpointscost());
        ve.setDiscountpercentage(voucherObj.getDiscountpercentage());
        ve.setVoucherexpiry(voucherObj.getVoucherexpiry());
        ve.setVocuhercreatedat(Utility.getcurrentTimeStamp());
        VoucherRepoObj.save(ve);
        VoucherResponse response = new VoucherResponse();
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        response.setData(VoucherRepoObj.save(ve));
        return response;
    }

    @Transactional
    public VoucherResponse updateVoucherService(Long voucherid, VoucherRequest voucherObj){
        Optional<VoucherEntity> updateVoucher =VoucherRepoObj.findById(voucherid);
        if(!updateVoucher.isPresent()){
                        throw new RecordNotFoundException("Voucher not found.");
        }
        VoucherEntity update = updateVoucher.get();

        String barcode = Utility.generateBarcodeDigits(
            voucherObj.getShopidfk()+""+voucherObj.getUsedstatus()+""+voucherObj.getVoucherpointscost()+""+voucherObj.getDiscountpercentage()+""+voucherObj.getVoucherexpiry(), 
            BarcodeFormat.CODE_128, 300, 100
        );
        
        update.setShopidfk(voucherObj.getShopidfk()==null?update.getShopidfk():voucherObj.getShopidfk());
        update.setUsedstatus(voucherObj.getUsedstatus()==null?update.getUsedstatus():voucherObj.getUsedstatus());

        update.setVoucherpointscost(voucherObj.getVoucherpointscost()==null?update.getVoucherpointscost():voucherObj.getVoucherpointscost());
        update.setVoucherbarcode(barcode);
        update.setVoucherexpiry(voucherObj.getVoucherexpiry()==null?update.getVoucherexpiry():voucherObj.getVoucherexpiry());
        update.setDiscountpercentage(voucherObj.getDiscountpercentage()==null?update.getDiscountpercentage():voucherObj.getDiscountpercentage());
        VoucherResponse response = new VoucherResponse();
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        response.setData(VoucherRepoObj.save(update));
        return response;
    }

    public VoucherResponse getVoucherService(Long voucherid){
        VoucherResponse response = new VoucherResponse();
        if(voucherid==null){
            response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
            response.setData(VoucherRepoObj.findAll());
            return response;
        }
        Optional<VoucherEntity> voucheropdata = VoucherRepoObj.findById(voucherid);
        List<VoucherEntity> voucherlst = new ArrayList<>();

        if(voucheropdata.isPresent()){
            response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
            voucherlst.add(voucheropdata.get());
            response.setData(voucherlst);
            return response;
        }
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        response.setData(voucherlst);
        return response;
    }

    public VoucherResponse getNonRedeemedVouchersForUserservice(Long userid){
        VoucherResponse response = new VoucherResponse();
        List<VoucherEntity> voucherlst = VoucherRepoObj.findAllVoucherNotRedeemedByUserYet(userid);
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        response.setData(voucherlst);
        return response;
    }

    public VoucherResponse getVoucherStats(){
        VoucherResponse response = new VoucherResponse();
        Map<String, Object> vocuherstats = new HashMap<>();
        vocuherstats.put("runningvouchers", VoucherRepoObj.countVoucherRedeemedToday());
        vocuherstats.put("totalvoucherredeemed", VoucherRepoObj.countredeemed());
        vocuherstats.put("totalvouchercreated", VoucherRepoObj.voucherCreated());
        vocuherstats.put("expiredvoucher", VoucherRepoObj.countexpiredVouchers());
        
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        response.setData(null);
        return response;
    }
    
}
