package com.rtechnologies.echofriend.repositories.companies;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.rtechnologies.echofriend.entities.companies.CompaniesEntity;

@Repository
public interface CompaniesRepo extends JpaRepository<CompaniesEntity, Long> {
    
    @Query("SELECT a FROM companies a WHERE a.companyname = :companyName")
    public CompaniesEntity findCompanyIdByName(@Param("companyName") String companyName);  

    @Query("SELECT a FROM companies a INNER JOIN banner b ON a.companyid=b.companyidfk WHERE a.companyname = :companyName AND b.bannerid = :bannerId")
    public CompaniesEntity findCompanyIdByNameAndBannerId(@Param("companyName") String companyName, Long bannerId);  
}
