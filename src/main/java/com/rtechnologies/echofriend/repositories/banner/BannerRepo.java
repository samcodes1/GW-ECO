package com.rtechnologies.echofriend.repositories.banner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rtechnologies.echofriend.entities.banner.BannerEntity;

@Repository
public interface BannerRepo extends JpaRepository<BannerEntity, Long> {
    
}
