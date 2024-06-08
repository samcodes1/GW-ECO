package com.rtechnologies.echofriend.entities.otp;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity( name = "otp" )
@Table( name = "otp" )
public class OtpEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long otpid;
    private String otp;
    private Boolean isused;
    private Timestamp expired;
    private Long companyidfk;
    private Timestamp createdat;
}
