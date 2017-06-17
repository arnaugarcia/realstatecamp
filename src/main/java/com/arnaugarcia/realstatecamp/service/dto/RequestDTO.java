package com.arnaugarcia.realstatecamp.service.dto;

import com.arnaugarcia.realstatecamp.domain.enumeration.Status;

import java.time.ZonedDateTime;

/**
 * Created by arnau on 1/5/17.
 */
public class RequestDTO {

    private Long id;
    private String name;
    private String phone;
    private String email;
    private Status status;
    private ZonedDateTime zonedDateTime;
    private String comment;
    private Long propertyId;
    private String propertyRef;

    public RequestDTO(Long id, String name, String phone, String email, Status status, ZonedDateTime zonedDateTime, String comment, Long propertyId, String propertyRef) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.status = status;
        this.zonedDateTime = zonedDateTime;
        this.comment = comment;
        this.propertyId = propertyId;
        this.propertyRef = propertyRef;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }

    public void setZonedDateTime(ZonedDateTime zonedDateTime) {
        this.zonedDateTime = zonedDateTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public String getPropertyRef() {
        return propertyRef;
    }

    public void setPropertyRef(String propertyRef) {
        this.propertyRef = propertyRef;
    }
}
