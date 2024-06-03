package com.rtechnologies.echofriend.entities.sales;

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
@Entity( name = "saleproduct" )
@Table( name = "saleproduct" )
public class SaleProductEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long saleproductid;
    private Long saleidfk;
    private Long productidfk;
    private Integer quantity;
    private Float total;
    private Long voucheridfk;
}
