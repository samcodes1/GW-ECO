package com.rtechnologies.echofriend.repositories.tasks;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rtechnologies.echofriend.entities.task.TaskAssigmentEntity;

@Repository
public interface TaskAssignmentRepo extends CrudRepository<TaskAssigmentEntity, Long>{
    
}
