package com.alpha.his.pojo.dao;



import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2018/10/21.
 */
@Table(name = "sys_nationality")
@Entity
public class Country {
    @Id
    private Integer id;
    private String name;
    private String code;
    private String symbol;
    private Integer times;

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

    public String getFirst(){
        return this.symbol.substring(0,1);
    }
}
