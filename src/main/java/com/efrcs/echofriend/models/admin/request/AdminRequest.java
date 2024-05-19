package com.efrcs.echofriend.models.admin.request;

import lombok.Data;

@Data
public class AdminRequest {
    String adminName;
    String adminEmail;
    String adminPassword;
    String adminType;
}
