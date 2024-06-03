package com.rtechnologies.echofriend.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.cj.exceptions.OperationCancelledException;
import com.rtechnologies.echofriend.appconsts.AppConstants;
import com.rtechnologies.echofriend.entities.sales.SaleProductEntity;
import com.rtechnologies.echofriend.entities.sales.SalesEntity;
import com.rtechnologies.echofriend.entities.voucher.VoucherEntity;
import com.rtechnologies.echofriend.entities.voucher.VoucherProjection;
import com.rtechnologies.echofriend.entities.voucher.VoucherUserAssociation;
import com.rtechnologies.echofriend.exceptions.OperationNotAllowedException;
import com.rtechnologies.echofriend.models.sale.request.SaleRequest;
import com.rtechnologies.echofriend.models.sale.response.SaleResponse;
import com.rtechnologies.echofriend.repositories.products.ProductsRepo;
import com.rtechnologies.echofriend.repositories.sale.SalesProductRepo;
import com.rtechnologies.echofriend.repositories.sale.SalesRepo;
import com.rtechnologies.echofriend.repositories.user.UserRepo;
import com.rtechnologies.echofriend.repositories.voucher.VoucherRepo;
import com.rtechnologies.echofriend.repositories.voucher.VoucherUserRepo;
import com.rtechnologies.echofriend.utility.Utility;

@Service
public class SalesService {
    @Autowired
    UserRepo userRepoObj;

    @Autowired
    ProductsRepo productRepoObj;

    @Autowired
    VoucherRepo voucherRepoObj;

    @Autowired 
    SalesRepo salesRepoObj;

    @Autowired 
    SalesProductRepo salesProductRepoObj;

    @Autowired
    VoucherUserRepo voucherUserRepoObj;

    @Transactional
    public SaleResponse placeOrder(SaleRequest saleRequestObj){
        SalesEntity salesEntity = new SalesEntity(
            null, saleRequestObj.getUserid(), Utility.getcurrentDate(), Utility.getcurrentTimeStamp(),saleRequestObj.getAddress(),"pending"
        );
        // saved sales table and got its id
        salesEntity = salesRepoObj.save(salesEntity);
        Long[] saleid = {salesEntity.getSaleid()};
        Float[] totalBill = {0f};

        saleRequestObj.getProductidQuantity().forEach(
            (productid, quantity)->{
                totalBill[0] += productRepoObj.findById(productid).get().getProductprice()*(float)quantity;
            }
        );

        VoucherEntity voucherEntity = voucherRepoObj.findById(saleRequestObj.getVoucherid()).get();

        Float discountAmount = (voucherEntity.getDiscountpercentage() / 100) * totalBill[0];

        float finalAmount = totalBill[0] - discountAmount;


        final Float tolerance = 0.01f;

        if(Math.abs(finalAmount - saleRequestObj.getTotal()) > tolerance){
            throw new OperationNotAllowedException("Calculated total does not match the user input total");
        }

        saleRequestObj.getProductidQuantity().forEach(
            (productid, quantity)->{
                // totalBill[0] += productRepoObj.findById(productid).get().getProductprice()*(float)quantity;
                salesProductRepoObj.save(new SaleProductEntity(
                    null, saleid[0], productid, quantity, finalAmount, voucherEntity.getVoucherid()
                ));
            }
        );

        VoucherUserAssociation uservoucher = voucherUserRepoObj.findByUseridfkAndVoucheridfk(saleRequestObj.getUserid(), saleRequestObj.getVoucherid());
        uservoucher.setIsused(true);
        voucherUserRepoObj.save(uservoucher);
        SaleResponse response = new SaleResponse();
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }
}
