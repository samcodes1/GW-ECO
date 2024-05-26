package com.rtechnologies.echofriend.controller;

import com.rtechnologies.echofriend.models.products.request.ProductsRequest;
import com.rtechnologies.echofriend.models.products.response.ProductsResponse;
import com.rtechnologies.echofriend.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rtechnologies.echofriend.appconsts.AppConstants;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;



@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ProductController {
    @Autowired
    ProductsService productsServiceObj;

    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "sign up success."),
            @ApiResponse(code = 400, message = "invalid request data."),
            @ApiResponse(code = 404, message = "record does not exists."),
            // @ApiResponse(code = 403, message = "operation not allowed."),
            @ApiResponse(code = 500, message = "internal server error."),
        }
    )
    @PostMapping("/addProduct")
    public ResponseEntity<ProductsResponse> addProduct(@RequestBody ProductsRequest productsRequestObj) {
        ProductsResponse response = productsServiceObj.addProductServiceMethod(productsRequestObj);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }

    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "sign up success."),
            @ApiResponse(code = 400, message = "invalid request data."),
            @ApiResponse(code = 404, message = "record does not exists."),
            @ApiResponse(code = 500, message = "internal server error."),
        }
    )
    @DeleteMapping("/deleteProduct/{productId}")
    public ResponseEntity<ProductsResponse> deleteProduct(@PathVariable Long productId){
        ProductsResponse response = productsServiceObj.deleteProduct(productId);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }

    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "sign up success."),
            @ApiResponse(code = 404, message = "record does not exists."),
            @ApiResponse(code = 500, message = "internal server error."),
        }
    )
    @PutMapping("/updateProduct/{productId}")
    public ResponseEntity<ProductsResponse> updateProduct(@PathVariable Long productId, @RequestBody ProductsRequest productsRequestObj) {
        ProductsResponse response = productsServiceObj.updateProduct(productId, productsRequestObj);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }
    
    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "sign up success."),
            @ApiResponse(code = 404, message = "record does not exists."),
            @ApiResponse(code = 500, message = "internal server error."),
        }
    )
    @GetMapping("/getProducts")
    public ResponseEntity<ProductsResponse> getProducts(@RequestParam(required = false) Long productId) {
        ProductsResponse response = productsServiceObj.getProducts(productId);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE)?
        200:500).body(response);
    }
    
}
