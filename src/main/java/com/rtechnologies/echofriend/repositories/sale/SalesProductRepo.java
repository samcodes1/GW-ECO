package com.rtechnologies.echofriend.repositories.sale;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rtechnologies.echofriend.entities.sales.SaleProductEntity;

@Repository
public interface SalesProductRepo extends CrudRepository< SaleProductEntity, Long> {
    
}
