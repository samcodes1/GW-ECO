package com.rtechnologies.echofriend.repositories.user;

import java.util.Optional;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.rtechnologies.echofriend.entities.user.UserEntity;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {
    public Boolean existsByEmail(String email);

    @Query("SELECT a FROM users a WHERE a.email = :email")
    public Optional<UserEntity> findAdminIdAndTypeByEmail(@Param("email") String email);

     Optional<UserEntity> findByEmail(String usernameOrEmail);
}
