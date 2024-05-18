package com.efrcs.echofriend.entities.companies;

import java.sql.Date;

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
@Entity( name = "companies" )
@Table( name = "companies" )
public class CompaniesEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long companyid;

    private String companyname;
    private String subscriptiontype;
    private String products;
    private Date subscriptionexpiry;
}
