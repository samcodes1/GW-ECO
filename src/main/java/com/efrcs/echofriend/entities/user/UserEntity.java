package com.efrcs.echofriend.entities.user;

import java.sql.Timestamp;

import com.efrcs.echofriend.appconsts.Membershiptype;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
}
