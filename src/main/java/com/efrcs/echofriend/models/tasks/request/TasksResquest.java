package com.efrcs.echofriend.models.tasks.request;

import lombok.Data;

@Data
public class TasksResquest {
    private String creatorEmail;
    private String taskDescription;
    private int taskPoints;
}
