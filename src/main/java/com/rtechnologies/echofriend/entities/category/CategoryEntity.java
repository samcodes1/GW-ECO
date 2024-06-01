package com.rtechnologies.echofriend.entities.category;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity( name = "productcategory" )
@Table( name = "productcategory" )
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long categoryid;

    private String category;
}
