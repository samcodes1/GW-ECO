package com.rtechnologies.echofriend.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rtechnologies.echofriend.appconsts.AppConstants;
import com.rtechnologies.echofriend.entities.admin.AdminEntity;
import com.rtechnologies.echofriend.entities.task.TaskAssigmentEntity;
import com.rtechnologies.echofriend.entities.task.TaskCategoryEntity;
import com.rtechnologies.echofriend.entities.task.TasksEntity;
import com.rtechnologies.echofriend.exceptions.OperationNotAllowedException;
import com.rtechnologies.echofriend.exceptions.RecordAlreadyExistsException;
import com.rtechnologies.echofriend.exceptions.RecordNotFoundException;
import com.rtechnologies.echofriend.models.tasks.request.TaskAssignmentRequest;
import com.rtechnologies.echofriend.models.tasks.request.TasksResquest;
import com.rtechnologies.echofriend.models.tasks.response.TaskCategortProjections;
import com.rtechnologies.echofriend.models.tasks.response.TasksResponse;
import com.rtechnologies.echofriend.repositories.adminrepo.AdminRespo;
import com.rtechnologies.echofriend.repositories.tasks.TaskAssignmentRepo;
import com.rtechnologies.echofriend.repositories.tasks.TaskCategoryRepo;
import com.rtechnologies.echofriend.repositories.tasks.TaskRepo;

@Service
public class TaskService {

    @Autowired
    TaskRepo taskRepoObj;

    @Autowired
    AdminRespo adminRespoObj;

    @Autowired
    TaskCategoryRepo taskCategoryRepoObj;

    @Autowired
    TaskAssignmentRepo taskAssignmentRepoObj;

    public TasksResponse createTaskServiceMethod(TasksResquest tasksResquestObj) {
        TasksResponse response = new TasksResponse();

        if(!adminRespoObj.existsByEmail(tasksResquestObj.getCreatorEmail())){
            // response.setResponseCode(AppConstants.RECORD_DOES_NOT_EXISTS);
            response.setResponseMessage(AppConstants.ADMIN_DOES_NOT_EXISTS);
            // return response;
            throw new RecordNotFoundException("Record with email '" + tasksResquestObj.getCreatorEmail() + "' does not exist.");
        }

        AdminEntity result = adminRespoObj.findAdminIdAndTypeByEmail(tasksResquestObj.getCreatorEmail());
        
        String adminType = result.getAdmintype();

        if(! AppConstants.SUPER_ADMIN.equalsIgnoreCase(adminType)){
            // response.setResponseCode(AppConstants.CANNOT_INSERT_DATA);
            response.setResponseMessage(AppConstants.CANNOT_INSERT_DATA_MESSAGE);
            // return response;
            throw new OperationNotAllowedException("email '" + tasksResquestObj.getCreatorEmail() + "' is not allwed to create task");
        }

        Long adminId = result.getId();

        taskRepoObj.save(new TasksEntity(
            null, tasksResquestObj.getTaskDescription(),tasksResquestObj.getTaskPoints(), tasksResquestObj.getTaskname(),adminId,tasksResquestObj.getTaskcategory()
        ));

        // response.setResponseCode(AppConstants.SUCCESS);
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);

        return response;
    }

    public TasksResponse assignTaskServiceMethod(TaskAssignmentRequest taskAssignmentRequestObj){
        TasksResponse response = new TasksResponse();
        // TODO: also check if task exists or not
        if(
            !adminRespoObj.existsByEmail(taskAssignmentRequestObj.getAssignedByEmail())
            || !adminRespoObj.existsByEmail(taskAssignmentRequestObj.getAssignedToemail())
        ){
            // response.setResponseCode(AppConstants.RECORD_DOES_NOT_EXISTS);
            response.setResponseMessage(AppConstants.ADMIN_DOES_NOT_EXISTS);
            throw new RecordAlreadyExistsException("Record already exists in the database.");
            // return response;
        }

        AdminEntity assignedByDBdata = adminRespoObj.findAdminIdAndTypeByEmail(taskAssignmentRequestObj.getAssignedByEmail());

        if(! AppConstants.SUPER_ADMIN.equalsIgnoreCase(assignedByDBdata.getAdmintype())){
            // response.setResponseCode(AppConstants.ONLY_SA_CAN_ASSIGN_TASK);
            response.setResponseMessage(AppConstants.ONLY_SA_CAN_ASSIGN_TASK_MESSAGE);
            throw new OperationNotAllowedException("email '" + taskAssignmentRequestObj.getAssignedByEmail() + "' is not allwed to assign task");
            // return response;
        }

        AdminEntity assignedToDBdata = adminRespoObj.findAdminIdAndTypeByEmail(taskAssignmentRequestObj.getAssignedToemail());
        
        if(AppConstants.SUPER_ADMIN.equalsIgnoreCase(assignedToDBdata.getAdmintype())){
            // response.setResponseCode(AppConstants.CANNOT_ASSIGN_TASK_TO_SA);
            response.setResponseMessage(AppConstants.CANNOT_ASSIGN_TASK_TO_SA_MESSAGE);
            throw new OperationNotAllowedException("can not assign task to the user.");
            
        }

        taskAssignmentRepoObj.save(new TaskAssigmentEntity(
            null, assignedByDBdata.getId(), assignedToDBdata.getId(), taskAssignmentRequestObj.getTaskId()
        ));

        // response.setResponseCode(AppConstants.TASK_ASSIGNED);
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }

    public TasksResponse topTask(){
        TasksResponse response = new TasksResponse();
        // taskRepoObj.findTopSixTasks();
        response.setData(taskRepoObj.findTopSixTasks());
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }

    public TasksResponse getTask(Long id){
        TasksResponse response = new TasksResponse();
        if(id!=null){
            // List <TaskCategortProjections> taskList = new ArrayList<>();
            // taskList.add(taskRepoObj.findtaskcategory(id));
            response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
            response.setData(taskRepoObj.findtaskcategory(id));
            return response;
        }

        response.setData(taskRepoObj.findtaskcategory());
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }

    public TasksResponse createTaskCategory(TaskCategoryEntity taskCategoryEntityObj){
        taskCategoryRepoObj.save(taskCategoryEntityObj);
        TasksResponse response = new TasksResponse();
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }

    public TasksResponse getTaskCategory(Long id){
        // taskCategoryRepoObj.save(taskCategoryEntityObj);
        TasksResponse response = new TasksResponse();
        if(id == null){
            List<TaskCategoryEntity> res = new ArrayList<>();
            res.add(taskCategoryRepoObj.findById(id).get());
            response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
            response.setData(res);
            return response;
        }
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        response.setData(taskCategoryRepoObj.findAll());
        return response;
    }

    public TasksResponse recentTasks(String email, Integer limit){
        TasksResponse response = new TasksResponse();
        if(email == null && limit == null){
            response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
            response.setData(taskRepoObj.findAllRecentTask());
            return response;
        }

        if(email == null && limit != null){
            response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
            response.setData(taskRepoObj.findAllRecentTaskLimit(limit));
            return response;
        }

        if(email != null && limit == null){
            AdminEntity admin = adminRespoObj.findAdminIdAndTypeByEmail(email);

            response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
            response.setData(taskRepoObj.findAllRecentTaskCreatedBy(admin.getId()));
            return response;
        }

        if(email != null && limit != null){
            AdminEntity admin = adminRespoObj.findAdminIdAndTypeByEmail(email);

            response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
            response.setData(taskRepoObj.findAllRecentTaskCreatedByLimit(admin.getId(), limit));
            return response;
        }
        return null;
    }
}
