package com.rtechnologies.echofriend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

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
        List<NotificationEntity> notification = notificationRepoObj.findByUserid(id);
        response.setData(notification);
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }
}
