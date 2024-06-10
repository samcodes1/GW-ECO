package com.rtechnologies.echofriend.entities;

import java.sql.Timestamp;

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
@Entity( name = "notification" )
@Table( name = "notification" )
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long notificationid;

    private String fcmid;
    private String title;
    private String message;
    private Long userid;
    private Timestamp notificationcreatedat;
}
