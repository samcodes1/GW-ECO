package com.rtechnologies.echofriend.repositories.companypayment;

import java.util.List;

import com.rtechnologies.echofriend.models.companypayment.response.CompanyJoinPaymentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rtechnologies.echofriend.entities.companypayment.CompanyPaymentEntity;

@Repository
public interface CompanyPaymentRepo extends JpaRepository<CompanyPaymentEntity, Long> {
    @Query(value = "SELECT cp.companypaymentid, cp.companysubscription, cp.paymentdate, cp.transactionid, " +
    "cp.companyidfk, cp.paymentamount, c.companyid, c.companyname, c.subscriptiontype, " +
    "c.products, c.subscriptionexpiry " +
    "FROM companiespayment cp INNER JOIN companies c ON c.companyid = cp.companyidfk " +
    "WHERE c.companyid = :companyId", nativeQuery = true)
    List<CompanyJoinPaymentDto> findCompanyAndPaymentById(@Param("companyId") Long companyId);

    @Query(value = "SELECT cp.companypaymentid, cp.companysubscription, cp.paymentdate, cp.transactionid, " +
    "cp.companyidfk, cp.paymentamount, c.companyid, c.companyname, c.subscriptiontype, " +
    "c.products, c.subscriptionexpiry " +
    "FROM companiespayment cp INNER JOIN companies c ON c.companyid = cp.companyidfk ", nativeQuery = true)
    List<CompanyJoinPaymentDto> findCompanyAndPayment();
}
