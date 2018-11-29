package com.alpha.commons.core.pojo;

import java.io.Serializable;

/**
 * 序号
 */
public class SysSequence implements Serializable {

    private static final long serialVersionUID = 8958027592169693968L;
    /**
     *
     */
    private Long id;

    /**
     * 序列名称
     */
    private String sequenceKey;
    /**
     * 当前值
     */
    private Long currentValue;

    /**
     * 增量
     */
    private Integer increment;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Long currentValue) {
        this.currentValue = currentValue;
    }

    public Integer getIncrement() {
        return increment;
    }

    public void setIncrement(Integer increment) {
        this.increment = increment;
    }
}
