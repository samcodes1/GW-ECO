package com.rtechnologies.echofriend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rtechnologies.echofriend.appconsts.AppConstants;
import com.rtechnologies.echofriend.entities.NotificationEntity;
import com.rtechnologies.echofriend.models.NotificationResponse;
import com.rtechnologies.echofriend.models.NotitifcationRequest;
import com.rtechnologies.echofriend.repositories.NotificationRepo;
import com.rtechnologies.echofriend.utility.Utility;

@Service
public class NotificationService {

    @Autowired
    NotificationRepo notificationRepoObj;
    // Public java.lang.String pushNotification(NotitifcationRequest entity){

    //     return "";
    // }

    public String pushNotification(NotitifcationRequest notify){
        NotificationEntity notifyDate = new NotificationEntity(
            null, notify.getFcmid(), notify.getTitle(), notify.getMessage(), notify.getUserid(), Utility.getcurrentTimeStamp()
        );
        notificationRepoObj.save(notifyDate);
        return null;
    }

    public NotificationResponse getNotification(Long id){
        NotificationResponse response = new NotificationResponse();
        
        response.setData(notificationRepoObj.findById(id).get());
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }
}
