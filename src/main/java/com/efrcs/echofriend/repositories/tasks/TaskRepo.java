package com.efrcs.echofriend.repositories.tasks;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.efrcs.echofriend.entities.task.TasksEntity;

@Repository
public interface TaskRepo extends CrudRepository<TasksEntity, Long>{
    
}
