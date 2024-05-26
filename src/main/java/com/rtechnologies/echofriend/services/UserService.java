package com.rtechnologies.echofriend.services;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.rtechnologies.echofriend.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rtechnologies.echofriend.exceptions.RecordAlreadyExistsException;
import com.rtechnologies.echofriend.exceptions.RecordNotFoundException;
import com.rtechnologies.echofriend.models.user.request.UserRequest;
import com.rtechnologies.echofriend.models.user.response.UserResponse;
import com.rtechnologies.echofriend.repositories.user.UserRepo;
import com.rtechnologies.echofriend.appconsts.AppConstants;
import com.rtechnologies.echofriend.entities.user.UserEntity;

@Service
public class UserService {
    @Autowired
    UserRepo userRepoObj;

    public UserResponse userSignupServiceMethod(UserRequest userRequestObj) throws NoSuchAlgorithmException, ParseException{
        if(userRepoObj.existsByEmail(userRequestObj.getEmail())){
            throw new RecordAlreadyExistsException("Record already exists in the database.");
        }
        userRepoObj.save(
            new UserEntity(
                null, userRequestObj.getUserName(),userRequestObj.getEmail(), Utility.hashPassword(userRequestObj.getPassword()),
                userRequestObj.getMembershiptype(), userRequestObj.getPoints(), userRequestObj.getProfilephoto(),
                (userRequestObj.getMemebershipexpiry()==null || userRequestObj.getMemebershipexpiry().isEmpty())?
                null:Utility.convertToTimestamp(userRequestObj.getMemebershipexpiry())
            )
        );
        UserResponse response = new UserResponse();
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }

    public UserResponse userLoginServiceMethod(UserRequest userRequestObj) throws NoSuchAlgorithmException{

        Optional<UserEntity> dbResponse = userRepoObj.findAdminIdAndTypeByEmail(userRequestObj.getEmail());
        if(!dbResponse.isPresent()){
            throw new RecordNotFoundException("email or password not correct.");
        }
        if(!Utility.compareHashes(dbResponse.get().getPassword(), Utility.hashPassword(userRequestObj.getPassword()))){
            throw new RecordNotFoundException("email or password not correct.");
        }
        UserResponse response = new UserResponse();
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        dbResponse.get().setPassword(null);
        response.setData(dbResponse.get());
        return response;
    }

    public UserResponse updateUserServiceMethod(Long usrId, UserRequest userRequestObj) throws ParseException, NoSuchAlgorithmException{
        Optional<UserEntity> userData = userRepoObj.findById(usrId);
        if(!userData.isPresent()){
            throw new RecordNotFoundException("user not found");
        }
        UserEntity userDbData = userData.get();

        userDbData.setEmail(
            (userRequestObj.getEmail()==null || userRequestObj.getEmail().isEmpty())?
            userDbData.getEmail():userRequestObj.getEmail()
        );
        userDbData.setMembershiptype(
            (userRequestObj.getMembershiptype()==null)?
            userDbData.getMembershiptype():userRequestObj.getMembershiptype()
        );
        userDbData.setMemebershipexpiry(
            (userRequestObj.getMemebershipexpiry()==null || userRequestObj.getMemebershipexpiry().isEmpty())?
            userDbData.getMemebershipexpiry():Utility.convertToTimestamp(userRequestObj.getMemebershipexpiry())
        );
        userDbData.setPassword(
            (userRequestObj.getPassword()==null || userRequestObj.getPassword().isEmpty())?
            userDbData.getPassword():Utility.hashPassword(userRequestObj.getPassword())
        );
        userDbData.setPoints(
            (userRequestObj.getPoints()==null)?
            userDbData.getPoints():userRequestObj.getPoints()
        );
        userDbData.setProfilephoto(
            (userRequestObj.getProfilephoto()==null || userRequestObj.getProfilephoto().isEmpty())?
            userDbData.getProfilephoto():userRequestObj.getProfilephoto()
        );
        userDbData.setUsername(
            (userRequestObj.getUserName()==null || userRequestObj.getUserName().isEmpty())?
            userDbData.getUsername():userRequestObj.getUserName()
        );

        userRepoObj.save(userDbData);
        UserResponse response = new UserResponse();
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        userDbData.setPassword(null);
        response.setData(userDbData);
        return response;
    }

    public UserResponse deleteUserServiceMethod(Long usrId){
        userRepoObj.deleteById(usrId);
        UserResponse response = new UserResponse();
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }

    public UserResponse getUsersServiceMethod(Long userId){
        UserResponse response = new UserResponse();
        if(userId==null){
            response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
            response.setData(userRepoObj.findAll());
            return response;
        }

        List<UserEntity> users = new ArrayList<>();
        users.add(userRepoObj.findById(userId).orElseThrow(
            ()-> new RecordNotFoundException("Record of user '" + userId + "' does not exists")
        ));
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        response.setData(users);
        return response;

    }
}
