package com.efrcs.echofriend.entities.companypayment;

import java.sql.Timestamp;

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
