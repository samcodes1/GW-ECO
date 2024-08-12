package com.rtechnologies.echofriend.models.user.request;

import java.sql.Timestamp;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rtechnologies.echofriend.appconsts.Membershiptype;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminUserUpdate {
    private Timestamp expiryUpdate;
    private String role;
    @Enumerated(EnumType.STRING) 
    private Membershiptype membership;
    private Integer points;
}
