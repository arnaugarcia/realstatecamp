package com.arnaugarcia.assessoriatorrelles.repository;

import com.arnaugarcia.assessoriatorrelles.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the Photo entity.
 */
@SuppressWarnings("unused")
public interface PhotoRepository extends JpaRepository<Photo,Long> {
    List<Photo> findPhotosByPropertyId(Long id);
}
