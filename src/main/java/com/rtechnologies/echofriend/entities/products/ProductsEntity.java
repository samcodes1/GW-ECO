package com.rtechnologies.echofriend.entities.products;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity( name = "products" )
@Table( name = "products" )
public class ProductsEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long productid;

    private String productname;
    private float productprice;
    private int productquantity;
    private String productimage;
    private Long companyidfk;
    private Long categoryidfk;
    private Long producttypeidfk;
}
