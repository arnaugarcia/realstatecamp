package com.arnaugarcia.realstatecamp.web.rest;

import com.arnaugarcia.realstatecamp.domain.enumeration.BuildingType;
import com.arnaugarcia.realstatecamp.domain.enumeration.ServiceType;
import com.arnaugarcia.realstatecamp.repository.FilterRepository;
import com.arnaugarcia.realstatecamp.service.dto.FilterDTO;
import com.arnaugarcia.realstatecamp.service.dto.PropertyDTO;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by arnau on 13/5/17.
 */
/**
 * REST controller for managing Location.
 */
@RestController
@RequestMapping("/api")
public class FilterResource {

    private final Logger log = LoggerFactory.getLogger(LocationResource.class);

    @Inject
    FilterRepository filterRepository;

    @GetMapping("/filter/home")
    @Timed
    public ResponseEntity<FilterDTO> getFilterFiledsHome() throws URISyntaxException {
        log.debug("REST request to get a page of Towns");
        List<PropertyDTO> propertyDTOList = filterRepository.findPropertiesFilter();
        //townAndProvinceList.parallelStream().distinct().collect(Collectors.toList());

        //Source - https://coderanch.com/t/623127/java/array-specific-attribute-values-list
        List<String> provinceList = propertyDTOList
            .stream()
            .map(PropertyDTO::getTown)
            .filter(Objects::nonNull)
            .distinct()
            .collect(Collectors.toList());

        List<String> townList = propertyDTOList.stream().map(PropertyDTO::getTown).filter(Objects::nonNull).distinct().collect(Collectors.toList());

        List<Integer> bedroomList = propertyDTOList.stream().map(PropertyDTO::getNumberBedroom).filter(Objects::nonNull).distinct().collect(Collectors.toList());

        List<Integer> bathroomList = propertyDTOList.stream().map(PropertyDTO::getNumberWc).filter(Objects::nonNull).distinct().collect(Collectors.toList());

        List<BuildingType> buildingTypeList = propertyDTOList.stream().map(PropertyDTO::getBuildingType).filter(Objects::nonNull).distinct().collect(Collectors.toList());

        List<ServiceType> serviceTypeList = propertyDTOList.stream().map(PropertyDTO::getServiceType).filter(Objects::nonNull).distinct().collect(Collectors.toList());

        List<Double> minPriceList = propertyDTOList
            .parallelStream()
            .map(PropertyDTO::getPrice)
            .filter(Objects::nonNull)
            .distinct()
            .sorted()
            .collect(Collectors.toList());

        List<Double> maxPriceList = propertyDTOList
            .parallelStream()
            .map(PropertyDTO::getPrice)
            .filter(Objects::nonNull)
            .distinct()
            .sorted(Comparator.reverseOrder())
            .collect(Collectors.toList());

        //maxPriceList.forEach((k)-> System.out.println(round5(k)));

        FilterDTO filterDTO = new FilterDTO(townList,provinceList,bedroomList,bathroomList,buildingTypeList,serviceTypeList,minPriceList,maxPriceList);

        return new ResponseEntity<>(filterDTO, HttpStatus.OK);
    }
}
