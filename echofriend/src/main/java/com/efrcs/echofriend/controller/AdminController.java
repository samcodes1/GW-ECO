package com.efrcs.echofriend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.efrcs.echofriend.models.admin.request.AdminRequest;
import com.efrcs.echofriend.models.admin.response.AdminResponse;
import com.efrcs.echofriend.models.tasks.request.TaskAssignmentRequest;
import com.efrcs.echofriend.models.tasks.request.TasksResquest;
import com.efrcs.echofriend.models.tasks.response.TasksResponse;
import com.efrcs.echofriend.services.AdminService;
import com.efrcs.echofriend.services.TaskService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class AdminController {

    @Autowired
    AdminService adminServiceObj;

    @Autowired
    TaskService taskServiceObj;

    @PostMapping("/adminSignUp")
    public AdminResponse adminSignup(@RequestBody AdminRequest adminRequestObj) {
        
        return adminServiceObj.adminSignupServiceMethod(adminRequestObj);
        
    }

    @PostMapping("/createTask")
    public TasksResponse createTask(@RequestBody TasksResquest tasksResquestObj) {
        
        return taskServiceObj.createTaskServiceMethod(tasksResquestObj);
        
    }
    
    @PostMapping("/assignTask")
    public TasksResponse postMethodName(@RequestBody TaskAssignmentRequest taskAssignmentRequestObj) {
        return taskServiceObj.assignTaskServiceMethod(taskAssignmentRequestObj);
    }
}
