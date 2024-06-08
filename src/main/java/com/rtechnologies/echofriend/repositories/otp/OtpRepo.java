package com.rtechnologies.echofriend.repositories.otp;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.rtechnologies.echofriend.entities.otp.OtpEntity;

public interface OtpRepo extends CrudRepository<OtpEntity, Long>{
    @Query(value = "select * from otp where companyidfk=?1 and expired >= DATE_SUB(NOW(), INTERVAL 1 MINUTE) order by otpid desc LIMIT 1", nativeQuery = true)
    OtpEntity otpdata(Long companyid);
}
