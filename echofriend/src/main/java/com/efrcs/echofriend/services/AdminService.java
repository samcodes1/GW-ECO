package com.efrcs.echofriend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efrcs.echofriend.appconsts.AppConstants;
import com.efrcs.echofriend.entities.admin.AdminEntity;
import com.efrcs.echofriend.models.admin.request.AdminRequest;
import com.efrcs.echofriend.models.admin.response.AdminResponse;
import com.efrcs.echofriend.repositories.adminrepo.AdminRespo;

@Service
public class AdminService {
    @Autowired
    AdminRespo adminRespoObj;

    public AdminResponse adminSignupServiceMethod(AdminRequest adminRequestObj) {
        AdminResponse response = new AdminResponse();

        if(adminRespoObj.existsByEmail(adminRequestObj.getAdminEmail())){
            response.setResponseCode(AppConstants.RECORD_EXISTS);
            response.setResponseMessage(AppConstants.RECORD_EXISTS_MESSAGE);
            return response;
        }

        adminRespoObj.save(new AdminEntity(
            null, adminRequestObj.getAdminName(), 
            adminRequestObj.getAdminEmail(), 
            adminRequestObj.getAdminPassword(), 
            adminRequestObj.getAdminType()
        ));

        response.setResponseCode(AppConstants.SUCCESS);
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }

    
}
