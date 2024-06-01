package com.rtechnologies.echofriend.repositories.userpayment;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rtechnologies.echofriend.entities.userpayment.UserPaymentEntity;

@Repository
public interface UserPaymentRepo extends CrudRepository<UserPaymentEntity, Long>{
    
}
