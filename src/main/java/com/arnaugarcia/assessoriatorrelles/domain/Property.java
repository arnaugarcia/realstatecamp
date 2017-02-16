package com.arnaugarcia.assessoriatorrelles.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.arnaugarcia.assessoriatorrelles.domain.enumeration.BuildingType;

import com.arnaugarcia.assessoriatorrelles.domain.enumeration.ServiceType;

/**
 * A Property.
 */
@Entity
@Table(name = "property")
public class Property implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price")
    private Double price;

    @Lob
    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "building_type")
    private BuildingType buildingType;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_type")
    private ServiceType serviceType;

    @NotNull
    @Column(name = "ref", nullable = false)
    private String ref;

    @Column(name = "visible")
    private Boolean visible;

    @Column(name = "sold")
    private Boolean sold;

    @Column(name = "terrace")
    private Boolean terrace;

    @Column(name = "m_2")
    private Integer m2;

    @Column(name = "number_bedroom")
    private Integer numberBedroom;

    @Column(name = "elevator")
    private Boolean elevator;

    @Column(name = "furnished")
    private Boolean furnished;

    @Column(name = "pool")
    private Boolean pool;

    @Column(name = "garage")
    private Boolean garage;

    @Column(name = "number_wc")
    private Integer numberWc;

    @Column(name = "ac")
    private Boolean ac;

    @OneToOne
    @JoinColumn(unique = true)
    private Location location;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "property")
    @JsonIgnore
    private Set<Photo> photos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Property name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public Property price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public Property description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public Property buildingType(BuildingType buildingType) {
        this.buildingType = buildingType;
        return this;
    }

    public void setBuildingType(BuildingType buildingType) {
        this.buildingType = buildingType;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public Property serviceType(ServiceType serviceType) {
        this.serviceType = serviceType;
        return this;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public String getRef() {
        return ref;
    }

    public Property ref(String ref) {
        this.ref = ref;
        return this;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public Boolean isVisible() {
        return visible;
    }

    public Property visible(Boolean visible) {
        this.visible = visible;
        return this;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Boolean isSold() {
        return sold;
    }

    public Property sold(Boolean sold) {
        this.sold = sold;
        return this;
    }

    public void setSold(Boolean sold) {
        this.sold = sold;
    }

    public Boolean isTerrace() {
        return terrace;
    }

    public Property terrace(Boolean terrace) {
        this.terrace = terrace;
        return this;
    }

    public void setTerrace(Boolean terrace) {
        this.terrace = terrace;
    }

    public Integer getm2() {
        return m2;
    }

    public Property m2(Integer m2) {
        this.m2 = m2;
        return this;
    }

    public void setm2(Integer m2) {
        this.m2 = m2;
    }

    public Integer getNumberBedroom() {
        return numberBedroom;
    }

    public Property numberBedroom(Integer numberBedroom) {
        this.numberBedroom = numberBedroom;
        return this;
    }

    public void setNumberBedroom(Integer numberBedroom) {
        this.numberBedroom = numberBedroom;
    }

    public Boolean isElevator() {
        return elevator;
    }

    public Property elevator(Boolean elevator) {
        this.elevator = elevator;
        return this;
    }

    public void setElevator(Boolean elevator) {
        this.elevator = elevator;
    }

    public Boolean isFurnished() {
        return furnished;
    }

    public Property furnished(Boolean furnished) {
        this.furnished = furnished;
        return this;
    }

    public void setFurnished(Boolean furnished) {
        this.furnished = furnished;
    }

    public Boolean isPool() {
        return pool;
    }

    public Property pool(Boolean pool) {
        this.pool = pool;
        return this;
    }

    public void setPool(Boolean pool) {
        this.pool = pool;
    }

    public Boolean isGarage() {
        return garage;
    }

    public Property garage(Boolean garage) {
        this.garage = garage;
        return this;
    }

    public void setGarage(Boolean garage) {
        this.garage = garage;
    }

    public Integer getNumberWc() {
        return numberWc;
    }

    public Property numberWc(Integer numberWc) {
        this.numberWc = numberWc;
        return this;
    }

    public void setNumberWc(Integer numberWc) {
        this.numberWc = numberWc;
    }

    public Boolean isAc() {
        return ac;
    }

    public Property ac(Boolean ac) {
        this.ac = ac;
        return this;
    }

    public void setAc(Boolean ac) {
        this.ac = ac;
    }

    public Location getLocation() {
        return location;
    }

    public Property location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public User getUser() {
        return user;
    }

    public Property user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public Property photos(Set<Photo> photos) {
        this.photos = photos;
        return this;
    }

    public Property addPhoto(Photo photo) {
        photos.add(photo);
        photo.setProperty(this);
        return this;
    }

    public Property removePhoto(Photo photo) {
        photos.remove(photo);
        photo.setProperty(null);
        return this;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Property property = (Property) o;
        if(property.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, property.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Property{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", price='" + price + "'" +
            ", description='" + description + "'" +
            ", buildingType='" + buildingType + "'" +
            ", serviceType='" + serviceType + "'" +
            ", ref='" + ref + "'" +
            ", visible='" + visible + "'" +
            ", sold='" + sold + "'" +
            ", terrace='" + terrace + "'" +
            ", m2='" + m2 + "'" +
            ", numberBedroom='" + numberBedroom + "'" +
            ", elevator='" + elevator + "'" +
            ", furnished='" + furnished + "'" +
            ", pool='" + pool + "'" +
            ", garage='" + garage + "'" +
            ", numberWc='" + numberWc + "'" +
            ", ac='" + ac + "'" +
            '}';
    }
}
