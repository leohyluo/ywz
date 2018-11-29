package com.alpha.commons.core.pojo;

import java.io.Serializable;

/**
 * Created by HP on 2018/3/26.
 * 省实体
 */
public class Province implements Serializable{

    private Integer id;

    private String provinceId;

    private String province;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

}
