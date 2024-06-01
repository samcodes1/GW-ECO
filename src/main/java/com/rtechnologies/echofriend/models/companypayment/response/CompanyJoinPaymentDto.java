package com.rtechnologies.echofriend.models.companypayment.response;

import java.sql.Date;
import java.sql.Timestamp;

public interface CompanyJoinPaymentDto {
    Long getCompanypaymentid();
    String getCompanysubscription();
    Timestamp getPaymentdate();
    String getTransactionid();
    Long getCompanyidfk();
    float getPaymentamount();
    Long getCompanyid();
    String getCompanyname();
    String getSubscriptiontype();
    String getProducts();
    Date getSubscriptionexpiry();

}
