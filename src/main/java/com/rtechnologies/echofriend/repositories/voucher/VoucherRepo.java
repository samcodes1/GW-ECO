package com.rtechnologies.echofriend.repositories.voucher;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.rtechnologies.echofriend.entities.voucher.VoucherEntity;

public interface VoucherRepo extends CrudRepository<VoucherEntity, Long> {
    @Query(value = "select * from voucher where voucherid not in (select voucheridfk from voucheruserbridge where useridfk=?1)", nativeQuery = true)
    List<VoucherEntity> findAllVoucherNotRedeemedByUserYet(Long userid);
}
