package com.rtechnologies.echofriend.entities.task;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Timestamp;

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
    private Integer pointsassigned;
    private String taskname;
    private Long taskcreatedby;
    private Long taskcategoryfk;
    private Timestamp taskCreatedTime;
    private Boolean active;
    private String externallink;
    private Long companyidfk;
    private String tasktype;
    private Integer totalsteps;
    private String taskbarcode;
}
