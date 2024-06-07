package com.rtechnologies.echofriend.entities.voucher;

import java.sql.Timestamp;

import javax.mail.Multipart;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity( name = "voucher" )
@Table( name = "voucher" )
public class VoucherEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long voucherid;

    private String voucherbarcode;
    private Long shopidfk;
    private String usedstatus;
    private String voucherimageurl;
    private Integer voucherpointscost;
    private Float discountpercentage;
    private Timestamp vocuhercreatedat;
    private Timestamp voucherexpiry;

}
