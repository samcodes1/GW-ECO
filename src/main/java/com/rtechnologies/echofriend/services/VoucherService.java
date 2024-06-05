package com.rtechnologies.echofriend.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.rtechnologies.echofriend.appconsts.AppConstants;
import com.rtechnologies.echofriend.entities.voucher.VoucherEntity;
import com.rtechnologies.echofriend.exceptions.RecordNotFoundException;
import com.rtechnologies.echofriend.models.voucher.request.VoucherRequest;
import com.rtechnologies.echofriend.models.voucher.response.VoucherResponse;
import com.rtechnologies.echofriend.repositories.voucher.VoucherRepo;

@Service
public class VoucherService {
    @Autowired
    VoucherRepo VoucherRepoObj;

    @Autowired
    private Cloudinary cloudinary;

    public VoucherResponse createVoucherService(VoucherRequest voucherObj){

        String voucherPicUrl = "";
        try {
            String folder = "vouchers-pics"; // Change this to your preferred folder name
            String publicId = folder + "/" + voucherObj.getVoucherimage().getName();
            Map data = cloudinary.uploader().upload(voucherObj.getVoucherimage().getBytes(), ObjectUtils.asMap("public_id", publicId));
            voucherPicUrl = data.get("secure_url").toString();
        } catch (IOException ioException) {
            throw new RuntimeException("File uploading failed");
        }
        VoucherEntity ve = new VoucherEntity();
        ve.setVoucherbarcode(voucherObj.getVoucherbarcode());
        ve.setShopidfk(voucherObj.getShopidfk());
        ve.setUsedstatus(voucherObj.getUsedstatus());
        ve.setVoucherimageurl(voucherPicUrl);
        ve.setVoucherpointscost(voucherObj.getVoucherpointscost());
        ve.setDiscountpercentage(voucherObj.getDiscountpercentage());
        VoucherRepoObj.save(ve);
        VoucherResponse response = new VoucherResponse();
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        response.setData(VoucherRepoObj.save(ve));
        return response;
    }

    public VoucherResponse updateVoucherService(Long voucherid, VoucherRequest voucherObj){
        Optional<VoucherEntity> updateVoucher =VoucherRepoObj.findById(voucherid);
        if(!updateVoucher.isPresent()){
                        throw new RecordNotFoundException("Voucher not found.");
        }
        VoucherEntity update = updateVoucher.get();
        String voucherPicUrl = "";
        if(voucherObj.getVoucherimage() == null){
            try {
                String folder = "vouchers-pics"; // Change this to your preferred folder name
                String publicId = folder + "/" + voucherObj.getVoucherimage().getName();
                Map data = cloudinary.uploader().upload(voucherObj.getVoucherimage().getBytes(), ObjectUtils.asMap("public_id", publicId));
                voucherPicUrl = data.get("secure_url").toString();
            } catch (IOException ioException) {
                throw new RuntimeException("File uploading failed");
            }
        }
        update.setShopidfk(voucherObj.getShopidfk()==null?update.getShopidfk():voucherObj.getShopidfk());
        update.setUsedstatus(voucherObj.getUsedstatus()==null?update.getUsedstatus():voucherObj.getUsedstatus());
        update.setVoucherimageurl(voucherObj.getVoucherimage()==null?update.getVoucherimageurl():voucherPicUrl);
        update.setVoucherpointscost(voucherObj.getVoucherpointscost()==null?update.getVoucherpointscost():voucherObj.getVoucherpointscost());
        update.setVoucherbarcode(voucherObj.getVoucherbarcode()==null?update.getVoucherbarcode():voucherObj.getVoucherbarcode());
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
    
}
