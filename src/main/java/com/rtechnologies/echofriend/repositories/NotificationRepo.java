package com.rtechnologies.echofriend.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rtechnologies.echofriend.entities.NotificationEntity;

@Repository
public interface NotificationRepo extends CrudRepository<NotificationEntity, Long>{
    Optional<NotificationEntity> findByUserid(Long userid);
}
