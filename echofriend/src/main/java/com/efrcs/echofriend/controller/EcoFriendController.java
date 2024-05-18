package com.efrcs.echofriend.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.efrcs.echofriend.models.admin.request.AdminRequest;
import com.efrcs.echofriend.models.admin.response.AdminResponse;
import com.efrcs.echofriend.models.banner.request.BannerRequest;
import com.efrcs.echofriend.models.banner.response.BannerResponse;
import com.efrcs.echofriend.models.companies.request.CompaniesRequest;
import com.efrcs.echofriend.models.companies.response.CompaniesResponse;
import com.efrcs.echofriend.models.products.request.ProductsRequest;
import com.efrcs.echofriend.models.products.response.ProductsResponse;
import com.efrcs.echofriend.models.tasks.request.TaskAssignmentRequest;
import com.efrcs.echofriend.models.tasks.request.TasksResquest;
import com.efrcs.echofriend.models.tasks.response.TasksResponse;
import com.efrcs.echofriend.services.AdminService;
import com.efrcs.echofriend.services.BannerService;
import com.efrcs.echofriend.services.CompaniesService;
import com.efrcs.echofriend.services.ProductsService;
import com.efrcs.echofriend.services.TaskService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class EcoFriendController {

    @Autowired
    AdminService adminServiceObj;

    @Autowired
    TaskService taskServiceObj;

    @Autowired
    CompaniesService companiesServiceObj;

    @Autowired
    ProductsService productsServiceObj;

    @Autowired
    BannerService bannerServiceObj;
    
    @GetMapping("/hello")
    public String hello(){
        return "hi!";
    }

    @PostMapping("/adminSignUp")
    public AdminResponse adminSignup(@RequestBody AdminRequest adminRequestObj) {
        
        AdminResponse response = adminServiceObj.adminSignupServiceMethod(adminRequestObj);
        
        return response;
    }

    @PostMapping("/createTask")
    public TasksResponse createTask(@RequestBody TasksResquest tasksResquestObj) {
        
        TasksResponse response = taskServiceObj.createTaskServiceMethod(tasksResquestObj);
        
        return response;
    }
    
    @PostMapping("/assignTask")
    public TasksResponse postMethodName(@RequestBody TaskAssignmentRequest taskAssignmentRequestObj) {
        return taskServiceObj.assignTaskServiceMethod(taskAssignmentRequestObj);
    }
    
    @PostMapping("/addCompany")
    public CompaniesResponse addCompany(@RequestBody CompaniesRequest companiesRequestObj) {
        return companiesServiceObj.addCompanyServiceMethod(companiesRequestObj);
    }

    @PutMapping("/changeSubscription/{companyId}")
    public CompaniesResponse updateCompanySubscription(@PathVariable Long companyId, @RequestBody CompaniesRequest companiesUpdateRequestObj) {
        
        return companiesServiceObj.updateCompanySubscription(companyId, companiesUpdateRequestObj);
    }

    @GetMapping("/getAllCompanies")
    public CompaniesResponse getMethodName() {
        return companiesServiceObj.getAllCompanies();
    }
    
    @PostMapping("/addProduct")
    public ProductsResponse addProduct(@RequestBody ProductsRequest productsRequestObj) {
        return productsServiceObj.addProductServiceMethod(productsRequestObj);
    }
    
    @PostMapping("/addBanner")
    public BannerResponse addBanner(@RequestBody BannerRequest bannerRequestObj) throws ParseException {
        return bannerServiceObj.addBannerServiceMethod(bannerRequestObj);
    }

    @PutMapping("/updateBannerExpiry/{bannerId}")
    public BannerResponse putMethodName(@PathVariable Long bannerId, @RequestBody BannerRequest bannerUpdateRequestObj) throws ParseException {
        
        return bannerServiceObj.updateBannerServiceMethod(bannerId, bannerUpdateRequestObj);
    }
    
}
