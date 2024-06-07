package com.rtechnologies.echofriend.models.tasks.response;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface TaskCategortProjections {

    public Long getTaskID();
    public String getTaskdescription();
    public Integer getPointsassigned();
    public String getTaskname();

    public String getTaskcategory(); 

    public String getUsername();
    public String getEmail();
    public Timestamp getTask_created_time();
    public Integer getParticipants();
    
}
