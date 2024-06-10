package com.rtechnologies.echofriend.repositories.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rtechnologies.echofriend.entities.task.TaskUserProjection;
import com.rtechnologies.echofriend.entities.user.UserPointsHistory;

@Repository
public interface UserHistoryRepo extends CrudRepository<UserPointsHistory, Long>{
    Optional<UserPointsHistory> findByUseridfk(Long userid);

    @Query(value = "select count(*) from taskuserbridge tb inner join users u on u.userid=tb.useridfk where tb.useridfk=?1 and tb.iscomplete=1",
    nativeQuery = true)
    Integer taskcompletecount(Long userid);

    @Query(value = "select count(*) from voucheruserbridge vb inner join users u on u.userid=vb.useridfk where vb.useridfk=?1 and vb.isused=1",
    nativeQuery = true)
    Integer voucherusercount(Long userid);

    @Query(value = "select tc.taskcategory, t.taskname, t.taskdescription, t.pointsassigned, t.taskid, tb.useridfk from tasks t inner join taskcategory tc on tc.taskcategoryid=t.taskcategoryfk inner join taskuserbridge tb on tb.taskidfk=t.taskid inner join users u on u.userid=tb.useridfk where tb.iscomplete=1 and tb.useridfk=?1 order by tb.taskuserid desc limit 6",
    nativeQuery = true)
    List<TaskUserProjection> recenttaskscompleted(Long userid);

}
