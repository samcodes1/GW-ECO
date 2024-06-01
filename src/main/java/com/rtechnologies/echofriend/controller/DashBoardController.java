package com.rtechnologies.echofriend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rtechnologies.echofriend.appconsts.AppConstants;
import com.rtechnologies.echofriend.models.dashboard.DashboardResponse;
import com.rtechnologies.echofriend.services.DashboardService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class DashBoardController {

    @Autowired
    DashboardService dashboardServiceObj;

    @GetMapping("/getDashBoardData")
    public ResponseEntity<DashboardResponse> getdashboarddata() {
        DashboardResponse response = dashboardServiceObj.getDashboarddata();
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }

}
