package com.alpha.commons.core.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by HP on 2018/7/11.
 */
@Entity
@Table(name = "sys_street")
public class Street {

    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "code")
    private Integer code;
    @Column(name = "name")
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
