package com.arnaugarcia.assessoriatorrelles.repository;

import com.arnaugarcia.assessoriatorrelles.domain.Property;
import com.arnaugarcia.assessoriatorrelles.service.dto.PropertyDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Spring Data JPA repository for the Property entity.
 */
@SuppressWarnings("unused")
public interface FilterRepository extends JpaRepository<Property,Long> {

    @Query("select new com.arnaugarcia.assessoriatorrelles.service.dto.PropertyDTO(property.location.province, property.location.town, property.price, property.buildingType, property.serviceType, property.m2, property.numberBedroom, property.numberWc, property.created) from Property property")
    List<PropertyDTO> findPropertiesFilter();

}
