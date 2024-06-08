package com.rtechnologies.echofriend.repositories.voucher;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.rtechnologies.echofriend.entities.voucher.VoucherEntity;
import com.rtechnologies.echofriend.entities.voucher.VoucherProjection;

public interface VoucherRepo extends CrudRepository<VoucherEntity, Long> {
    @Query(value = "select * from voucher where voucherid not in (select voucheridfk from voucheruserbridge where useridfk=?1)", nativeQuery = true)
    List<VoucherEntity> findAllVoucherNotRedeemedByUserYet(Long userid);

    @Query(value = "SELECT count(*) FROM voucher inner join voucheruserbridge vu on vu.voucheridfk=voucher.voucherid WHERE DATE(vocuhercreatedat) = CURDATE()", nativeQuery = true)
    Integer countVoucherRedeemedToday();

    @Query(value = "SELECT count(*) FROM voucher inner join voucheruserbridge vu on vu.voucheridfk=voucher.voucherid WHERE DATE(vocuhercreatedat) = CURDATE() and shopidfk=?1", nativeQuery = true)
    Integer countVoucherRedeemedToday(Long companyid);

    @Query(value = "SELECT count(*) FROM voucher inner join voucheruserbridge vu on vu.voucheridfk=voucher.voucherid", nativeQuery = true)
    Integer countredeemed();

    @Query(value = "SELECT count(*) FROM voucher inner join voucheruserbridge vu on vu.voucheridfk=voucher.voucherid and shopidfk=?1", nativeQuery = true)
    Integer countredeemed(Long companyid);

    @Query(value = "SELECT count(*) FROM voucher", nativeQuery = true)
    Integer voucherCreated();

    @Query(value = "SELECT count(*) FROM voucher where shopidfk=?1", nativeQuery = true)
    Integer voucherCreated(Long companyid);

    @Query(value = "SELECT count(*) FROM voucher WHERE voucherexpiry < CURDATE()", nativeQuery = true)
    Integer countexpiredVouchers();

    @Query(value = "SELECT count(*) FROM voucher WHERE voucherexpiry < CURDATE() where shopidfk=?1", nativeQuery = true)
    Integer countexpiredVouchers(Long companyid);

    @Query(value = "SELECT v.shopidfk,v.usedstatus,v.useridfk,v.voucherbarcode,v.voucherid,v.voucherpointscost,v.discountpercentage,v.voucherexpiry,v.vocuhercreatedat,v.description,v.name,c.companyid,c.companyname,c.subscriptionexpiry,c.subscriptiontype,c.company_email from voucher v inner join companies c on c.companyid=v.shopidfk where c.companyid=?1", nativeQuery = true)
    List<VoucherProjection> getCompanyVouchers(Long companyid);    
}
