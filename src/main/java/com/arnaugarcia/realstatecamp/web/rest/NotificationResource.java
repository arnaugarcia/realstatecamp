package com.arnaugarcia.realstatecamp.web.rest;

import com.arnaugarcia.realstatecamp.domain.Notification;
import com.arnaugarcia.realstatecamp.repository.NotificationRepository;
import com.arnaugarcia.realstatecamp.web.rest.util.HeaderUtil;
import com.arnaugarcia.realstatecamp.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Notification.
 */
@RestController
@RequestMapping("/api")
public class NotificationResource {

    private final Logger log = LoggerFactory.getLogger(NotificationResource.class);

    @Inject
    private NotificationRepository notificationRepository;

    /**
     * POST  /notifications : Create a new notification.
     *
     * @param notification the notification to create
     * @return the ResponseEntity with status 201 (Created) and with body the new notification, or with status 400 (Bad Request) if the notification has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/notifications")
    @Timed
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) throws URISyntaxException {
        log.debug("REST request to save Notification : {}", notification);
        if (notification.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("notification", "idexists", "A new notification cannot already have an ID")).body(null);
        }
        Notification result = notificationRepository.save(notification);
        return ResponseEntity.created(new URI("/api/notifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("notification", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /notifications : Updates an existing notification.
     *
     * @param notification the notification to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated notification,
     * or with status 400 (Bad Request) if the notification is not valid,
     * or with status 500 (Internal Server Error) if the notification couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/notifications")
    @Timed
    public ResponseEntity<Notification> updateNotification(@RequestBody Notification notification) throws URISyntaxException {
        log.debug("REST request to update Notification : {}", notification);
        if (notification.getId() == null) {
            return createNotification(notification);
        }
        Notification result = notificationRepository.save(notification);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("notification", notification.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /notifications : Updates an existing notification.
     *
     * @param notificationList the notification to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated notification,
     * or with status 400 (Bad Request) if the notification is not valid,
     * or with status 500 (Internal Server Error) if the notification couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/notifications/multiple")
    @Timed
    public ResponseEntity<List<Notification>> updateMultipleNotification(@RequestBody List<Notification> notificationList) throws URISyntaxException {
        log.debug("REST request to update multiple Notification : {}", notificationList.size());
        if (notificationList.isEmpty()) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("notification", "emptylist", "The list is empty")).body(null);
        }else{
            List<Notification> result = notificationRepository.save(notificationList);
            //TODO: Make validation better
            return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("notification", result.toString())).body(result);
        }
    }

    /**
     * GET  /notifications : get all the notifications.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of notifications in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/notifications")
    @Timed
    public ResponseEntity<List<Notification>> getAllNotifications(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Notifications");
        Page<Notification> page = notificationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/notifications");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /notifications/:id : get the "id" notification.
     *
     * @param id the id of the notification to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the notification, or with status 404 (Not Found)
     */
    @GetMapping("/notifications/{id}")
    @Timed
    public ResponseEntity<Notification> getNotification(@PathVariable Long id) {
        log.debug("REST request to get Notification : {}", id);
        Notification notification = notificationRepository.findOne(id);
        return Optional.ofNullable(notification)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /notifications/byUser/:id : get the "id" notification.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the notification, or with status 404 (Not Found)
     */
    @GetMapping("/notifications/byUser")
    @Timed
    public ResponseEntity<List<Notification>> getAllNotificationsByUser(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Notifications by User");
        Page<Notification> page = notificationRepository.findByUserIsCurrentUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/notifications");
        if (!page.getContent().isEmpty()){
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.NO_CONTENT);
        }

    }

    /**
     * GET  /notifications/byUser/:id : get the "id" notification.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the notification, or with status 404 (Not Found)
     */
    @GetMapping("/notifications/byUser/active")
    @Timed
    public ResponseEntity<List<Notification>> getActiveNotificationsByUser(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Notifications active by user");
        Page<Notification> page = notificationRepository.findByUserAndSeenIsFalse(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/notifications");
        if (!page.getContent().isEmpty()){
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(page.getContent(),headers, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * DELETE  /notifications/:id : delete the "id" notification.
     *
     * @param id the id of the notification to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/notifications/{id}")
    @Timed
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        log.debug("REST request to delete Notification : {}", id);
        notificationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("notification", id.toString())).build();
    }

    /**
     * DELETE  /notifications/
     *
     * @param ids of notifications to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/notifications/multiple")
    // URL: [6-2-4]
    @Timed
    @Transactional
    public ResponseEntity<Void> deleteMultipleNotification(@RequestParam Long cacheBuster, @RequestParam String ids) {
        log.debug("REST request to delete Notification : {}", ids + cacheBuster);
        List<Long> longList = null;
        try{
            //TODO : Check if notification exists
           longList = Arrays.stream(ids.split("-"))
                .map(s -> Long.parseLong(s))
                .collect(Collectors.toList());
        }catch(Exception e){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("notification", "badids: " + ids, "The ids aren't correct " + ids)).body(null);
        }

        notificationRepository.deleteByIdIn(longList);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("notification", ids)).build();
    }

}
