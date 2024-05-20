package com.efrcs.echofriend.controller;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.efrcs.echofriend.appconsts.AppConstants;
import com.efrcs.echofriend.models.user.request.UserRequest;
import com.efrcs.echofriend.models.user.response.UserResponse;
import com.efrcs.echofriend.services.UserService;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserController {

    @Autowired
    UserService userServiceObj;

    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "sign up success."),
            @ApiResponse(code = 400, message = "invalid request data."),
            @ApiResponse(code = 409, message = "Record already exists in the database."),
            @ApiResponse(code = 500, message = "internal server error."),
        }
    )
    @PostMapping("/userSignUp")
    public ResponseEntity<UserResponse> userSignup(@RequestBody UserRequest userRequestObj) throws NoSuchAlgorithmException, ParseException {
        UserResponse response = userServiceObj.userSignupServiceMethod(userRequestObj);
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
    @PostMapping("/userLogin")
    public ResponseEntity<UserResponse> userLogin(@RequestBody UserRequest userRequestObj) throws NoSuchAlgorithmException, ParseException {
        UserResponse response = userServiceObj.userLoginServiceMethod(userRequestObj);
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
    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long userId, @RequestBody UserRequest userRequestObj) throws NoSuchAlgorithmException, ParseException {
        UserResponse response = userServiceObj.updateUserServiceMethod(userId, userRequestObj);

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
    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable Long userId, @RequestBody UserRequest userRequestObj) throws NoSuchAlgorithmException, ParseException {
        UserResponse response = userServiceObj.deleteUserServiceMethod(userId);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }
}
