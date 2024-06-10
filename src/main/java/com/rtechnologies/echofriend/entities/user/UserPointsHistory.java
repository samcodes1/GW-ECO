package com.rtechnologies.echofriend.entities.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity( name = "userhistory" )
@Table( name = "userhistory" )
public class UserPointsHistory {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long userhistoryid;

    private Long useridfk;
    private Integer totalpoinysearned;
    private Integer totalpointsredeemed;
}
