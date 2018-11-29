package com.alpha.commons.core.pojo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by HP on 2018/4/28.
 */
@Entity
@Table(name = "sys_departmentconfig")
public class DepartMent {

    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;

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
}
