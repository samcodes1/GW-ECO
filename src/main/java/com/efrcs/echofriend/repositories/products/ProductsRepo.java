package com.efrcs.echofriend.repositories.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efrcs.echofriend.entities.products.ProductsEntity;

@Repository
public interface ProductsRepo extends JpaRepository<ProductsEntity, Long> {
    
}
