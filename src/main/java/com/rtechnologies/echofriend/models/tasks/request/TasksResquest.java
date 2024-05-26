package com.rtechnologies.echofriend.models.tasks.request;

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
}
