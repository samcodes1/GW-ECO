package com.rtechnologies.echofriend.repositories.sale;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rtechnologies.echofriend.entities.sales.SalesEntity;

@Repository
public interface SalesRepo extends CrudRepository<SalesEntity, Long> {
    
}
