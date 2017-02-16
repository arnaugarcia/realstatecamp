package com.arnaugarcia.assessoriatorrelles.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.arnaugarcia.assessoriatorrelles.domain.enumeration.RoadType;

/**
 * A Location.
 */
@Entity
@Table(name = "location")
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ref")
    private String ref;

    @NotNull
    @Column(name = "province", nullable = false)
    private String province;

    @NotNull
    @Column(name = "town", nullable = false)
    private String town;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_road", nullable = false)
    private RoadType typeOfRoad;

    @NotNull
    @Column(name = "name_road", nullable = false)
    private String nameRoad;

    @NotNull
    @Column(name = "number", nullable = false)
    private Integer number;

    @Column(name = "apartment")
    private Integer apartment;

    @Column(name = "building")
    private Integer building;

    @Column(name = "door")
    private Integer door;

    @Column(name = "stair")
    private String stair;

    @Column(name = "urlgmaps")
    private String urlgmaps;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @OneToOne(mappedBy = "location")
    @JsonIgnore
    private Company company;

    @OneToOne(mappedBy = "location")
    @JsonIgnore
    private Property property;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRef() {
        return ref;
    }

    public Location ref(String ref) {
        this.ref = ref;
        return this;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getProvince() {
        return province;
    }

    public Location province(String province) {
        this.province = province;
        return this;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getTown() {
        return town;
    }

    public Location town(String town) {
        this.town = town;
        return this;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public RoadType getTypeOfRoad() {
        return typeOfRoad;
    }

    public Location typeOfRoad(RoadType typeOfRoad) {
        this.typeOfRoad = typeOfRoad;
        return this;
    }

    public void setTypeOfRoad(RoadType typeOfRoad) {
        this.typeOfRoad = typeOfRoad;
    }

    public String getNameRoad() {
        return nameRoad;
    }

    public Location nameRoad(String nameRoad) {
        this.nameRoad = nameRoad;
        return this;
    }

    public void setNameRoad(String nameRoad) {
        this.nameRoad = nameRoad;
    }

    public Integer getNumber() {
        return number;
    }

    public Location number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getApartment() {
        return apartment;
    }

    public Location apartment(Integer apartment) {
        this.apartment = apartment;
        return this;
    }

    public void setApartment(Integer apartment) {
        this.apartment = apartment;
    }

    public Integer getBuilding() {
        return building;
    }

    public Location building(Integer building) {
        this.building = building;
        return this;
    }

    public void setBuilding(Integer building) {
        this.building = building;
    }

    public Integer getDoor() {
        return door;
    }

    public Location door(Integer door) {
        this.door = door;
        return this;
    }

    public void setDoor(Integer door) {
        this.door = door;
    }

    public String getStair() {
        return stair;
    }

    public Location stair(String stair) {
        this.stair = stair;
        return this;
    }

    public void setStair(String stair) {
        this.stair = stair;
    }

    public String getUrlgmaps() {
        return urlgmaps;
    }

    public Location urlgmaps(String urlgmaps) {
        this.urlgmaps = urlgmaps;
        return this;
    }

    public void setUrlgmaps(String urlgmaps) {
        this.urlgmaps = urlgmaps;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Location latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Location longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Company getCompany() {
        return company;
    }

    public Location company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Property getProperty() {
        return property;
    }

    public Location property(Property property) {
        this.property = property;
        return this;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Location location = (Location) o;
        if(location.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, location.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Location{" +
            "id=" + id +
            ", ref='" + ref + "'" +
            ", province='" + province + "'" +
            ", town='" + town + "'" +
            ", typeOfRoad='" + typeOfRoad + "'" +
            ", nameRoad='" + nameRoad + "'" +
            ", number='" + number + "'" +
            ", apartment='" + apartment + "'" +
            ", building='" + building + "'" +
            ", door='" + door + "'" +
            ", stair='" + stair + "'" +
            ", urlgmaps='" + urlgmaps + "'" +
            ", latitude='" + latitude + "'" +
            ", longitude='" + longitude + "'" +
            '}';
    }
}
