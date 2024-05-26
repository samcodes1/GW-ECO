package com.rtechnologies.echofriend.repositories.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rtechnologies.echofriend.entities.products.ProductsEntity;

@Repository
public interface ProductsRepo extends JpaRepository<ProductsEntity, Long> {
    
}
