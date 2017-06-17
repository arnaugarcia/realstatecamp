package com.arnaugarcia.realstatecamp.web.rest;

import com.arnaugarcia.realstatecamp.service.MailService;
import com.arnaugarcia.realstatecamp.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.net.URISyntaxException;

/**
 * REST controller for managing Contact.
 */
@RestController
@RequestMapping("/api")
public class ContactResource {

    private final Logger log = LoggerFactory.getLogger(ContactResource.class);


    @Inject
    private MailService mailService;

    /**
     * POST  /contacts : Create a new contact.
     *
     * @return the ResponseEntity with status 201 (Created) and with body the new contact, or with status 400 (Bad Request) if the contact has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contact")
    @Timed
    public ResponseEntity<Void> createContact(@RequestParam("to") String to, @RequestParam("subject") String subject, @RequestParam("content") String content) throws URISyntaxException {

        try {
            mailService.sendEmail(to,subject,content,false,false);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("contact", "errorEnvioMail", e.getMessage())).body(null);
        }

    }

}
