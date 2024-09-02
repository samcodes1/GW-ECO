package com.rtechnologies.echofriend.entities.companies;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private Date joindate;
    private String role;
    @Column(unique = true) // This ensures that the companyEmail field is unique
    private String companyEmail;
    private String companylogo;
    private Long companycategoryfk;
    private String location;
    private String password;
}
