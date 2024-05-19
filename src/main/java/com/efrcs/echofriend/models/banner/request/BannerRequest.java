package com.efrcs.echofriend.models.banner.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class BannerRequest {
    private String bannerName;
    private String bannerImage;
    private String bannerExpiry;
    private String companyBanner;
}
