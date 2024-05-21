package com.efrcs.echofriend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.efrcs.echofriend.appconsts.AppConstants;
import com.efrcs.echofriend.models.tasks.request.TaskAssignmentRequest;
import com.efrcs.echofriend.models.tasks.request.TasksResquest;
import com.efrcs.echofriend.models.tasks.response.TasksResponse;
import com.efrcs.echofriend.services.TaskService;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

public class TasksController {

    @Autowired
    TaskService taskServiceObj;

    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "sign up success."),
            @ApiResponse(code = 400, message = "invalid request data."),
            @ApiResponse(code = 404, message = "record does not exists."),
            @ApiResponse(code = 403, message = "operation not allowed."),
            @ApiResponse(code = 500, message = "internal server error."),
        }
    )
    @PostMapping("/createTask")
    public ResponseEntity<TasksResponse> createTask(@RequestBody TasksResquest tasksResquestObj) {
        TasksResponse response = taskServiceObj.createTaskServiceMethod(tasksResquestObj);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }
    
    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "sign up success."),
            @ApiResponse(code = 400, message = "invalid request data."),
            @ApiResponse(code = 404, message = "record does not exists."),
            @ApiResponse(code = 403, message = "operation not allowed."),
            @ApiResponse(code = 500, message = "internal server error."),
        }
    )
    @PostMapping("/assignTask")
    public ResponseEntity<TasksResponse> postMethodName(@RequestBody TaskAssignmentRequest taskAssignmentRequestObj) {
        TasksResponse response = taskServiceObj.assignTaskServiceMethod(taskAssignmentRequestObj);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }
}
