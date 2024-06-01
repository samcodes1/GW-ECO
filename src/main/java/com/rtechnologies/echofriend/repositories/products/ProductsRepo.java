package com.rtechnologies.echofriend.repositories.products;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rtechnologies.echofriend.entities.products.ProductsEntity;

@Repository
public interface ProductsRepo extends JpaRepository<ProductsEntity, Long> {
    @Query(value = "select p.* from products p inner join productcategory c on c.categoryid=p.categoryidfk", nativeQuery = true)
    List<ProductsEntity> findProducts();

    @Query(value = "select p.* from products p inner join productcategory c on c.categoryid=p.categoryidfk where p.categoryidfk=?1", nativeQuery = true)
    List<ProductsEntity> findProductsByCategory(Long id);

}
