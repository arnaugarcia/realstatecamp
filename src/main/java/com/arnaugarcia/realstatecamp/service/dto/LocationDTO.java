package com.arnaugarcia.realstatecamp.service.dto;

/**
 * Created by arnau on 12/5/17.
 */
public class LocationDTO {

    private Long id;
    private String ref;
    private String province;
    private String town;
    private Double latitude;
    private Double longitude;

    public LocationDTO(Long id, String ref, String province, String town, Double latitude, Double longitude) {
        this.id = id;
        this.ref = ref;
        this.province = province;
        this.town = town;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public LocationDTO(String province, String town) {
        this.province = province;
        this.town = town;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }


    @Override
    public String toString() {
        return "LocationDTO{" +
            "id=" + id +
            ", ref='" + ref + '\'' +
            ", province='" + province + '\'' +
            ", town='" + town + '\'' +
            ", latitude=" + latitude +
            ", longitude=" + longitude +
            '}';
    }
}
