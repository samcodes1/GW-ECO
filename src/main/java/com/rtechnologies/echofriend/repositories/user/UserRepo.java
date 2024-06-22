package com.rtechnologies.echofriend.repositories.user;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rtechnologies.echofriend.entities.task.TaskUserProjection;
import com.rtechnologies.echofriend.entities.user.UserEntity;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {
    public Boolean existsByEmail(String email);

    @Query("SELECT a FROM users a WHERE a.email = :email")
    public Optional<UserEntity> findAdminIdAndTypeByEmail(@Param("email") String email);

     Optional<UserEntity> findByEmail(String usernameOrEmail);

    @Query("SELECT SUM(u.points) FROM users u")
    Integer sumAllPoints();

    @Query(value = "SELECT *,  (SELECT COUNT(*) FROM taskuserbridge t WHERE t.useridfk = u.userid AND t.iscomplete = true) as taskcount FROM `users` u JOIN taskuserbridge tub ON u.userid = tub.useridfk WHERE joiningdate >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) limit 5",
    nativeQuery = true)
    List<TaskUserProjection> findusersAndTasksCompleted();

    @Query(value = "UPDATE users SET membershiptype =  CASE WHEN memebershipexpiry <= CURDATE() THEN 'FREE' ELSE membershiptype END",
    nativeQuery = true)
    void updateexpired();

    @Query(value = "SELECT u.*,COUNT(sp.saleidfk) as productsbought, SUM(sp.total) as totalmoneyspend FROM companies c inner join products p on p.companyidfk=c.companyid inner join saleproduct sp on sp.productidfk=p.productid inner join sales s on s.saleid=sp.saleidfk INNER join users u on u.userid=s.useridfk where c.companyid=?1 group by u.email, u.userid",
    nativeQuery = true)
    List<TaskUserProjection> findCustomerdata(Long companyid);
}
