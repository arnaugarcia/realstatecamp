package com.arnaugarcia.assessoriatorrelles.web.rest;

import com.arnaugarcia.assessoriatorrelles.domain.Property;
import com.arnaugarcia.assessoriatorrelles.domain.enumeration.BuildingType;
import com.arnaugarcia.assessoriatorrelles.domain.enumeration.ServiceType;
import com.arnaugarcia.assessoriatorrelles.repository.LocationRepository;
import com.arnaugarcia.assessoriatorrelles.repository.PropertyByCriteriaRepository;
import com.arnaugarcia.assessoriatorrelles.repository.PropertyRepository;
import com.arnaugarcia.assessoriatorrelles.repository.UserRepository;
import com.arnaugarcia.assessoriatorrelles.security.SecurityUtils;
import com.arnaugarcia.assessoriatorrelles.web.rest.util.HeaderUtil;
import com.arnaugarcia.assessoriatorrelles.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing Property.
 */
@RestController
@RequestMapping("/api")
public class PropertyResource {

    private final Logger log = LoggerFactory.getLogger(PropertyResource.class);

    @Inject
    private PropertyRepository propertyRepository;

    @Inject
    private PropertyByCriteriaRepository propertyByCriteriaRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private LocationRepository locationRepository;

    /**
     * POST  /properties : Create a new property.
     *
     * @param property the property to create
     * @return the ResponseEntity with status 201 (Created) and with body the new property, or with status 400 (Bad Request) if the property has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/properties")
    @Timed
    public ResponseEntity<Property> createProperty(@Valid @RequestBody Property property) throws URISyntaxException {
        log.debug("REST request to save Property : {}", property);
        if (property.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("property", "idexists", "A new property cannot already have an ID")).body(null);
        }

        property.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());

        property.setRef("REF-" + property.getLocation().getProvince().toLowerCase().charAt(0) + property.getLocation().getTown().toLowerCase().charAt(0) + property.getLocation().getRef().substring(4,7));

        Property result = propertyRepository.save(property);
        return ResponseEntity.created(new URI("/api/properties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("property", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /properties : Updates an existing property.
     *
     * @param property the property to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated property,
     * or with status 400 (Bad Request) if the property is not valid,
     * or with status 500 (Internal Server Error) if the property couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/properties")
    @Timed
    public ResponseEntity<Property> updateProperty(@Valid @RequestBody Property property) throws URISyntaxException {
        log.debug("REST request to update Property : {}", property);
        if (property.getId() == null) {
            return createProperty(property);
        }
        Property result = propertyRepository.save(property);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("property", property.getId().toString()))
            .body(result);
    }

    /**
     * GET  /properties : get all the properties.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of properties in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/properties")
    @Timed
    public ResponseEntity<List<Property>> getAllProperties(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Properties");
        Page<Property> page = propertyRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/properties");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /properties/:id : get the "id" property.
     *
     * @param id the id of the property to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the property, or with status 404 (Not Found)
     */
    @GetMapping("/properties/{id}")
    @Timed
    public ResponseEntity<Property> getProperty(@PathVariable Long id) {
        log.debug("REST request to get Property : {}", id);
        Property property = propertyRepository.findOne(id);
        return Optional.ofNullable(property)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /properties/:id : delete the "id" property.
     *
     * @param id the id of the property to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/properties/{id}")
    @Timed
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        log.debug("REST request to delete Property : {}", id);
        propertyRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("property", id.toString())).build();
    }

    @RequestMapping(value = "/property/byfilters",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    public ResponseEntity<List<Property>> getPropertyByCriteria(
        Pageable pageable,
        @RequestParam(value = "location", required = false) String location,
        @RequestParam(value = "minPrice", required = false) String minPrice,
        @RequestParam(value = "maxPrice", required = false) String maxPrice,
        @RequestParam(value = "minSize", required = false) String minSize,
        @RequestParam(value = "maxSize", required = false) String maxSize,
        @RequestParam(value = "terrace", required = false) Boolean terrace,
        @RequestParam(value = "elevator", required = false) Boolean elevator,
        @RequestParam(value = "furnished", required = false) Boolean furnished,
        @RequestParam(value = "pool", required = false) Boolean pool,
        @RequestParam(value = "garage", required = false) Boolean garage,
        @RequestParam(value = "ac", required = false) Boolean ac,
        @RequestParam(value = "numberWc", required = false) String numberWc,
        @RequestParam(value = "numberBedroom", required = false) String numberBedroom,
        @RequestParam(value = "buildingType", required = false) BuildingType buildingType,
        @RequestParam(value = "serviceType", required = false) ServiceType serviceType
        ) throws URISyntaxException {
        Map<String, Object> params = new HashMap<>();

        //params.put("locality", locality);
            /*
             */

        if (location != null) {
            params.put("location", location);
        }
        //******Add enum params*****
        if (buildingType != null) {
            params.put("buildingType", buildingType);
        }

        if (serviceType != null) {
            params.put("serviceType", serviceType);
        }

        if (minPrice != null) {

            try {
                Double minPriceDouble = Double.parseDouble(minPrice);
                params.put("minPrice", minPriceDouble);
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("property",
                    "number format exception on param",
                    "A numeric param cannot have non numeric characters")).body(null);
            }

        }

        if (maxPrice != null) {

            try {
                Double maxPriceDouble = Double.parseDouble(maxPrice);
                params.put("maxPrice", maxPriceDouble);
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("property",
                    "number format exception on param",
                    "A numeric param cannot have non numeric characters")).body(null);
            }

        }

        if (minSize != null) {

            try {
                Integer minSizeInt = Integer.parseInt(minSize);
                params.put("minSize", minSizeInt);
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("property",
                    "number format exception on param",
                    "A numeric param cannot have non numeric characters")).body(null);
            }

        }

        if (maxSize != null) {

            try {
                Integer maxSizeInt = Integer.parseInt(maxSize);
                params.put("maxSize", maxSizeInt);
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("property",
                    "number format exception on param",
                    "A numeric param cannot have non numeric characters")).body(null);
            }

        }

        if (terrace != null) {
            params.put("terrace", terrace);
        }

        if (elevator != null) {
            params.put("elevator", elevator);
        }

        if (furnished != null) {
            params.put("furnished", furnished);
        }
        if (pool != null) {
            params.put("pool", pool);
        }
        if (garage != null) {
            params.put("garage", garage);
        }
        if (ac != null) {
            params.put("ac", ac);
        }

        if (numberWc != null) {

            try {
                Integer numberWcInt = Integer.parseInt(numberWc);
                params.put("numberWc", numberWcInt);
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("property",
                    "number format exception on param",
                    "A numeric param cannot have non numeric characters")).body(null);
            }

        }

        if (numberBedroom != null) {

            try {
                Integer numberBedroomInt = Integer.parseInt(numberBedroom);
                params.put("numberBedroom", numberBedroomInt);
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("property",
                    "number format exception on param",
                    "A numeric param cannot have non numeric characters")).body(null);
            }

        }

        List<Property> result = propertyByCriteriaRepository.filteryPropertyByCriteria(params,pageable);
        if (result.isEmpty()) {
            return new ResponseEntity<>(

                null,HeaderUtil.createAlert("No match for the criteria entered!","property"),HttpStatus.NOT_FOUND);
        } else {
            Page<Property> page = new PageImpl<Property>(result,pageable,propertyRepository.findAll().size());
            HttpHeaders httpHeaders = new HttpHeaders();
            //httpHeaders.add("X-Total-Count",String.valueOf(result.size()));
//            return new ResponseEntity<>(
//                result,
//                httpHeaders,
//                HttpStatus.OK
//
//            );

            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/property/byfilters");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        }
    }

}
