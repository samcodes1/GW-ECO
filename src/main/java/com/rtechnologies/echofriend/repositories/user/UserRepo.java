package com.rtechnologies.echofriend.repositories.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.rtechnologies.echofriend.entities.user.UserEntity;

@Repository
public interface UserRepo extends CrudRepository<UserEntity, Long> {
    public Boolean existsByEmail(String email);

    @Query("SELECT a FROM users a WHERE a.email = :email")
    public Optional<UserEntity> findAdminIdAndTypeByEmail(@Param("email") String email);   
}
