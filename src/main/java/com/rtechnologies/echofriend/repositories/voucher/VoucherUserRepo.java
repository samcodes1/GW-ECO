package com.rtechnologies.echofriend.repositories.voucher;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rtechnologies.echofriend.entities.voucher.VoucherProjection;
import com.rtechnologies.echofriend.entities.voucher.VoucherUserAssociation;

@Repository
public interface VoucherUserRepo extends CrudRepository<VoucherUserAssociation, Long> {
    @Query(value = "SELECT shopidfk, usedstatus, v.useridfk, voucherbarcode, voucherimage, voucherid, voucherimageurl, voucherpointscost, discountpercentage, voucheruserid from voucher v INNER join voucheruserbridge vu on vu.voucheridfk=v.voucherid where vu.useridfk=?1 and vu.isused=false", nativeQuery = true)
    List<VoucherProjection> findUseableVoucherById(Long id);

    VoucherUserAssociation findByUseridfkAndVoucheridfk(Long userid, Long voucherid);

    @Query(value = "select COUNT(voucheruserid) from voucheruserbridge where isused=false and useridfk=?1", nativeQuery = true)
    Integer countUnusedVoucher(Long id);
}
