package com.arnaugarcia.assessoriatorrelles.repository;

import com.arnaugarcia.assessoriatorrelles.domain.Property;
import com.arnaugarcia.assessoriatorrelles.service.dto.PropertyDTO;
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

    @Query("select new com.arnaugarcia.assessoriatorrelles.service.dto.PropertyDTO (id, name, location.province, location.town, created) " +
            "from Property order by created desc")
    List<PropertyDTO> findLast5(Pageable pageable);

}
