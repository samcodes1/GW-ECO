package com.rtechnologies.echofriend.models.tasks.request;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TasksResquest {
    private String creatorEmail;
    private String taskDescription;
    private int taskPoints;
    private String taskname;
    private Long taskcategory;
    private String externallink;
    private Long companyid;
    // private Timestamp taskCreatedTimestamp;
}
