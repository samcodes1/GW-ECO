package com.rtechnologies.echofriend.entities.userpayment;

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
@Entity( name = "userpayment" )
@Table( name = "userpayment" )
public class UserPaymentEntity{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long userpaymentid;

    private String usersubscription;
    private Float amount;
    private Timestamp paymentdate;
    private String transactionid;
    private Long useridfk;
}
