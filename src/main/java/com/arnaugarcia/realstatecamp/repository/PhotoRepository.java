package com.arnaugarcia.realstatecamp.repository;

import com.arnaugarcia.realstatecamp.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the Photo entity.
 */
@SuppressWarnings("unused")
public interface PhotoRepository extends JpaRepository<Photo,Long> {
    List<Photo> findPhotosByPropertyId(Long id);

    List<Photo> findPhotosByPropertyIdAndAndCoverTrue(Long id);
}
