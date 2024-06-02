package com.rtechnologies.echofriend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class VoucherController {


    @PostMapping("/createVoucher")
    public ResponseEntity<String> postMethodName(@RequestBody String entity) {
        //TODO: process POST request
        
        return null;
    }
    
}
