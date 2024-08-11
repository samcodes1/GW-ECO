package com.rtechnologies.echofriend.entities.task;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface  TaskUserProjection {
    public Long getTaskuserid();
    public Long getUserid();
    public Long getTaskidfk();
    public Long getUseridfk();
    public Boolean getIscomplete();
    public Long getTaskID();
    public String getTaskdescription();
    public Integer getPointsassigned();
    public String getTaskname();
    public Long getTaskcreatedby();
    public Long getTaskcategoryfk();
    public Long getTaskcount();
    public String getEmail();
    public String getMembershiptype();
    public String getProfilephoto();
    public String getRole();
    public String getUsername();
    public Timestamp getMemebershipexpiry();
    public String getTaskcategory();
    public String getQrcode();
    public Integer getTotalsteps();
    public String getApplieddatetime();
    public Integer getTaskduration();
}
