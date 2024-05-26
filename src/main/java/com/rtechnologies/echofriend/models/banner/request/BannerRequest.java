package com.rtechnologies.echofriend.models.banner.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class BannerRequest {
    private String bannerName;
    private String bannerImage;
    private String bannerExpiry;
    private String companyBanner;
}
