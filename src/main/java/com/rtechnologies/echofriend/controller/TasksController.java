package com.rtechnologies.echofriend.controller;

import com.rtechnologies.echofriend.models.tasks.request.TaskAssignmentRequest;
import com.rtechnologies.echofriend.models.tasks.request.TasksResquest;
import com.rtechnologies.echofriend.models.tasks.response.TasksResponse;
import com.rtechnologies.echofriend.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rtechnologies.echofriend.appconsts.AppConstants;
import com.rtechnologies.echofriend.entities.task.TaskCategoryEntity;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;



@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/task")
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
    @PostMapping("/create")
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
    @PostMapping("/assign")
    public ResponseEntity<TasksResponse> postMethodName(@RequestBody TaskAssignmentRequest taskAssignmentRequestObj) {
        TasksResponse response = taskServiceObj.assignTaskServiceMethod(taskAssignmentRequestObj);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }

    @GetMapping("/getTopTask")
    public ResponseEntity<TasksResponse> getMethodName() {
        TasksResponse response = taskServiceObj.topTask();
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }

    @GetMapping("/getTask")
    public ResponseEntity<TasksResponse> getMethodName(@RequestParam(required = false) Long taskId) {
        TasksResponse response = taskServiceObj.getTask(taskId);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }

    @PostMapping("/createTaskCategory")
    public ResponseEntity<TasksResponse> postMethodName(@RequestBody TaskCategoryEntity taskCategoryEntityObj) {
        TasksResponse response = taskServiceObj.createTaskCategory(taskCategoryEntityObj);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }

    @PostMapping("/getTaskCategory")
    public ResponseEntity<TasksResponse> getCategories(@RequestParam(required = false) Long taskCategoryId) {
        TasksResponse response = taskServiceObj.getTaskCategory(taskCategoryId);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }
    
    
}
