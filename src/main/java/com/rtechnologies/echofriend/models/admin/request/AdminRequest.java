package com.rtechnologies.echofriend.models.admin.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminRequest {
    String adminName;
    String adminEmail;
    String adminPassword;
    String adminType;
}
