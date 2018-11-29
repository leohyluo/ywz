package com.alpha.server.rpc.his.pojo;

import com.alpha.commons.core.pojo.BasePojo;

public class SysConstant extends BasePojo<SysConstant>{
    private Long id;

    private Long code;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}