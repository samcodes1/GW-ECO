package com.efrcs.echofriend.services;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

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

}
