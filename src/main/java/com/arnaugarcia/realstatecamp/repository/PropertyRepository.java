package com.arnaugarcia.realstatecamp.repository;

import com.arnaugarcia.realstatecamp.domain.Property;
import com.arnaugarcia.realstatecamp.service.dto.PropertyDTO;
import org.springframework.data.domain.Page;
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

    @Query("select new com.arnaugarcia.realstatecamp.service.dto.PropertyDTO (id, name, location.province, location.town, created) " +
            "from Property order by created desc")
    List<PropertyDTO> findLast5(Pageable pageable);

   @Query("select new com.arnaugarcia.realstatecamp.service.dto.PropertyDTO" +
       " (property.id, property.name, property.location.province, property.location.town, property.price, property.buildingType, " +
       "property.serviceType, property.ref, property.m2, property.created) from Property property")
   //@Query("select property.name from Property property inner join com.arnaugarcia.realstatecamp.domain.Photo photo on property.id = photo.property.id")
   Page<PropertyDTO> findPropertiesDto(Pageable pageable);

}
