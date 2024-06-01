package com.rtechnologies.echofriend.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rtechnologies.echofriend.appconsts.AppConstants;
import com.rtechnologies.echofriend.models.dashboard.DashboardResponse;

@Service
public class DashboardService {

    @Autowired
    BannerService bannerServiceObj;

    @Autowired
    TaskService taskServiceObj;

    @Autowired
    ProductsService productsServiceObj;

    public DashboardResponse getDashboarddata(){
        DashboardResponse response = new DashboardResponse();
        Map<String, Object> dashboarddata = new HashMap<>();
        dashboarddata.put("topTasks", taskServiceObj.topTask());
        dashboarddata.put("popularProducts", productsServiceObj.getPopularProducts());
        dashboarddata.put("bannerList", bannerServiceObj.getBannerServiceMethod(null));
        response.setData(dashboarddata);
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }
}
