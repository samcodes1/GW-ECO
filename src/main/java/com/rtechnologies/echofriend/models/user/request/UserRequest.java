package com.rtechnologies.echofriend.models.user.request;

import com.rtechnologies.echofriend.appconsts.Membershiptype;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequest {
    private String userName;
    private String email;
    private String password;
    private Membershiptype membershiptype;
    private Integer points;
    private String profilephoto;
    private String memebershipexpiry;
}
