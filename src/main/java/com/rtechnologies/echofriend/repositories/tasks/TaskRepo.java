package com.rtechnologies.echofriend.repositories.tasks;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rtechnologies.echofriend.entities.task.TasksEntity;

@Repository
public interface TaskRepo extends CrudRepository<TasksEntity, Long>{
    
    @Query(value = "select * from tasks order by  pointsassigned desc LIMIT 6", nativeQuery = true)
    List<TasksEntity> findTopSixTasks();
}
