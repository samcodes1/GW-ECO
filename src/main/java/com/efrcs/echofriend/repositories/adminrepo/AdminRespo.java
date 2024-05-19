package com.efrcs.echofriend.repositories.adminrepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efrcs.echofriend.entities.admin.AdminEntity;

@Repository
public interface AdminRespo extends JpaRepository <AdminEntity, Long>{

    public Boolean existsByEmail(String email);

    @Query("SELECT a FROM admins a WHERE a.email = :email")
    public AdminEntity findAdminIdAndTypeByEmail(@Param("email") String email);   
}
