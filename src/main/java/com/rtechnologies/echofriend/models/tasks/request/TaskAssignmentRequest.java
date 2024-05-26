package com.rtechnologies.echofriend.models.tasks.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskAssignmentRequest {
    private String assignedByEmail;
    private String assignedToemail;
    private int taskId;
}
