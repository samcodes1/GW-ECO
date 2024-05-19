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
