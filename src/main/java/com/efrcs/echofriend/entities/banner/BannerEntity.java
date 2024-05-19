package com.efrcs.echofriend.entities.banner;

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
@Entity( name = "banner" )
@Table( name = "banner" )
public class BannerEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long bannerid;

    private String bannername;

    private String bannerimage;

    private Timestamp bannerexpiry;

    private Long companyidfk;
}
