package com.efrcs.echofriend.repositories.banner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efrcs.echofriend.entities.banner.BannerEntity;

@Repository
public interface BannerRepo extends JpaRepository<BannerEntity, Long> {
    
}
