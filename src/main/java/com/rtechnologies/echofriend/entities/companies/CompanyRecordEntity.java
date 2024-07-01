package com.rtechnologies.echofriend.entities.companies;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity( name = "companiesrecord" )
@Table( name = "companiesrecord" )
public class CompanyRecordEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long companyrecordid;

    private Long companyidfk;
    private String firstName;
    private String lastName;
    private String firstAddress;
    private String secondAddress;
    private String city;
    private String state;
    private String zipcode;
    private String country;
}
