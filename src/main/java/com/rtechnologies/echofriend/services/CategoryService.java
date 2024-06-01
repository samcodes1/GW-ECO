package com.rtechnologies.echofriend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rtechnologies.echofriend.appconsts.AppConstants;
import com.rtechnologies.echofriend.entities.category.CategoryEntity;
import com.rtechnologies.echofriend.models.category.response.CategoryResponse;
import com.rtechnologies.echofriend.repositories.category.CategoryRepo;

@Service
public class CategoryService {

    @Autowired
    CategoryRepo categoryRepoObj;

    public CategoryResponse createCategory(CategoryEntity categoryObj){
        categoryRepoObj.save(categoryObj);
        CategoryResponse response = new CategoryResponse();
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }

    public CategoryResponse getCategory(){
        // categoryRepoObj.findAll();
        CategoryResponse response = new CategoryResponse();
        response.setData(categoryRepoObj.findAll());
        response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
        return response;
    }
}
