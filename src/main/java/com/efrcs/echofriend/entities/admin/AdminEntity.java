package com.efrcs.echofriend.entities.admin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Entity;
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
@Entity( name = "admins" )
@Table( name = "admins" )
public class AdminEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long adminid;
    private String username;
    private String email;
    private String password;
    private String admintype;

}
