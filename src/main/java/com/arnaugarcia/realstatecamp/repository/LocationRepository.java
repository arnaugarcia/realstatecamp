package com.arnaugarcia.realstatecamp.repository;

import com.arnaugarcia.realstatecamp.domain.Location;
import com.arnaugarcia.realstatecamp.service.dto.LocationDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Spring Data JPA repository for the Location entity.
 */
@SuppressWarnings("unused")
public interface LocationRepository extends JpaRepository<Location,Long> {
    @Query("select new com.arnaugarcia.realstatecamp.service.dto.LocationDTO(location.province,location.town) from Location location")
    List<LocationDTO> getAllTownAndProvince();
}
