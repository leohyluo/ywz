package com.alpha.commons.core.pojo;

import java.io.Serializable;

/**
 * Created by HP on 2018/3/26.
 */
public class City implements Serializable{

    private Integer id;
    private String cityId;
    private String city;
    private String provinceId;
    public Integer getId() {
        return id;
    }
    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getCityId() {
        return cityId;
    }
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
