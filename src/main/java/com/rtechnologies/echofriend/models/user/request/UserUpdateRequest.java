package com.rtechnologies.echofriend.models.user.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rtechnologies.echofriend.appconsts.Membershiptype;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserUpdateRequest {
    private String userName;
    private String email;
    private MultipartFile profilephoto;
}
