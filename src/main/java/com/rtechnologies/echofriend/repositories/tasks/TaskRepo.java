package com.rtechnologies.echofriend.repositories.tasks;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.rtechnologies.echofriend.entities.task.TasksEntity;
import com.rtechnologies.echofriend.models.tasks.response.TaskCategortProjections;

@Repository
public interface TaskRepo extends CrudRepository<TasksEntity, Long>{
    
    @Query(value = "select *, (select count(*) from users inner join taskuserbridge tb on tb.useridfk=users.userid where t.taskid=tb.taskidfk) as participants from tasks t INNER join taskcategory tc on t.taskcategoryfk=tc.taskcategoryid order by t.pointsassigned desc limit 6", nativeQuery = true)
    List<TaskCategortProjections> findTopSixTasks();

    @Query(value = "select * from tasks t INNER join taskcategory tc on t.taskcategoryfk=tc.taskcategoryid", nativeQuery = true)
    List<TaskCategortProjections> findtaskcategory();

    @Query(value = "select * from tasks t INNER join taskcategory tc on t.taskcategoryfk=tc.taskcategoryid where taskid=?1", nativeQuery = true)
    List<TaskCategortProjections> findtaskcategory(Long id);



    @Query(value = "select * from tasks order by taskid desc", nativeQuery = true)
    List<TasksEntity> findAllRecentTask();

    @Query(value = "select * from tasks order by taskid desc limit ?1", nativeQuery = true)
    List<TasksEntity> findAllRecentTaskLimit(Integer limit);

    @Query(value = "select * from tasks where taskcreatedby=?1 order by taskid desc", nativeQuery = true)
    List<TasksEntity> findAllRecentTaskCreatedBy(Long id);

    @Query(value = "select * from tasks where taskcreatedby=?1 order by taskid desc limit ?2", nativeQuery = true)
    List<TasksEntity> findAllRecentTaskCreatedByLimit(Long id, Integer limit);


}
