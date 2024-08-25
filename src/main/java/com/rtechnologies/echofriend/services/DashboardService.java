package com.rtechnologies.echofriend.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rtechnologies.echofriend.appconsts.AppConstants;
import com.rtechnologies.echofriend.entities.user.UserPointsHistory;
import com.rtechnologies.echofriend.models.dashboard.DashboardResponse;
import com.rtechnologies.echofriend.repositories.companies.CompaniesRepo;
import com.rtechnologies.echofriend.repositories.tasks.TaskCategoryRepo;
import com.rtechnologies.echofriend.repositories.tasks.TaskRepo;
import com.rtechnologies.echofriend.repositories.tasks.TaskUserRepo;
import com.rtechnologies.echofriend.repositories.user.UserHistoryRepo;
import com.rtechnologies.echofriend.repositories.user.UserRepo;
import com.rtechnologies.echofriend.repositories.voucher.VoucherUserRepo;

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

    @Autowired
    VoucherUserRepo voucherUserRepoObj;
    @Autowired
    TaskRepo taskRepoObj;
    @Autowired
    TaskCategoryRepo taskCategoryRepoObj;

    @Autowired
    UserHistoryRepo userHistoryRepoObj;

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
        Integer points = userRepoObj.sumAllPoints();
        dashboarddata.put("totalusers", userRepoObj.count());
        dashboarddata.put("totalpointsearned", points==null?0:points);
        dashboarddata.put("totaltaskscompleted", taskUserRepoObj.countByIscompleteTrue());
        dashboarddata.put("totalcompanies", companiesRepoObj.count());

        dashboarddata.put("usersdata", userRepoObj.findusersAndTasksCompleted());
        dashboarddata.put("recenttasks", taskRepoObj.findAllRecentTask());
        dashboarddata.put("tasksCategories", taskCategoryRepoObj.findAll());
        
        response.setData(dashboarddata);
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }

    public DashboardResponse getDashboarddataUser(Long id){
        DashboardResponse response = new DashboardResponse();
        Map<String, Object> dashboarddata = new HashMap<>();
        UserPointsHistory userhist = userHistoryRepoObj.userhistory(id);
        dashboarddata.put("pointsearned", userRepoObj.findById(id).get().getPoints());
        dashboarddata.put("pointsredeemed", userhist.getTotalpointsredeemed());//userRepoObj.sumAllPoints());// TODO: where clause fix here
        dashboarddata.put("totaltaskscompleted", taskUserRepoObj.countByIscompleteTrueWhereUserid(id));
        dashboarddata.put("usedvouchers", voucherUserRepoObj.countUsedVoucher(id));
        dashboarddata.put("voucherslist", voucherUserRepoObj.countUnusedVoucher(id));
        response.setData(dashboarddata);
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }
}
