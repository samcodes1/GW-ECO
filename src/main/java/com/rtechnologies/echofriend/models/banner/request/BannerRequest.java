package com.rtechnologies.echofriend.models.banner.request;

import org.springframework.web.multipart.MultipartFile;

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
    private MultipartFile bannerImage;
    private String bannerExpiry;
    private Long companyBanner;
}
