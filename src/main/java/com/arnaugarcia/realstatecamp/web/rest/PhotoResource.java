package com.arnaugarcia.realstatecamp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arnaugarcia.realstatecamp.domain.Photo;

import com.arnaugarcia.realstatecamp.repository.PhotoRepository;
import com.arnaugarcia.realstatecamp.web.rest.util.HeaderUtil;
import com.arnaugarcia.realstatecamp.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Photo.
 */
@RestController
@RequestMapping("/api")
public class PhotoResource {

    private final Logger log = LoggerFactory.getLogger(PhotoResource.class);

    @Inject
    private PhotoRepository photoRepository;

    /**
     * POST  /photos : Create a new photo.
     *
     * @param photo the photo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new photo, or with status 400 (Bad Request) if the photo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/photos")
    @Timed
    public ResponseEntity<Photo> createPhoto(@Valid @RequestBody Photo photo) throws URISyntaxException {
        log.debug("REST request to save Photo : {}", photo);
        if (photo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("photo", "idexists", "A new photo cannot already have an ID")).body(null);
        }
        Photo result = photoRepository.save(photo);
        return ResponseEntity.created(new URI("/api/photos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("photo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /photos : Updates an existing photo.
     *
     * @param photo the photo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated photo,
     * or with status 400 (Bad Request) if the photo is not valid,
     * or with status 500 (Internal Server Error) if the photo couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/photos")
    @Timed
    public ResponseEntity<Photo> updatePhoto(@Valid @RequestBody Photo photo) throws URISyntaxException {
        log.debug("REST request to update Photo : {}", photo);
        if (photo.getId() == null) {
            return createPhoto(photo);
        }
        Photo result = photoRepository.save(photo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("photo", photo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /photos : get all the photos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of photos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/photos")
    @Timed
    public ResponseEntity<List<Photo>> getAllPhotos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Photos");
        Page<Photo> page = photoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/photos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /photos/:id : get the "id" photo.
     *
     * @param id the id of the photo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the photo, or with status 404 (Not Found)
     */
    @GetMapping("/photos/{id}")
    @Timed
    public ResponseEntity<Photo> getPhoto(@PathVariable Long id) {
        log.debug("REST request to get Photo : {}", id);
        Photo photo = photoRepository.findOne(id);
        return Optional.ofNullable(photo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /photos/:id : delete the "id" photo.
     *
     * @param id the id of the photo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/photos/{id}")
    @Timed
    public ResponseEntity<Void> deletePhoto(@PathVariable Long id) {
        log.debug("REST request to delete Photo : {}", id);
        photoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("photo", id.toString())).build();
    }

}
