package com.rtechnologies.echofriend.repositories.tasks;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rtechnologies.echofriend.entities.task.TaskCategoryEntity;

@Repository
public interface TaskCategoryRepo extends CrudRepository<TaskCategoryEntity, Long> {
}

