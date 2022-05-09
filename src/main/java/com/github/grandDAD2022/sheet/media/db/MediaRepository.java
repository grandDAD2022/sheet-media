package com.github.grandDAD2022.sheet.media.db;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

@CacheConfig(cacheNames = "fotos")
public interface MediaRepository extends JpaRepository<Media, Long>{
	
	@CacheEvict(allEntries=true)
	Media save(Media media);
	
	@Cacheable
	List<Media> findAll();
}