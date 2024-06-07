package com.rtechnologies.echofriend.services;

import java.sql.Timestamp;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import com.rtechnologies.echofriend.appconsts.AppConstants;
import com.rtechnologies.echofriend.entities.sales.SaleProductEntity;
import com.rtechnologies.echofriend.entities.sales.SalesEntity;
import com.rtechnologies.echofriend.entities.voucher.VoucherEntity;
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
        
        VoucherEntity voucherEntity = voucherRepoObj.findById(saleRequestObj.getVoucherid()==null?-1:saleRequestObj.getVoucherid()).orElse(
            new VoucherEntity(null,null, null,null,null, null,0.0f, Utility.getcurrentTimeStamp(), Utility.getcurrentTimeStamp(), "","")
        );

        Float discountAmount = (voucherEntity.getDiscountpercentage() / 100) * totalBill[0];

        float finalAmount = totalBill[0] - discountAmount;

        System.out.println("FINAL AMOUNT>>>>>>> "+finalAmount);
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
        if(saleRequestObj.getVoucherid()!=null){
            VoucherUserAssociation uservoucher = voucherUserRepoObj.findByUseridfkAndVoucheridfk(saleRequestObj.getUserid(), saleRequestObj.getVoucherid());
            uservoucher.setIsused(true);
            voucherUserRepoObj.save(uservoucher);
        }
        
        SaleResponse response = new SaleResponse();
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }

    public SaleResponse getOrder(Long userid, Timestamp orderTimestamp){
        SaleResponse response = new SaleResponse();
        if(userid==null && orderTimestamp == null){
            
            response.setData(salesRepoObj.findOrders());
            response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
            return response;
        }

        if(userid!=null && orderTimestamp==null){
            
            response.setData(salesRepoObj.findOrdersByUserid(userid));
            response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
            return response;
        }

        if(userid==null && orderTimestamp !=null){
            
            response.setData(salesRepoObj.findOrdersByDateTime(orderTimestamp));
            response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
            return response;
        }

        response.setData(salesRepoObj.findOrdersByDateTimeUserid(orderTimestamp, userid));
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }

    public SaleResponse updateState(Long saleid, SaleRequest saleRequestObj){
        Optional<SalesEntity> saledata = salesRepoObj.findById(saleid);
        if(!saledata.isPresent()){
            throw new NotFoundException("Sales data not found");
        }
        SalesEntity salesEntity = saledata.get();
        salesEntity.setState(saleRequestObj.getState());
        SaleResponse response = new SaleResponse();
        response.setData(salesRepoObj.save(salesEntity));
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }
}
