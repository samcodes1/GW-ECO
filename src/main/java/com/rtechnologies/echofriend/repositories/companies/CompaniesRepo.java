package com.rtechnologies.echofriend.repositories.companies;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.rtechnologies.echofriend.entities.companies.CompaniesEntity;

@Repository
public interface CompaniesRepo extends JpaRepository<CompaniesEntity, Long> {
    
    @Query("SELECT a FROM companies a WHERE a.companyname = :companyName")
    public CompaniesEntity findCompanyIdByName(@Param("companyName") String companyName);  

    @Query("SELECT a FROM companies a INNER JOIN banner b ON a.companyid=b.companyidfk WHERE a.companyid = :companyId AND b.bannerid = :bannerId")
    public CompaniesEntity findCompanyIdByNameAndBannerId(@Param("companyId") Long companyId, Long bannerId);  
   
    @Query("select count(*) from companies")
    long count();

    Optional<CompaniesEntity> findByCompanyEmail(String usernameOrEmail);
}
