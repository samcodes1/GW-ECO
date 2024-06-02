package com.rtechnologies.echofriend.repositories.voucher;

import org.springframework.data.repository.CrudRepository;

import com.rtechnologies.echofriend.entities.voucher.VoucherEntity;

public interface VoucherRepo extends CrudRepository<VoucherEntity, Long> {
    
}
