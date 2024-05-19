package com.efrcs.echofriend.models.tasks.request;

import lombok.Data;

@Data
public class TaskAssignmentRequest {
    private String assignedByEmail;
    private String assignedToemail;
    private int taskId;
}
