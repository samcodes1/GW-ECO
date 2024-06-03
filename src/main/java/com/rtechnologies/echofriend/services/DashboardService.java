package com.rtechnologies.echofriend.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rtechnologies.echofriend.appconsts.AppConstants;
import com.rtechnologies.echofriend.models.dashboard.DashboardResponse;
import com.rtechnologies.echofriend.repositories.companies.CompaniesRepo;
import com.rtechnologies.echofriend.repositories.tasks.TaskUserRepo;
import com.rtechnologies.echofriend.repositories.user.UserRepo;

@Service
public class DashboardService {

    @Autowired
    BannerService bannerServiceObj;

    @Autowired
    TaskService taskServiceObj;

    @Autowired
    ProductsService productsServiceObj;

    @Autowired
    UserRepo userRepoObj;

    @Autowired
    TaskUserRepo taskUserRepoObj;

    @Autowired
    CompaniesRepo companiesRepoObj;

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

    public DashboardResponse getDashboarddatav2(){
        DashboardResponse response = new DashboardResponse();
        Map<String, Object> dashboarddata = new HashMap<>();
        dashboarddata.put("totalusers", userRepoObj.count());
        dashboarddata.put("totalpointsearned", userRepoObj.sumAllPoints());
        dashboarddata.put("totaltaskscompleted", taskUserRepoObj.countByIscompleteTrue());
        dashboarddata.put("totalcompanies", companiesRepoObj.count());

        dashboarddata.put("usersdata", userRepoObj.findusersAndTasksCompleted());
        response.setData(dashboarddata);
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }
}
