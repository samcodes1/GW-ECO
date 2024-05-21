package com.efrcs.echofriend.models.user.request;

import com.efrcs.echofriend.appconsts.Membershiptype;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
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
