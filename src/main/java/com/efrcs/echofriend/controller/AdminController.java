package com.efrcs.echofriend.controller;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.efrcs.echofriend.appconsts.AppConstants;
import com.efrcs.echofriend.models.admin.request.AdminRequest;
import com.efrcs.echofriend.models.admin.response.AdminResponse;
import com.efrcs.echofriend.services.AdminService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class AdminController {

    @Autowired
    AdminService adminServiceObj;

    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "sign up success."),
            @ApiResponse(code = 400, message = "invalid request data."),
            @ApiResponse(code = 409, message = "Record already exists in the database."),
            @ApiResponse(code = 500, message = "internal server error."),
        }
    )
    @PostMapping("/adminSignUp")
    public ResponseEntity<AdminResponse> adminSignup(@RequestBody AdminRequest adminRequestObj) throws NoSuchAlgorithmException {
        AdminResponse response = adminServiceObj.adminSignupServiceMethod(adminRequestObj);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
        
    }

    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "sign up success."),
            @ApiResponse(code = 400, message = "invalid request data."),
            @ApiResponse(code = 409, message = "Record already exists in the database."),
            @ApiResponse(code = 500, message = "internal server error."),
        }
    )
    @PostMapping("/adminLogin")
    public ResponseEntity<AdminResponse> adminLogin(@RequestBody AdminRequest adminLoginRequestObj) throws NoSuchAlgorithmException {
        
        AdminResponse response = adminServiceObj.adminloginResponse(adminLoginRequestObj);
        
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }

    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "sign up success."),
            @ApiResponse(code = 400, message = "invalid request data."),
            @ApiResponse(code = 409, message = "Record already exists in the database."),
            @ApiResponse(code = 500, message = "internal server error."),
        }
    )
    @GetMapping("/getAdmins")
    public ResponseEntity<AdminResponse> getAdmins(@RequestParam(required = false) Long adminId) {
        AdminResponse response = adminServiceObj.getAdminsServiceMethod(adminId);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }
    
    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "sign up success."),
            @ApiResponse(code = 400, message = "invalid request data."),
            @ApiResponse(code = 409, message = "Record already exists in the database."),
            @ApiResponse(code = 500, message = "internal server error."),
        }
    )
    @PutMapping("updateAdmin/{adminId}")
    public ResponseEntity<AdminResponse> updateAdmin(@PathVariable Long adminId, @RequestBody AdminRequest adminLoginRequestObj) throws NoSuchAlgorithmException {
        AdminResponse response = adminServiceObj.updateAdmin(adminId, adminLoginRequestObj);

        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }
    
}
