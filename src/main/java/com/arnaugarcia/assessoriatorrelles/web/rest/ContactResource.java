package com.arnaugarcia.assessoriatorrelles.web.rest;

import com.arnaugarcia.assessoriatorrelles.service.MailService;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URISyntaxException;
import java.util.Properties;

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
    public ResponseEntity.BodyBuilder createContact(@RequestParam("to") String to, @RequestParam("subject") String subject, @RequestParam("content") String content) throws URISyntaxException {
        // Recipient's email ID needs to be mentioned.

        // Sender's email ID needs to be mentioned
        String from = "assessoriatorrellestest@gmail.com";//change accordingly

        final String username = "assessoriatorrellestest@gmail.com";//change accordingly
        final String password = "assessoriatorrellestest2017";//change accordingly

        // GMail's SMTP server
        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", host);

        // Get the Session object.
        Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setText(content);

            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully.... from GMAIL");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok();
    }

}
