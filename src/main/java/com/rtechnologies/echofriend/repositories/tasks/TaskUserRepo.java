package com.rtechnologies.echofriend.repositories.tasks;

import java.sql.Timestamp;
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

    Optional<TaskUserAssociation> findByTaskidfkAndUseridfk(Long taskidfk, Long useridfk);

    @Query(value = "SELECT * FROM taskuserbridge WHERE taskidfk = ?1 AND useridfk = ?2 AND DATE_ADD(applieddatetime, INTERVAL 1 HOUR) >= ?3 order by taskuserid desc limit 1", nativeQuery = true)
    Optional<TaskUserAssociation> findByTaskidfkAndUseridfkDateTime(Long taskidfk, Long useridfk, Timestamp comparetime);

    @Query(value = "SELECT * FROM taskuserbridge WHERE taskidfk = ?1 AND useridfk = ?2 AND DATE_ADD(applieddatetime, INTERVAL ?4 HOUR) >= ?3 order by taskuserid desc limit 1", nativeQuery = true)
    Optional<TaskUserAssociation> findByTaskidfkAndUseridfkDateTimeDuration(Long taskidfk, Long useridfk, Timestamp comparetime, Integer duration);

    @Query(value = "SELECT * FROM taskuserbridge WHERE taskidfk = ?1 AND useridfk = ?2 AND DATE_ADD(applieddatetime, INTERVAL 168 HOUR) >= ?3 order by taskuserid desc limit 1", nativeQuery = true)
    Optional<TaskUserAssociation> findByTaskidfkAndUseridfkDateTimeWeek(Long taskidfk, Long useridfk, Timestamp comparetime);

    @Query(value = "select * from taskuserbridge tu inner join tasks t on t.taskid=tu.taskidfk where tu.useridfk=?1",
    nativeQuery = true)
    List<TaskUserProjection> findTaskByUserid(Long userid);

    @Query(value = "select * from taskuserbridge tu inner join tasks t on t.taskid=tu.taskidfk where t.taskid=?1",
    nativeQuery = true)
    List<TaskUserProjection> findTaskByTaskId(Long taskid);

    @Query(value = "select * from taskuserbridge tu inner join tasks t on t.taskid=tu.taskidfk where t.taskid=?1 and tu.useridfk=?2",
    nativeQuery = true)
    List<TaskUserProjection> findTaskByTaskIdUserid(Long taskid, Long userid);

    @Query(value = "select * from tasks t inner join taskuserbridge tu on t.taskid=tu.taskidfk where tu.taskuserid=?1 and tu.iscomplete=1",
    nativeQuery = true)
    TaskUserProjection taskstatus(Long taskuserid);
}
