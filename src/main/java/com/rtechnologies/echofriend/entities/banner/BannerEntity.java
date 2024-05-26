package com.rtechnologies.echofriend.entities.banner;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity( name = "banner" )
@Table( name = "banner" )
public class BannerEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long bannerid;

    private String bannername;

    private String bannerimage;

    private Timestamp bannerexpiry;

    private Long companyidfk;
}
