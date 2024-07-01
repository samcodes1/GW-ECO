package com.rtechnologies.echofriend.repositories.companies;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.rtechnologies.echofriend.entities.companies.CompanyRecordEntity;
import com.rtechnologies.echofriend.entities.companypayment.CompaniesRecordProjection;
//CompanyRecordEntity
@Repository
public interface CompanyRecordRepo extends JpaRepository<CompanyRecordEntity, Long>  {

    @Query(value = "select cr.*,c.companyname from companiesrecord cr inner JOIN companies c on c.companyid=cr.companyidfk where cr.companyidfk=?1", nativeQuery = true)
    CompaniesRecordProjection getCompanyRecord(Long companyid);
}
