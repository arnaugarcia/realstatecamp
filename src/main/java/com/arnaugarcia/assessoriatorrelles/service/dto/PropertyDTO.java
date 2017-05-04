package com.arnaugarcia.assessoriatorrelles.service.dto;

import com.arnaugarcia.assessoriatorrelles.domain.Photo;
import com.arnaugarcia.assessoriatorrelles.domain.enumeration.BuildingType;
import com.arnaugarcia.assessoriatorrelles.domain.enumeration.ServiceType;

import java.time.ZonedDateTime;

/**
 * Created by arnau on 21/4/17.
 */
public class PropertyDTO {

    private Long id;
    private String name;
    private String province;
    private String town;
    private Double price;
    private BuildingType buildingType;
    private ServiceType serviceType;
    private String ref;
    private Boolean visible;
    private Boolean sold;
    private Integer m2;
    private ZonedDateTime created;
    private Photo photo;

    public PropertyDTO(Long id, String name, String province, String town, ZonedDateTime created) {
        this.id = id;
        this.name = name;
        this.province = province;
        this.town = town;
        this.created = created;
    }

    public PropertyDTO(Long id, String name, String province, String town, Double price, BuildingType buildingType, ServiceType serviceType, String ref, Boolean visible, Boolean sold, Integer m2, ZonedDateTime created, Photo photo) {
        this.id = id;
        this.name = name;
        this.province = province;
        this.town = town;
        this.price = price;
        this.buildingType = buildingType;
        this.serviceType = serviceType;
        this.ref = ref;
        this.visible = visible;
        this.sold = sold;
        this.m2 = m2;
        this.created = created;
        this.photo = photo;
    }

    public PropertyDTO(Long id, String name, String province, String town, Double price, BuildingType buildingType, ServiceType serviceType, String ref, Integer m2, ZonedDateTime created) {
        this.id = id;
        this.name = name;
        this.province = province;
        this.town = town;
        this.price = price;
        this.buildingType = buildingType;
        this.serviceType = serviceType;
        this.ref = ref;
        this.m2 = m2;
        this.created = created;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(BuildingType buildingType) {
        this.buildingType = buildingType;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Boolean getSold() {
        return sold;
    }

    public void setSold(Boolean sold) {
        this.sold = sold;
    }

    public Integer getM2() {
        return m2;
    }

    public void setM2(Integer m2) {
        this.m2 = m2;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "PropertyDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", province='" + province + '\'' +
            ", town='" + town + '\'' +
            ", price=" + price +
            ", buildingType=" + buildingType +
            ", serviceType=" + serviceType +
            ", ref='" + ref + '\'' +
            ", visible=" + visible +
            ", sold=" + sold +
            ", m2=" + m2 +
            ", created=" + created +
            ", photo=" + photo +
            '}';
    }
}
