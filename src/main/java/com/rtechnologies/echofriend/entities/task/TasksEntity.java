package com.rtechnologies.echofriend.entities.task;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity( name = "tasks" )
@Table( name = "tasks" )
public class TasksEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long taskID;
    private String taskdescription;
    private int pointsassigned;
    private String taskname;
    private Long taskcreatedby;
    private Long taskcategoryfk;
}
