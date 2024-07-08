package com.rtechnologies.echofriend.services;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.webjars.NotFoundException;

import com.rtechnologies.echofriend.appconsts.AppConstants;
import com.rtechnologies.echofriend.entities.sales.SaleProductEntity;
import com.rtechnologies.echofriend.entities.sales.SalesEntity;
import com.rtechnologies.echofriend.entities.user.UserEntity;
import com.rtechnologies.echofriend.entities.voucher.VoucherEntity;
import com.rtechnologies.echofriend.entities.voucher.VoucherUserAssociation;
import com.rtechnologies.echofriend.exceptions.OperationNotAllowedException;
import com.rtechnologies.echofriend.models.sale.request.InvoiceUpdate;
import com.rtechnologies.echofriend.models.sale.request.SaleRequest;
import com.rtechnologies.echofriend.models.sale.response.SaleResponse;
import com.rtechnologies.echofriend.models.sale.response.SalesProjection;
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
            new VoucherEntity(null,null, null,null,null, null,0.0f, Utility.getcurrentTimeStamp(), Utility.getcurrentTimeStamp(), "","",null)
        );

        Float discountAmount = null;
        if(voucherEntity.getIsdiscountpercentage()==null){
            discountAmount = 0.0f;
        }else if(voucherEntity.getIsdiscountpercentage()){
            discountAmount = (voucherEntity.getDiscountpercentage() / 100) * totalBill[0];
        }else{
            discountAmount = voucherEntity.getDiscountpercentage();// else will have amount not its percentage
        }

        float finalAmount = totalBill[0] - discountAmount;

        System.out.println("FINAL AMOUNT>>>>>>> "+finalAmount);
        final Float tolerance = 0.01f;

        if(Math.abs(finalAmount - saleRequestObj.getTotal()) > tolerance){
            throw new OperationNotAllowedException("Calculated total does not match the user input total");
        }

        saleRequestObj.getProductidQuantity().forEach(
            (productid, quantity)->{
                Float totalAmountPerProduct = 0f;
                totalAmountPerProduct = totalAmountPerProduct +  productRepoObj.findById(productid).get().getProductprice()*(float)quantity;
                salesProductRepoObj.save(new SaleProductEntity(
                    null, saleid[0], productid, quantity, totalAmountPerProduct, voucherEntity.getVoucherid()
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
        response.setData(salesEntity);
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

    public SaleResponse getOrders(Long userid){
        SaleResponse response = new SaleResponse();
        if(userid==null){
            response.setData(salesRepoObj.findAllSales());
            response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
            return response;
        }

        response.setData(salesRepoObj.findAllSalesbyUserid(userid));
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }

    public SaleResponse getInvouceOfUser(Long userid){
        SaleResponse response = new SaleResponse();

        response.setData(salesRepoObj.findAllSalesInvoice(userid));
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }

    public SaleResponse getIvoiceProducts(Long invoiceid){
        SaleResponse response = new SaleResponse();

        response.setData(salesRepoObj.findAllIvoiceProducts(invoiceid));
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }

    public SaleResponse updatedInvoice( Long invoiceid, InvoiceUpdate invoiceUpdateObj){
        SaleResponse response = new SaleResponse();
        Optional<SalesEntity> invoicedata = salesRepoObj.findById(invoiceid);
        if(!invoicedata.isPresent()){
            throw new NotFoundException("Sales data not found");
        }
        SalesEntity data = invoicedata.get();
        data.setState("payment_failed");
        salesRepoObj.save(data);
        // response.setData(salesRepoObj.findAllIvoiceProducts(invoiceid));
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }

    public SaleResponse orderManagmentServiceMethod( Long userid){
        SaleResponse response = new SaleResponse();
        
        // List<SalesProjection> salesdata = salesRepoObj.findAllSalesbyUserid(userid);
        Optional<UserEntity> userdata = userRepoObj.findById(userid);
        if(!userdata.isPresent()){
            throw new NotFoundException("User data not found");
        }
        Map<String, Object> ordermanagment = new HashMap<>();
        ordermanagment.put("userdata", userdata.get());

        List<SalesProjection> salesdata = salesRepoObj.findSalesDataForOrderManagment(userid);

        ordermanagment.put("productlisting", salesdata);
        ordermanagment.put("billcalculation", salesRepoObj.findAllSalesInvoice(userid));

        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }
}
