package com.rtechnologies.echofriend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rtechnologies.echofriend.appconsts.AppConstants;
import com.rtechnologies.echofriend.entities.userpayment.UserPaymentEntity;
import com.rtechnologies.echofriend.models.userpayment.response.UserPaymentResponse;
import com.rtechnologies.echofriend.repositories.userpayment.UserPaymentRepo;

@Service
public class UserPaymentService {
    @Autowired
    UserPaymentRepo userPaymentRepoObj;

    public UserPaymentResponse createpaymentRecord(UserPaymentEntity userPaymentEntityObj){
        userPaymentRepoObj.save(userPaymentEntityObj);
        UserPaymentResponse response = new UserPaymentResponse();
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }


    public UserPaymentResponse getpaymentHistory(Long id){
        userPaymentRepoObj.findById(id).get();
        UserPaymentResponse response = new UserPaymentResponse();
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        response.setData(userPaymentRepoObj.findByUseridfk(id).get());
        return response;
    }
}
