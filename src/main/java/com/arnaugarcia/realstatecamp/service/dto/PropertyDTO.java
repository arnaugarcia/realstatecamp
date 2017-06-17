package com.arnaugarcia.realstatecamp.service.dto;

import com.arnaugarcia.realstatecamp.domain.Photo;
import com.arnaugarcia.realstatecamp.domain.enumeration.BuildingType;
import com.arnaugarcia.realstatecamp.domain.enumeration.ServiceType;

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
    private Boolean sold;
    private Integer m2;
    private ZonedDateTime created;
    private Photo photo;
    private Integer numberBedroom;
    private Integer numberWc;

    public  PropertyDTO(){

    }

    public PropertyDTO(Long id, String name, String province, String town, ZonedDateTime created) {
        this.id = id;
        this.name = name;
        this.province = province;
        this.town = town;
        this.created = created;
    }

    public PropertyDTO(String province, String town, Double price, BuildingType buildingType, ServiceType serviceType, Integer m2, Integer numberBedroom, Integer numberWc, ZonedDateTime created) {
        this.province = province;
        this.town = town;
        this.price = price;
        this.buildingType = buildingType;
        this.serviceType = serviceType;
        this.m2 = m2;
        this.numberBedroom = numberBedroom;
        this.numberWc = numberWc;
        this.created = created;
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

    public Integer getNumberBedroom() {
        return numberBedroom;
    }

    public void setNumberBedroom(Integer numberBedroom) {
        this.numberBedroom = numberBedroom;
    }

    public Integer getNumberWc() {
        return numberWc;
    }

    public void setNumberWc(Integer numberWc) {
        this.numberWc = numberWc;
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
            ", sold=" + sold +
            ", m2=" + m2 +
            ", created=" + created +
            ", photo=" + photo +
            '}';
    }
}
