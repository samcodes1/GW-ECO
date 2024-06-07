package com.rtechnologies.echofriend.entities.user;

import java.sql.Date;
import java.sql.Timestamp;

import com.rtechnologies.echofriend.appconsts.Membershiptype;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity( name = "users" )
@Table( name = "users" )
public class UserEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long userid;
    private String username;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING) 
    private Membershiptype membershiptype;
    private Integer points;
    private String profilephoto;
    private Timestamp memebershipexpiry;
    private String role;
    private String fullname;
    private Date joiningdate;
}
