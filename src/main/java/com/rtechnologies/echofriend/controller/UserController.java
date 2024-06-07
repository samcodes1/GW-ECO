package com.rtechnologies.echofriend.controller;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import com.rtechnologies.echofriend.models.user.request.AdminUserUpdate;
import com.rtechnologies.echofriend.models.user.request.UserRequest;
import com.rtechnologies.echofriend.models.user.request.UserUpdateRequest;
import com.rtechnologies.echofriend.models.user.response.UserResponse;
import com.rtechnologies.echofriend.services.UserService;
import com.rtechnologies.echofriend.services.VoucherService;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rtechnologies.echofriend.appconsts.AppConstants;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.multipart.MultipartFile;





@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userServiceObj;

    @Autowired
    VoucherService voucherServiceObj;

    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "sign up success."),
            @ApiResponse(code = 400, message = "invalid request data."),
            @ApiResponse(code = 409, message = "Record already exists in the database."),
            @ApiResponse(code = 500, message = "internal server error."),
        }
    )
    @PostMapping("/sign-up")
    public ResponseEntity<UserResponse> userSignup(
            @RequestParam String userName,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(required = false) MultipartFile profilephoto) throws NoSuchAlgorithmException, ParseException {

        UserRequest userRequestObj = UserRequest.builder()
                .userName(userName)
                .email(email)
                .password(password)
                .profilephoto(profilephoto)
                .build();

        UserResponse response = userServiceObj.userSignupServiceMethod(userRequestObj);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE) ? 200 : 500).body(response);
    }


//    @ApiResponses(
//        value = {
//            @ApiResponse(code = 200, message = "sign up success."),
//            @ApiResponse(code = 400, message = "invalid request data."),
//            @ApiResponse(code = 409, message = "Record already exists in the database."),
//            @ApiResponse(code = 500, message = "internal server error."),
//        }
//    )
//    @PostMapping("/userLogin")
//    public ResponseEntity<UserResponse> userLogin(@RequestBody UserRequest userRequestObj) throws NoSuchAlgorithmException, ParseException {
//        UserResponse response = userServiceObj.userLoginServiceMethod(userRequestObj);
//        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
//        200:500).body(response);
//
//    }

    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Update success."),
                    @ApiResponse(code = 400, message = "Invalid request data."),
                    @ApiResponse(code = 404, message = "User not found."),
                    @ApiResponse(code = 500, message = "Internal server error."),
            }
    )
    @PutMapping(value = "/update-user/{userId}", consumes = "multipart/form-data")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long userId,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) MultipartFile profilephoto) throws NoSuchAlgorithmException, ParseException {

        UserUpdateRequest userUpdateRequestObj = UserUpdateRequest.builder()
                .userName(userName)
                .email(email)
                .profilephoto(profilephoto)
                .build();

        UserResponse response = userServiceObj.updateUser(userId, userUpdateRequestObj);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE) ? 200 : 500).body(response);
    }


    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "sign up success."),
            @ApiResponse(code = 400, message = "invalid request data."),
            @ApiResponse(code = 409, message = "Record already exists in the database."),
            @ApiResponse(code = 500, message = "internal server error."),
        }
    )
    @DeleteMapping("/{userId}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable Long userId) throws NoSuchAlgorithmException, ParseException {
        UserResponse response = userServiceObj.deleteUserServiceMethod(userId);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }

    @GetMapping("/get")
    public ResponseEntity<UserResponse> getUsers(@RequestParam(required = false) Long userId) {
        UserResponse response = userServiceObj.getUser(userId);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }

    @ApiOperation(value = "Change Password for Mentee", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Password changed successfully"),
            @ApiResponse(code = 400, message = "Invalid request data"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PatchMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @RequestParam("email") String email,
            @RequestParam("oldPassword") String oldPassword,
            @RequestParam("newPassword") String newPassword) {

        String responseMessage = userServiceObj.changePassword(email, oldPassword, newPassword);
        return ResponseEntity.ok(responseMessage);
    }

    @GetMapping("/redeemVoucher")
    public ResponseEntity<UserResponse> redeemvoucher(@RequestParam Long userId, @RequestParam Long voucherid) {
        UserResponse response = userServiceObj.redeemVoucher(userId, voucherid);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }
    
    @GetMapping("/getAllNonUsedVouchers")
    public ResponseEntity<UserResponse> getAllNonUsedVouchers(@RequestParam Long userId) {
        UserResponse response = userServiceObj.getUnUsedVouchers(userId);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }

    @PutMapping("/adminUpdateUser/{userid}")
    public ResponseEntity<UserResponse> updateuser(@PathVariable Long userid, @RequestBody AdminUserUpdate updateReq) {
        UserResponse response = userServiceObj.updateUser(userid, updateReq);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }
    
    // @PutMapping("/updateUser/{userid}")
    // public ResponseEntity<UserResponse> updateuser(@PathVariable Long userid, @RequestBody String entity) {
    //     UserResponse response = userServiceObj.updateUser(userId);
    //     return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
    //     200:500).body(response);
    // }
    
}
