package com.arnaugarcia.assessoriatorrelles.repository;

import com.arnaugarcia.assessoriatorrelles.domain.Property;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Spring Data JPA repository for the Property entity.
 */
@SuppressWarnings("unused")
public interface PropertyRepository extends JpaRepository<Property,Long> {

    @Query("select property from Property property where property.user.login = ?#{principal.username}")
    List<Property> findByUserIsCurrentUser();

    List<Property> findTop5PropertiesByOrderByCreatedDesc();

    @Query("select id, name, location.province, location.town, created from Property order by created desc")
    List<Property> findTop5(Pageable pageable);

}
