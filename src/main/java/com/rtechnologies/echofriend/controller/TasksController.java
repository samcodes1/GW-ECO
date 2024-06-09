package com.rtechnologies.echofriend.controller;

import com.rtechnologies.echofriend.models.tasks.request.TaskAssignmentRequest;
import com.rtechnologies.echofriend.models.tasks.request.TasksResquest;
import com.rtechnologies.echofriend.models.tasks.response.TasksResponse;
import com.rtechnologies.echofriend.services.TaskService;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rtechnologies.echofriend.appconsts.AppConstants;
import com.rtechnologies.echofriend.entities.task.TaskCategoryEntity;
import com.rtechnologies.echofriend.entities.task.TaskUserAssociation;
import com.rtechnologies.echofriend.entities.task.TasksEntity;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;







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

    @GetMapping("/getAllTask")
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
    
    @GetMapping("/getRecentTasks")
    public ResponseEntity<TasksResponse> ecentTask(@RequestParam(required = false) String email, @RequestParam(required = false) Integer limit) {
        TasksResponse response = taskServiceObj.recentTasks(email, limit);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }

    @PostMapping("/applyForTask")
    public ResponseEntity<TasksResponse> postMethodName(@RequestBody TaskUserAssociation userTaskObj) {
        TasksResponse response = taskServiceObj.applyForTask(userTaskObj);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }

    @PutMapping("/markTaskComplete")
    public ResponseEntity<TasksResponse> markTaskComplete(@RequestBody TaskUserAssociation userTaskObj) {
        TasksResponse response = taskServiceObj.markTaskComplete(userTaskObj);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }
    
    @GetMapping("/getTasks")
    public ResponseEntity<TasksResponse> getTasks(@RequestParam Long userid, @RequestParam Boolean taskstatus) {
        TasksResponse response = taskServiceObj.getTasks(userid, taskstatus);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }

    @DeleteMapping("/deletetask/{taskid}")
    public ResponseEntity<TasksResponse> deletetask(@PathVariable Long taskid) {
        TasksResponse response = taskServiceObj.deletetask(taskid);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }

    @PutMapping("/updatetask/{taskid}")
    public ResponseEntity<TasksResponse> putMethodName(@PathVariable Long taskid, @RequestBody TasksEntity tasksEntityObj) {
        TasksResponse response = taskServiceObj.updatetask(taskid, tasksEntityObj);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }

    @GetMapping("/getCompanytasks")
    public ResponseEntity<TasksResponse> getCompanyTasks(@RequestParam(required = false) Long companyid) {
        TasksResponse response = taskServiceObj.getCompanyTasks(companyid);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }
    
    @GetMapping("/getTaskByUser")
    public ResponseEntity<TasksResponse> gettaskbyuser(@RequestParam(required = false) Long taskid,@RequestParam(required = false) Long userid) {
        TasksResponse response = taskServiceObj.getTasksbyUser(taskid, userid);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }


    @GetMapping("/generatebarcodeforalltasksdummyendpoint")
    public ResponseEntity<TasksResponse> gettaskbyuser() throws IOException {
        TasksResponse response = taskServiceObj.generate();
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }
}
