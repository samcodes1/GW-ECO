package com.rtechnologies.echofriend.entities.companypayment;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity( name = "companiespayment" )
@Table( name = "companiespayment" )
public class CompanyPaymentEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long companypaymentid;

    private String companysubscription;

    private Timestamp paymentdate;

    private String transactionid;

    private Long companyidfk;

    private float paymentamount;
}
