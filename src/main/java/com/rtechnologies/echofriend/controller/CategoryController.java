package com.rtechnologies.echofriend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.rtechnologies.echofriend.appconsts.AppConstants;
import com.rtechnologies.echofriend.entities.category.CategoryEntity;
import com.rtechnologies.echofriend.models.category.response.CategoryResponse;
import com.rtechnologies.echofriend.services.CategoryService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class CategoryController {
    @Autowired
    CategoryService categoryServiceObj;


    @PostMapping("/createCategory")
    public ResponseEntity<CategoryResponse> postMethodName(@RequestBody CategoryEntity categoryObj) {
        //TODO: process POST request
        CategoryResponse response = categoryServiceObj.createCategory(categoryObj);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }

    
    
}
