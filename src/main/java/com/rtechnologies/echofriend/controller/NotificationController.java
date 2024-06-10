package com.rtechnologies.echofriend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.cloudinary.http44.api.Response;
import com.rtechnologies.echofriend.appconsts.AppConstants;
import com.rtechnologies.echofriend.models.NotificationResponse;
import com.rtechnologies.echofriend.models.NotitifcationRequest;
import com.rtechnologies.echofriend.models.dashboard.DashboardResponse;
import com.rtechnologies.echofriend.services.NotificationService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class NotificationController {

    @Autowired
    NotificationService notificationServiceObj;

    @PostMapping("/pushNotifications")
    public String postMethodName(@RequestBody NotitifcationRequest entity) {
        //TODO: process POST request
        notificationServiceObj.pushNotification(entity);
        return "";
    }

    @GetMapping("/getNotifications")
    public ResponseEntity<NotificationResponse> getMethodName(@RequestParam Long userid) {
        NotificationResponse response = notificationServiceObj.getNotification(userid);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }
    
    
}
