package com.rtechnologies.echofriend.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rtechnologies.echofriend.appconsts.AppConstants;
import com.rtechnologies.echofriend.appconsts.Membershiptype;
import com.rtechnologies.echofriend.entities.user.UserEntity;
import com.rtechnologies.echofriend.entities.userpayment.UserPaymentEntity;
import com.rtechnologies.echofriend.models.userpayment.response.UserPaymentResponse;
import com.rtechnologies.echofriend.repositories.user.UserRepo;
import com.rtechnologies.echofriend.repositories.userpayment.UserPaymentRepo;
import com.rtechnologies.echofriend.utility.Utility;
@EnableScheduling
@Service
public class UserPaymentService {
    @Autowired
    UserPaymentRepo userPaymentRepoObj;
    @Autowired
    UserRepo userRepoObj;

    @Transactional
    public UserPaymentResponse createpaymentRecord(UserPaymentEntity userPaymentEntityObj){
        userPaymentRepoObj.save(userPaymentEntityObj);
        UserEntity user = userRepoObj.findById(userPaymentEntityObj.getUseridfk()).get();
        user.setMembershiptype(Membershiptype.PAID);
        user.setMemebershipexpiry(Utility.getDaysExpiryFromCurrentDate(30));
        userRepoObj.save(user);
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

    @Scheduled(cron = "0 0 0 * * *") // Run at 12 AM every day
    public void autoAttendanceMarking() throws JsonProcessingException {
        userRepoObj.updateexpired();
    }
}
