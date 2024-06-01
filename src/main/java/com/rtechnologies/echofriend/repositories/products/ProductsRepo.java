package com.rtechnologies.echofriend.repositories.products;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rtechnologies.echofriend.entities.products.ProductsEntity;
import com.rtechnologies.echofriend.models.products.response.ProductProjections;

@Repository
public interface ProductsRepo extends JpaRepository<ProductsEntity, Long> {
    @Query(value = "select * from products p inner join productcategory c on c.categoryid=p.categoryidfk inner join producttype pt on pt.producttypeid=p.producttypeidfk", nativeQuery = true)
    List<ProductProjections> findProducts();

    @Query(value = "select * from products p inner join productcategory c on c.categoryid=p.categoryidfk inner join producttype pt on pt.producttypeid=p.producttypeidfk where p.categoryidfk=?1", nativeQuery = true)
    List<ProductProjections> findProductsByCategory(Long id);



}
