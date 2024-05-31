package com.rtechnologies.echofriend.services;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.rtechnologies.echofriend.appconsts.Membershiptype;
import com.rtechnologies.echofriend.models.user.request.UserUpdateRequest;
import com.rtechnologies.echofriend.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Cloudinary cloudinary;

    public UserResponse userSignupServiceMethod(UserRequest userRequestObj) throws NoSuchAlgorithmException, ParseException {
        if (userRepoObj.existsByEmail(userRequestObj.getEmail())) {
            throw new RecordAlreadyExistsException("Record already exists in the database.");
        }

        String profilePicUrl = "";
        try {
            String folder = "profile-pics"; // Change this to your preferred folder name
            String publicId = folder + "/" + userRequestObj.getProfilephoto().getName();
            Map data = cloudinary.uploader().upload(userRequestObj.getProfilephoto().getBytes(), ObjectUtils.asMap("public_id", publicId));
            profilePicUrl = data.get("secure_url").toString();
        } catch (IOException ioException) {
            throw new RuntimeException("File uploading failed");
        }

        String hashedPassword = new BCryptPasswordEncoder().encode(userRequestObj.getPassword());
        userRequestObj.setPassword(hashedPassword);

        UserEntity userEntity = UserEntity.builder()
                .username(userRequestObj.getUserName())
                .email(userRequestObj.getEmail())
                .password(hashedPassword)
                .membershiptype(Membershiptype.FREE) // Setting membership as free
                .points(0) // Setting points as 0
                .profilephoto(profilePicUrl)
                .memebershipexpiry(new Timestamp(-1)) // Setting timestamp as -1
                .build();

        userRepoObj.save(userEntity);

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

    public UserResponse updateUser(Long userId, UserUpdateRequest userUpdateRequestObj) throws NoSuchAlgorithmException, ParseException {
        Optional<UserEntity> existingUserOpt = userRepoObj.findById(userId);
        if (!existingUserOpt.isPresent()) {
            throw new RecordNotFoundException("User not found.");
        }

        UserEntity existingUser = existingUserOpt.get();

        String profilePicUrl = "";
        if(userUpdateRequestObj.getProfilephoto() != null) {
            try {
                String folder = "profile-pics"; // Change this to your preferred folder name
                String publicId = folder + "/" + userUpdateRequestObj.getProfilephoto().getName();
                Map data = cloudinary.uploader().upload(userUpdateRequestObj.getProfilephoto().getBytes(), ObjectUtils.asMap("public_id", publicId));
                profilePicUrl = data.get("secure_url").toString();
            } catch (IOException ioException) {
                throw new RuntimeException("File uploading failed");
            }
        } else {
            profilePicUrl = existingUser.getProfilephoto();
        }


        UserEntity updatedUserEntity = UserEntity.builder()
                .userid(existingUser.getUserid())
                .username(userUpdateRequestObj.getUserName() != null ? userUpdateRequestObj.getUserName() : existingUser.getUsername())
                .email(userUpdateRequestObj.getEmail() != null ? userUpdateRequestObj.getEmail() : existingUser.getEmail())
                .password(existingUser.getPassword()) // Keeping existing password
                .membershiptype(existingUser.getMembershiptype()) // Keeping existing membership type
                .points(existingUser.getPoints()) // Keeping existing points
                .profilephoto(profilePicUrl)
                .memebershipexpiry(existingUser.getMemebershipexpiry()) // Keeping existing membership expiry
                .build();

        userRepoObj.save(updatedUserEntity);

        UserResponse response = new UserResponse();
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }


    public UserResponse deleteUserServiceMethod(Long userId){
        Optional<UserEntity> existingUserOpt = userRepoObj.findById(userId);
        if (!existingUserOpt.isPresent()) {
            throw new RecordNotFoundException("User not found.");
        }

        userRepoObj.deleteById(userId);
        UserResponse response = new UserResponse();
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }

    public UserResponse getUser(Long userId){
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
