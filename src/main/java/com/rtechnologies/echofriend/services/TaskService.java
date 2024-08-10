package com.rtechnologies.echofriend.services;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.zxing.BarcodeFormat;
import com.rtechnologies.echofriend.appconsts.AppConstants;
import com.rtechnologies.echofriend.appconsts.Membershiptype;
import com.rtechnologies.echofriend.entities.admin.AdminEntity;
import com.rtechnologies.echofriend.entities.task.TaskAssigmentEntity;
import com.rtechnologies.echofriend.entities.task.TaskCategoryEntity;
import com.rtechnologies.echofriend.entities.task.TaskUserAssociation;
import com.rtechnologies.echofriend.entities.task.TasksEntity;
import com.rtechnologies.echofriend.entities.user.UserEntity;
import com.rtechnologies.echofriend.entities.user.UserPointsHistory;
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
import com.rtechnologies.echofriend.repositories.tasks.TaskUserRepo;
import com.rtechnologies.echofriend.repositories.user.UserHistoryRepo;
import com.rtechnologies.echofriend.repositories.user.UserRepo;
import com.rtechnologies.echofriend.utility.Utility;

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

    @Autowired 
    TaskUserRepo taskUserRepoObj;

    @Autowired
    UserRepo userRepoObj;

    @Autowired
    UserHistoryRepo userHistoryRepoObj;

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

        // if(! AppConstants.SUPER_ADMIN.equalsIgnoreCase(adminType)){
        //     // response.setResponseCode(AppConstants.CANNOT_INSERT_DATA);
        //     response.setResponseMessage(AppConstants.CANNOT_INSERT_DATA_MESSAGE);
        //     // return response;
        //     throw new OperationNotAllowedException("email '" + tasksResquestObj.getCreatorEmail() + "' is not allwed to create task");
        // }

        Long adminId = result.getId();
        // StringBuilder barcodeStringBuilder = new StringBuilder();

// barcodeStringBuilder.append(tasksResquestObj.getTaskPoints()).append("//")
//                     .append(tasksResquestObj.getTaskname()).append("//")
//                     .append(adminId).append("//")
//                     .append(Utility.getcurrentTimeStamp()).append("//")
//                     .append(true).append("//")
//                     .append(tasksResquestObj.getTasktype()).append("//")
//                     .append(tasksResquestObj.getTotalsteps());

// String barcodeString = barcodeStringBuilder.toString();
        Timestamp timestamp = Utility.getcurrentTimeStamp();
        taskRepoObj.save(new TasksEntity(
            null, tasksResquestObj.getTaskDescription(),tasksResquestObj.getTaskPoints(), 
            tasksResquestObj.getTaskname(),adminId,tasksResquestObj.getTaskcategory(), timestamp,true,tasksResquestObj.getExternallink(),
            tasksResquestObj.getCompanyid(),tasksResquestObj.getTasktype(),tasksResquestObj.getTotalsteps(),Utility.generateUniqueCode(timestamp),0,tasksResquestObj.getTaskduration()
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
            // !adminRespoObj.existsByEmail(taskAssignmentRequestObj.getAssignedToemail())
        ){
            // response.setResponseCode(AppConstants.RECORD_DOES_NOT_EXISTS);
            response.setResponseMessage(AppConstants.ADMIN_DOES_NOT_EXISTS);
            throw new RecordAlreadyExistsException("Record already exists in the database.");
            // return response;
        }

        AdminEntity assignedByDBdata = adminRespoObj.findAdminIdAndTypeByEmail(taskAssignmentRequestObj.getAssignedByEmail());

        // if(! AppConstants.SUPER_ADMIN.equalsIgnoreCase(assignedByDBdata.getAdmintype())){
        //     // response.setResponseCode(AppConstants.ONLY_SA_CAN_ASSIGN_TASK);
        //     response.setResponseMessage(AppConstants.ONLY_SA_CAN_ASSIGN_TASK_MESSAGE);
        //     throw new OperationNotAllowedException("email '" + taskAssignmentRequestObj.getAssignedByEmail() + "' is not allwed to assign task");
        //     // return response;
        // }

        AdminEntity assignedToDBdata = adminRespoObj.findAdminIdAndTypeByEmail(taskAssignmentRequestObj.getAssignedToemail());
        
        // if(AppConstants.SUPER_ADMIN.equalsIgnoreCase(assignedToDBdata.getAdmintype())){
        //     // response.setResponseCode(AppConstants.CANNOT_ASSIGN_TASK_TO_SA);
        //     response.setResponseMessage(AppConstants.CANNOT_ASSIGN_TASK_TO_SA_MESSAGE);
        //     throw new OperationNotAllowedException("can not assign task to the user.");
            
        // }

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
        if(id != null){
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

    public TasksResponse applyForTask(TaskUserAssociation userTaskObj){
        // Optional<TaskUserAssociation> voucherapplieddata = taskUserRepoObj.findByTaskidfk(userTaskObj.getTaskidfk());
        // if(voucherapplieddata.isPresent()){
        //     throw new OperationNotAllowedException("task already applied");
        // }
        Optional<TasksEntity> task = taskRepoObj.findById(userTaskObj.getTaskidfk());
        if(!task.isPresent()){
            throw new OperationNotAllowedException("task not present");
        }
        if(task.get().getTasktype().contains("qr") && task.get().getTasktype().contains("recycle")){
            System.out.println(Utility.getcurrentTimeStamp());
            Optional<TaskUserAssociation> voucherapplieddata = taskUserRepoObj.findByTaskidfkAndUseridfkDateTimeWeek(userTaskObj.getTaskidfk(), userTaskObj.getUseridfk(), Utility.getcurrentTimeStamp());
            if(voucherapplieddata.isPresent()){
                throw new OperationNotAllowedException("task already applied");
            }
    
            userTaskObj.setIscomplete(false);
            userTaskObj.setApplieddatetime(Utility.getcurrentTimeStamp());
            taskUserRepoObj.save(userTaskObj);
            TasksResponse response = new TasksResponse();
            response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
            return response;
        }else{
            System.out.println(Utility.getcurrentTimeStamp());
            Optional<TaskUserAssociation> voucherapplieddata = taskUserRepoObj.findByTaskidfkAndUseridfkDateTime(userTaskObj.getTaskidfk(), userTaskObj.getUseridfk(), Utility.getcurrentTimeStamp());
            if(voucherapplieddata.isPresent()){
                throw new OperationNotAllowedException("task already applied");
            }
    
            userTaskObj.setIscomplete(false);
            userTaskObj.setApplieddatetime(Utility.getcurrentTimeStamp());
            taskUserRepoObj.save(userTaskObj);
            TasksResponse response = new TasksResponse();
            response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
            return response;
        }
    }

    @Transactional
    public TasksResponse markTaskComplete(TaskUserAssociation userTaskObj){
        // TODO: one hour check
        Optional<TaskUserAssociation> voucherapplieddata;// = taskUserRepoObj.findByTaskidfkAndUseridfkDateTime(userTaskObj.getTaskidfk(), userTaskObj.getUseridfk(), Utility.getcurrentTimeStamp());
        
        Optional<TasksEntity> task = taskRepoObj.findById(userTaskObj.getTaskidfk());
        if(!task.isPresent()){
            throw new OperationNotAllowedException("task not present");
        }

        if(task.get().getTasktype().contains("qr") && task.get().getTasktype().contains("recycle")){
            voucherapplieddata = taskUserRepoObj.findByTaskidfkAndUseridfkDateTimeWeek(userTaskObj.getTaskidfk(), userTaskObj.getUseridfk(), Utility.getcurrentTimeStamp());
        }else{
            voucherapplieddata = taskUserRepoObj.findByTaskidfkAndUseridfkDateTime(userTaskObj.getTaskidfk(), userTaskObj.getUseridfk(), Utility.getcurrentTimeStamp());
        }

        if(!voucherapplieddata.isPresent()){
            throw new OperationNotAllowedException("This task has not been applied wrong action");
        }

        Optional<UserEntity> userdata = userRepoObj.findById(userTaskObj.getUseridfk());
        if(!userdata.isPresent()){
            throw new OperationNotAllowedException("User does not exists");
        }

        if(voucherapplieddata.get().getIscomplete()){
            throw new OperationNotAllowedException("This task is already complete");
        }


        // get task points from data base
        Optional<TasksEntity> taskpointsdata = taskRepoObj.findById(userTaskObj.getTaskidfk());
        // get user data from data base
        UserEntity updatepoints = userdata.get();
        // add up the points
        Optional<UserEntity> user = userRepoObj.findById(userTaskObj.getUseridfk());
        if(!user.isPresent()){
            throw new OperationNotAllowedException("User not allowed");
        }
        if(user.get().getMembershiptype().equals(Membershiptype.FREE)){
            updatepoints.setPoints(updatepoints.getPoints()==null?taskpointsdata.get().getPointsassigned():updatepoints.getPoints()+taskpointsdata.get().getPointsassigned());
            userRepoObj.save(updatepoints);
        }else{
            updatepoints.setPoints(updatepoints.getPoints()==null?taskpointsdata.get().getPointsassigned():updatepoints.getPoints()+(taskpointsdata.get().getPointsassigned()*2));
            userRepoObj.save(updatepoints);
        }
        
        TaskUserAssociation updatereq = voucherapplieddata.get();
        updatereq.setIscomplete(true);
        taskUserRepoObj.save(updatereq);
        Optional<UserPointsHistory> history = userHistoryRepoObj.findByUseridfk(userTaskObj.getUseridfk());
        if(history.isPresent()){
            UserPointsHistory historyEntity = history.get();
            historyEntity.setTotalpoinysearned(historyEntity.getTotalpoinysearned()==null?taskpointsdata.get().getPointsassigned():historyEntity.getTotalpoinysearned()+taskpointsdata.get().getPointsassigned());
            userHistoryRepoObj.save(historyEntity);
        }else{
            UserPointsHistory historyEntity = new UserPointsHistory(
                null,userTaskObj.getUseridfk(),taskpointsdata.get().getPointsassigned(),0
            );
            userHistoryRepoObj.save(historyEntity);

        }
        TasksResponse response = new TasksResponse();
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }

    public TasksResponse getTasks(Long userid, Boolean taskstatus){
        TasksResponse response = new TasksResponse();
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        response.setData(taskUserRepoObj.findTaskByUseridCompleteStatus(userid, taskstatus));
        return response;
    }

    public TasksResponse deletetask(Long deletetask){
        TasksResponse response = new TasksResponse();
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        taskRepoObj.deleteById(deletetask);
        return response;
    }

    public TasksResponse updatetask(Long taskid, TasksEntity tasksEntityObj){
        TasksResponse response = new TasksResponse();

        Optional<TasksEntity> taskdata = taskRepoObj.findById(taskid);

        if(!taskdata.isPresent()){
            throw new RecordNotFoundException("Record not found");
        }
        TasksEntity updateTask = taskdata.get();

        updateTask.setTaskdescription(
            tasksEntityObj.getTaskdescription()==null?updateTask.getTaskdescription():tasksEntityObj.getTaskdescription()
        );
        updateTask.setPointsassigned(
            tasksEntityObj.getPointsassigned()==null?updateTask.getPointsassigned():tasksEntityObj.getPointsassigned()
        );
        updateTask.setTaskname(
            tasksEntityObj.getTaskname()==null?updateTask.getTaskname():tasksEntityObj.getTaskname()
        );
        updateTask.setTaskcreatedby(
            tasksEntityObj.getTaskcreatedby()==null?updateTask.getTaskcreatedby():tasksEntityObj.getTaskcreatedby()
        );
        updateTask.setTaskcategoryfk(
            tasksEntityObj.getTaskcategoryfk()==null?updateTask.getTaskcategoryfk():tasksEntityObj.getTaskcategoryfk()
        );

        updateTask.setActive(
            tasksEntityObj.getActive()==null?updateTask.getActive():tasksEntityObj.getActive()
        );

        updateTask.setExternallink(
            tasksEntityObj.getExternallink()==null?updateTask.getExternallink():tasksEntityObj.getExternallink()
        );

        updateTask.setTasktype(
            tasksEntityObj.getTasktype()==null?updateTask.getTasktype():tasksEntityObj.getTasktype()
        );

        updateTask.setTotalsteps(
            tasksEntityObj.getTotalsteps()==null?updateTask.getTotalsteps():tasksEntityObj.getTotalsteps()
        );

        updateTask.setPromotionranking(
            tasksEntityObj.getPromotionranking()==null?updateTask.getPromotionranking():tasksEntityObj.getPromotionranking()
        );

        updateTask.setTaskduration(
            tasksEntityObj.getTaskduration()==null?updateTask.getTaskduration():tasksEntityObj.getTaskduration()
        );

        response.setData(taskRepoObj.save(updateTask));
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }

    public TasksResponse getCompanyTasks(Long companyid, Long companytaskcategoryid){
        TasksResponse response = new TasksResponse();
        if(companyid!=null && companytaskcategoryid==null){
            response.setData(taskRepoObj.findtasksbycompany(companyid));
            response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
            return response;
        }else if(companyid==null && companytaskcategoryid!=null){
            response.setData(taskRepoObj.findtasksbycompanyCategory(companytaskcategoryid));
            response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
            return response;
        }
        // response.setData(taskRepoObj.findtasksbycompany(companyid));
            response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
            return response;
    }

    public TasksResponse getTasksbyUser( Long taskid, Long userid){
        Optional<TaskUserAssociation> taskuserdata = taskUserRepoObj.findByTaskidfkAndUseridfk(taskid, userid);
        if(!taskuserdata.isPresent()){
            throw new OperationNotAllowedException("Task not applied");
        }
        // List<TaskCategortProjections> taskuserstatus = taskRepoObj.findtasksbyusertask(userid,taskid);
        TasksResponse response = new TasksResponse();
        response.setData(taskRepoObj.findtasksbyusertask(userid,taskid));
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }

    public TasksResponse generate() throws IOException{
        // Optional<TaskUserAssociation> taskuserdata = taskUserRepoObj.findByTaskidfkAndUseridfk(taskid, userid);
        Iterable<TasksEntity> all = taskRepoObj.findAll();
        for (TasksEntity tasksEntity : all) {
            System.out.println("_________________ "+tasksEntity.getTaskCreatedTime());
            tasksEntity.setQrcode(Utility.generateUniqueCode(tasksEntity.getTaskCreatedTime()));
            taskRepoObj.save(tasksEntity);
            // System.err.println(Utility.decodeBarcode(Utility.generateBarcode(barcodeStringBuilder.toString(),BarcodeFormat.CODE_128, 150, 50)));
        }
        // List<TaskCategortProjections> taskuserstatus = taskRepoObj.findtasksbyusertask(userid,taskid);
        TasksResponse response = new TasksResponse();
        // response.setData(taskRepoObj.findtasksbyusertask(userid,taskid));
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }

    public TasksResponse promot(Long taskid, Integer promotion, Boolean promot){
        Optional<TasksEntity> taskinfo = taskRepoObj.findById(taskid);
        if(!taskinfo.isPresent()){
            throw new OperationNotAllowedException("Task not applied");
        }
        TasksEntity promotionupdate = taskinfo.get();

        Integer currentPromotionValue = promotionupdate.getPromotionranking();

        if(promot!=null && promot){
            currentPromotionValue = currentPromotionValue==null?promotion:promotion+currentPromotionValue;
        }else{
            currentPromotionValue = currentPromotionValue==null?promotion:promotion-currentPromotionValue;
        }

        promotionupdate.setPromotionranking(
            currentPromotionValue
        );
        taskRepoObj.save(promotionupdate);
        TasksResponse response = new TasksResponse();
        // response.setData(taskRepoObj.findtasksbyusertask(userid,taskid));
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }

    public TasksResponse getTasksUseridtaskId(Long userid, Long taskid){
        TasksResponse response = new TasksResponse();
        // if(userid==null && taskid==null){

        // }else 
        if(userid!=null && taskid==null){
            response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
            response.setData(taskUserRepoObj.findTaskByUserid(userid));
            return response; 
        }else if(userid==null && taskid!=null){
            response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
            response.setData(taskUserRepoObj.findTaskByTaskId(taskid));
            return response; 
        }else if(userid!=null && taskid!=null){
            response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
            response.setData(taskUserRepoObj.findTaskByTaskIdUserid(taskid, userid));
            return response; 
        }else{
            response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
            response.setData(new ArrayList<>());
            return response; 
        }
        // response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        // response.setData(taskUserRepoObj.findTaskByUseridCompleteStatus(userid, taskstatus));
        // return response;
    }

    public TasksResponse getTaskStatusService(Long taskid){
        // taskUserRepoObj
        return null;
    }
}
