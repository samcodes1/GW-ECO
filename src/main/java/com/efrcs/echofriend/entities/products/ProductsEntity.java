package com.efrcs.echofriend.entities.products;

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
@Entity( name = "products" )
@Table( name = "products" )
public class ProductsEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long productid;

    private String productname;
    private float productprice;
    private int productquantity;

    private Long companyidfk;
}
