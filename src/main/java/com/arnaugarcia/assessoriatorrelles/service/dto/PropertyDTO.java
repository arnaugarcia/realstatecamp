package com.arnaugarcia.assessoriatorrelles.service.dto;

import java.time.ZonedDateTime;

/**
 * Created by arnau on 21/4/17.
 */
public class PropertyDTO {

    private Long id;
    private String name;
    private String province;
    private String town;

    public PropertyDTO(Long id, String name, String province, String town, ZonedDateTime created) {
        this.id = id;
        this.name = name;
        this.province = province;
        this.town = town;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProvince() {
        return province;
    }

    public String getTown() {
        return town;
    }

    @Override
    public String toString() {
        return "PropertyDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", province='" + province + '\'' +
            ", town='" + town + '\'' +
            '}';
    }
}
