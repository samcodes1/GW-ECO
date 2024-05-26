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
@Entity( name = "assignmenttoadmin" )
@Table( name = "assignmenttoadmin" )
public class TaskAssigmentEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long assignmentid;

    private Long assignedbysafk;
    private Long assindedtoadminfk;
    private int assignedtaskfk;

}
