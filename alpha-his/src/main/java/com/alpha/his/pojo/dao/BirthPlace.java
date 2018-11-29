package com.alpha.his.pojo.dao;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2018/10/21.
 */
@Entity
@Table(name = "sys_birthplace")
public class BirthPlace {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }
    @Id
    private Integer id;
    private String name;
    private String code;
    private String symbol;
    private Integer times;

    public String getFirst(){
        return this.symbol.substring(0,1);
    }
}
