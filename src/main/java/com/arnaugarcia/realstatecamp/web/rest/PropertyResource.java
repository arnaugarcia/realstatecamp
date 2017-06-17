package com.arnaugarcia.realstatecamp.web.rest;

import com.arnaugarcia.realstatecamp.domain.Notification;
import com.arnaugarcia.realstatecamp.domain.Photo;
import com.arnaugarcia.realstatecamp.domain.Property;
import com.arnaugarcia.realstatecamp.domain.enumeration.BuildingType;
import com.arnaugarcia.realstatecamp.domain.enumeration.ServiceType;
import com.arnaugarcia.realstatecamp.repository.*;
import com.arnaugarcia.realstatecamp.security.SecurityUtils;
import com.arnaugarcia.realstatecamp.service.dto.PropertyDTO;
import com.arnaugarcia.realstatecamp.service.util.ReferenceUtil;
import com.arnaugarcia.realstatecamp.web.rest.util.HeaderUtil;
import com.arnaugarcia.realstatecamp.web.rest.util.NotificationUtil;
import com.arnaugarcia.realstatecamp.web.rest.util.PaginationUtil;
import com.arnaugarcia.realstatecamp.web.rest.util.ValidateUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * REST controller for managing Property.
 */
@RestController
@Transactional
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
    private PhotoRepository photoRepository;

    @Inject
    private NotificationRepository notificationRepository;

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

        property.setRef(ReferenceUtil.generateReferenceProperty(property));

        property.setCreated(ZonedDateTime.now());

        String validateResult = ValidateUtil.validateProperty(property);
        if (!validateResult.equalsIgnoreCase("OK")){

            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert("property", "validationFails", validateResult))
                .body(null);

        }

        Property result = propertyRepository.save(property);

        if (result.getId() != null){
            Notification notificationSuccess = NotificationUtil.createSuccessNotification(property,userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());
            notificationRepository.save(notificationSuccess);
        }

        return ResponseEntity.created(new URI("/api/properties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("property", result.getId().toString()))
            .body(result);


    }

    /**
     * POST  /properties : Create a new property.
     *
     * @param photo the property to create and the id of property
     * @return the ResponseEntity with status 201 (Created) and with body the new property, or with status 400 (Bad Request) if the photo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/properties/{id}/photos")
    @Timed
    public ResponseEntity<Photo> createPropertyPhoto(@Valid @RequestBody Photo photo, @PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to save Property Photo : {}", photo);

        if (photo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("photo", "idexists", "A new photo cannot already have an ID")).body(null);
        }

        Property property = propertyRepository.findOne(id);

        if (property == null){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("property", "idnotexists", "Property not found")).body(null);
        }

        photo.setProperty(property);

        //TODO controlar el tamaño de la imagen para que no se suban imagenes tan grandes
        //photo.getImage().length

        Photo result =  photoRepository.save(photo);

        return ResponseEntity.created(new URI("/api/photos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("photo", result.getId().toString()))
            .body(result);
    }


    @PostMapping("/properties/{idProperty}/cover/{idPhoto}")
    @Timed
    public ResponseEntity<Photo> createCoverPropertyPhoto(@PathVariable Long idProperty,@PathVariable Long idPhoto) throws URISyntaxException {
        log.debug("REST request to save createCoverPropertyPhoto {}");

        Property property = propertyRepository.findOne(idProperty);
        for (Photo photo: property.getPhotos()){
            photo.setCover(false);
            photoRepository.save(photo);
        }
        Photo photo = photoRepository.findOne(idPhoto);
        photo.setCover(true);
        photoRepository.save(photo);

        return ResponseEntity.created(new URI("/api/photos/" + photo.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("photo", photo.getId().toString()))
            .body(photo);
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
    @Transactional
    public ResponseEntity<List<PropertyDTO>> getAllProperties(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PropertiesDTO");

        Page<PropertyDTO> page = propertyRepository.findPropertiesDto(pageable);

        List<PropertyDTO> propertyDTOList = page.getContent().parallelStream()

            .map( propertyDTO -> {

                //Set<Photo> photoSet = propertyRepository.findOne(propertyDTO.getId()).getPhotos();
                List<Photo> photoSet = photoRepository.findPhotosByPropertyIdAndAndCoverTrue(propertyDTO.getId());

                photoSet.stream().filter(photo -> photo.getCover()).findFirst().ifPresent(photo -> propertyDTO.setPhoto(photo));

                return propertyDTO;

            }).collect(Collectors.toList());

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/properties");

        return new ResponseEntity<>(propertyDTOList, headers, HttpStatus.OK);
    }

    /**
     * GET  /properties : get top 5 of properties
     *
     * @return the ResponseEntity with status 200 (OK) and the list of properties in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/properties/top5")
    @Timed
    @Transactional
    public ResponseEntity<List<PropertyDTO>> getNewProperties()
        throws URISyntaxException {
        log.debug("REST request to get a page of Properties");
        Pageable top5 = new PageRequest(0,5);
        List<PropertyDTO> propertyList = propertyRepository.findLast5(top5);
        /*List<PropertyDTO> result = propertyList
            .parallelStream()
            .map(property -> new PropertyDTO(property.getId(),property.getName(),property.getLocation().getTown(),property.getLocation().getProvince()))
            .collect(Collectors.toList());*/
        return new ResponseEntity<>(propertyList, HttpStatus.OK);
    }

    /**
     * GET  /properties : get top 5 of properties
     *
     * @return the ResponseEntity with status 200 (OK) and the list of properties in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/properties/{id}/photos")
    @Timed
    @Transactional
    public ResponseEntity<Set<Photo>> getPhotosFromProperty(@PathVariable Long id, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Properties");
        Property property = propertyRepository.findOne(id);
        if (property == null){

        }
        return new ResponseEntity<>(property.getPhotos(), HttpStatus.OK);
    }

    @DeleteMapping("/properties/{id}/photos")
    @Timed
    public ResponseEntity<Void> deletePhotosFromProperty(@PathVariable Long id)
        throws URISyntaxException {

        Photo photo = photoRepository.findOne(id);

        if (photo != null){
            photoRepository.delete(id);
            return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("photo", id.toString())).build();
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

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
    public ResponseEntity<List<PropertyDTO>> getPropertyByCriteria(
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
       // List<Property> resultTotal = propertyByCriteriaRepository.filteryPropertyByCriteria(params);
        //Temporary fix, implemented filtering w/ java 8. TODO look up why hibernate criteria is not working
        List<PropertyDTO> resultDTO = result.stream()
            .skip(pageable.getPageNumber()*pageable.getPageSize())
            .limit(pageable.getPageSize())
            .map(current -> {
            PropertyDTO propertyDTO = new PropertyDTO();
            propertyDTO.setId(current.getId());
            propertyDTO.setName(current.getName());
            propertyDTO.setPrice(current.getPrice());
            propertyDTO.setTown(current.getLocation().getTown());
            propertyDTO.setProvince(current.getLocation().getProvince());
            propertyDTO.setM2(current.getm2());
            propertyDTO.setServiceType(current.getServiceType());
            propertyDTO.setBuildingType(current.getBuildingType());
            propertyDTO.setNumberBedroom(current.getNumberBedroom());
            propertyDTO.setNumberWc(current.getNumberWc());
            current.getPhotos().parallelStream().filter(Photo::isCover).findFirst()
                .ifPresent(cover -> propertyDTO.setPhoto(cover));

            return propertyDTO;
        }).collect(Collectors.toList());



        if (resultDTO.isEmpty()) {
            return new ResponseEntity<>(

                null,HeaderUtil.createAlert("No match for the criteria entered!","property"),HttpStatus.NOT_FOUND);
        } else {
            Page<PropertyDTO> page = new PageImpl<PropertyDTO>(resultDTO,pageable,result.size());
            HttpHeaders httpHeaders = new HttpHeaders();

            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page,params, "/api/property/byfilters");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        }
    }

}
