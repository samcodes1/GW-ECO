package com.rtechnologies.echofriend.repositories.tasks;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rtechnologies.echofriend.entities.task.TaskUserAssociation;
import com.rtechnologies.echofriend.entities.task.TaskUserProjection;

@Repository
public interface TaskUserRepo extends CrudRepository<TaskUserAssociation, Long> {
    Optional<TaskUserAssociation> findByTaskidfk(Long taskidfk);

    @Query(value = "select * from taskuserbridge tu inner join tasks t on t.taskid=tu.taskidfk where tu.useridfk=?1 and tu.iscomplete=?2",
    nativeQuery = true)
    List<TaskUserProjection> findTaskByUseridCompleteStatus(Long userid, Boolean status);

    Integer countByIscompleteTrue();

    @Query(value = "select count(taskuserid) from taskuserbridge where iscomplete=true and useridfk=?1", nativeQuery = true)
    Integer countByIscompleteTrueWhereUserid(Long userid);
}
