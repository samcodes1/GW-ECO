package com.rtechnologies.echofriend.models.user.request;

import com.rtechnologies.echofriend.appconsts.Membershiptype;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequest {
    private String userName;
    private String email;
    private String password;
    private MultipartFile profilephoto;
    private String fullname;
}
