package com.rtechnologies.echofriend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.rtechnologies.echofriend.models.NotitifcationRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class NotificationController {
    @PostMapping("/pushNotifications")
    public String postMethodName(@RequestBody NotitifcationRequest entity) {
        //TODO: process POST request
        
        return "";
    }
    
}
