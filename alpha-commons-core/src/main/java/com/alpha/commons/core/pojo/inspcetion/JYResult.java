package com.alpha.commons.core.pojo.inspcetion;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by edz on 2018/10/27.
 */
@Entity
@Table(name = "jy_result")
public class JYResult {
    @Id
    private Integer id;
    private String reportId;
    private String itemId;
    private String itemName;
    private String itemResult;
    private String resultAbnormalSign;
    private String resultReferenceHigh;
    private String resultReferenceLow;
    private String resultReference;
    private String unit;
    private String measureDate;
    private String recordDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemResult() {
        return itemResult;
    }

    public void setItemResult(String itemResult) {
        this.itemResult = itemResult;
    }

    public String getResultAbnormalSign() {
        return resultAbnormalSign;
    }

    public void setResultAbnormalSign(String resultAbnormalSign) {
        this.resultAbnormalSign = resultAbnormalSign;
    }

    public String getResultReferenceHigh() {
        return resultReferenceHigh;
    }

    public void setResultReferenceHigh(String resultReferenceHigh) {
        this.resultReferenceHigh = resultReferenceHigh;
    }

    public String getResultReferenceLow() {
        return resultReferenceLow;
    }

    public void setResultReferenceLow(String resultReferenceLow) {
        this.resultReferenceLow = resultReferenceLow;
    }

    public String getResultReference() {
        return resultReference;
    }

    public void setResultReference(String resultReference) {
        this.resultReference = resultReference;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMeasureDate() {
        return measureDate;
    }

    public void setMeasureDate(String measureDate) {
        this.measureDate = measureDate;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }
}
