package com.alpha.commons.core.pojo;

import java.io.Serializable;

/**
 * Created by HP on 2018/3/22.
 * 国家表
 */
public class Nationality implements Serializable{

    private Integer id;
    private Integer code;
    private String name;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
