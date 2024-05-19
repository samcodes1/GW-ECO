package com.efrcs.echofriend.entities.task;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private Long taskcreatedby;
}
