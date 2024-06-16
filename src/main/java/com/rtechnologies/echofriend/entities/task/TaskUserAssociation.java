package com.rtechnologies.echofriend.entities.task;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity( name = "taskuserbridge" )
@Table( name = "taskuserbridge" )
public class TaskUserAssociation {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long taskuserid;

    private Long taskidfk;
    private Long useridfk;
    private Boolean iscomplete;
    private Timestamp applieddatetime;
}
