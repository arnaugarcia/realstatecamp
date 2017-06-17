package com.arnaugarcia.realstatecamp.service.dto;

import com.arnaugarcia.realstatecamp.domain.enumeration.BuildingType;
import com.arnaugarcia.realstatecamp.domain.enumeration.ServiceType;

import java.util.List;

/**
 * Created by arnau on 12/5/17.
 */
public class FilterDTO {

    private List<String> townList;
    private List<String>  provinceList;
    private List<Integer> bedroomList;
    private List<Integer> bathroomList;
    private List<BuildingType> buildingTypeList;
    private List<ServiceType> serviceTypeList;
    private List<Double> minPriceList;
    private List<Double> maxPriceList;

    public FilterDTO(List<String> townList, List<String> provinceList) {
        this.townList = townList;
        this.provinceList = provinceList;
    }

    public FilterDTO(List<String> townList, List<String> provinceList, List<Integer> bedroomList, List<Integer> bathroomList, List<BuildingType> buildingTypeList, List<ServiceType> serviceTypeList, List<Double> minPriceList, List<Double> maxPriceList) {
        this.townList = townList;
        this.provinceList = provinceList;
        this.bedroomList = bedroomList;
        this.bathroomList = bathroomList;
        this.buildingTypeList = buildingTypeList;
        this.serviceTypeList = serviceTypeList;
        this.minPriceList = minPriceList;
        this.maxPriceList = maxPriceList;
    }

    public List<String> getTownList() {
        return townList;
    }

    public void setTownList(List<String> townList) {
        this.townList = townList;
    }

    public List<String> getProvinceList() {
        return provinceList;
    }

    public void setProvinceList(List<String> provinceList) {
        this.provinceList = provinceList;
    }

    public List<Integer> getBedroomList() {
        return bedroomList;
    }

    public void setBedroomList(List<Integer> bedroomList) {
        this.bedroomList = bedroomList;
    }

    public List<Integer> getBathroomList() {
        return bathroomList;
    }

    public void setBathroomList(List<Integer> bathroomList) {
        this.bathroomList = bathroomList;
    }

    public List<BuildingType> getBuildingTypeList() {
        return buildingTypeList;
    }

    public void setBuildingTypeList(List<BuildingType> buildingTypeList) {
        this.buildingTypeList = buildingTypeList;
    }

    public List<ServiceType> getServiceTypeList() {
        return serviceTypeList;
    }

    public void setServiceTypeList(List<ServiceType> serviceTypeList) {
        this.serviceTypeList = serviceTypeList;
    }

    public List<Double> getMinPriceList() {
        return minPriceList;
    }

    public void setMinPriceList(List<Double> minPriceList) {
        this.minPriceList = minPriceList;
    }

    public List<Double> getMaxPriceList() {
        return maxPriceList;
    }

    public void setMaxPriceList(List<Double> maxPriceList) {
        this.maxPriceList = maxPriceList;
    }
}
