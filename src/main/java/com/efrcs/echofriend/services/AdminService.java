package com.efrcs.echofriend.services;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efrcs.echofriend.appconsts.AppConstants;
import com.efrcs.echofriend.entities.admin.AdminEntity;
import com.efrcs.echofriend.exceptions.RecordAlreadyExistsException;
import com.efrcs.echofriend.exceptions.RecordNotFoundException;
import com.efrcs.echofriend.models.admin.request.AdminRequest;
import com.efrcs.echofriend.models.admin.response.AdminResponse;
import com.efrcs.echofriend.repositories.adminrepo.AdminRespo;
import com.efrcs.echofriend.utility.Utility;

@Service
public class AdminService {
    @Autowired
    AdminRespo adminRespoObj;

    public AdminResponse adminSignupServiceMethod(AdminRequest adminRequestObj) throws NoSuchAlgorithmException {
        AdminResponse response = new AdminResponse();

        if(adminRespoObj.existsByEmail(adminRequestObj.getAdminEmail())){
            // response.setResponseCode(AppConstants.RECORD_EXISTS);
            response.setResponseMessage(AppConstants.RECORD_EXISTS_MESSAGE);
            throw new RecordAlreadyExistsException("Record already exists in the database.");
        }

        adminRespoObj.save(new AdminEntity(
            null, adminRequestObj.getAdminName(), 
            adminRequestObj.getAdminEmail(), 
            Utility.hashPassword(adminRequestObj.getAdminPassword()), 
            adminRequestObj.getAdminType()
        ));

        // response.setResponseCode(AppConstants.SUCCESS);
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }

    public AdminResponse adminloginResponse(AdminRequest adminRequestObj) throws NoSuchAlgorithmException{

        AdminEntity dbResponse = adminRespoObj.findAdminIdAndTypeByEmail(adminRequestObj.getAdminEmail());
        if(dbResponse == null){
            throw new RecordNotFoundException("email or password not correct.");
        }
        if(!Utility.compareHashes(dbResponse.getPassword(), Utility.hashPassword(adminRequestObj.getAdminPassword()))){
            throw new RecordNotFoundException("email or password not correct.");
        }
        AdminResponse response = new AdminResponse();
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        dbResponse.setPassword(null);
        response.setData(dbResponse);
        return response;
    }

    public AdminResponse getAdminsServiceMethod(Long adminId){
        AdminResponse response = new AdminResponse();
        if(adminId==null){
            response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
            response.setData(adminRespoObj.findAll());
            return response;
        }

        List<AdminEntity> admins = new ArrayList<>();
        admins.add(adminRespoObj.findById(adminId).orElseThrow(
            ()-> new RecordNotFoundException("Record of admin '" + adminId + "' does not exists")
        ));
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        response.setData(admins);
        return response;
    }

    public AdminResponse updateAdmin(Long adminId, AdminRequest adminRequestObj) throws NoSuchAlgorithmException{
        AdminResponse response = new AdminResponse();
        Optional<AdminEntity> dbresponse= adminRespoObj.findById(adminId);
        if(!dbresponse.isPresent()){
            throw new RecordNotFoundException("Record of adminId '" + adminId + "' does not exist.");
        }

        AdminEntity responseEntity = dbresponse.get();

        responseEntity.setAdmintype(
            (adminRequestObj.getAdminType()==null || adminRequestObj.getAdminType().isEmpty())?
            responseEntity.getAdmintype():adminRequestObj.getAdminType()
        );

        responseEntity.setEmail(
            (adminRequestObj.getAdminEmail()==null || adminRequestObj.getAdminEmail().isEmpty())?
            responseEntity.getEmail():adminRequestObj.getAdminEmail()
        );

        responseEntity.setPassword(
            (adminRequestObj.getAdminPassword()==null || adminRequestObj.getAdminPassword().isEmpty())?
            responseEntity.getPassword():Utility.hashPassword(adminRequestObj.getAdminPassword())
        );

        responseEntity.setUsername(
            (adminRequestObj.getAdminName()==null || adminRequestObj.getAdminName().isEmpty())?
            responseEntity.getUsername():adminRequestObj.getAdminName()
        );

        adminRespoObj.save(responseEntity);
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }
}
