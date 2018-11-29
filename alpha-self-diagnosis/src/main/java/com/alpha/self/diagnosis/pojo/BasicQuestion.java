package com.alpha.self.diagnosis.pojo;

import com.alpha.commons.enums.BasicQuestionType;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.commons.util.DateUtils;
import com.alpha.commons.util.StringUtils;
import com.alpha.self.diagnosis.pojo.vo.AnswerRequestVo;
import com.alpha.server.rpc.user.pojo.UserInfo;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class BasicQuestion implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 问题内容
     */
    private String title;

    /**
     * 问题编码
     */
    private String questionCode;

    /**
     * 问题类型
     */
    private String displayType;

    /**
     * 问题序号
     */
    private Integer defaultOrder;

    /**
     * 所属入口类型
     */
    private Integer inType;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 最小年龄
     */
    private Integer minAge;

    /**
     * 最大年龄
     */
    private Integer maxAge;

    /**
     * 是否多选
     */
    private Boolean isMulti;

    /**
     * 范围标识
     */
    private String rangeScope;

    private static final long serialVersionUID = 1L;

    public BasicQuestion() {
    }

    public BasicQuestion(Integer defaultOrder, String questionCode, Integer gender, Integer minAge, Integer maxAge) {
        this.defaultOrder = defaultOrder;
        this.questionCode = questionCode;
        this.gender = gender;
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode == null ? null : questionCode.trim();
    }


    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    public Integer getDefaultOrder() {
        return defaultOrder;
    }

    public void setDefaultOrder(Integer defaultOrder) {
        this.defaultOrder = defaultOrder;
    }

    public Integer getInType() {
        return inType;
    }

    public void setInType(Integer inType) {
        this.inType = inType;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public Boolean getIsMulti() {
        return isMulti;
    }

    public void setIsMulti(Boolean isMulti) {
        this.isMulti = isMulti;
    }


    public String getRangeScope() {
        return rangeScope;
    }

    public void setRangeScope(String rangeScope) {
        this.rangeScope = rangeScope;
    }

}