package com.rtechnologies.echofriend.repositories.category;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rtechnologies.echofriend.entities.category.CategoryEntity;

@Repository
public interface CategoryRepo extends CrudRepository<CategoryEntity, Long> {
    
}
