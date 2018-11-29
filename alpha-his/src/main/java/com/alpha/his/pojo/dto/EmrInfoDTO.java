package com.alpha.his.pojo.dto;

/**
 * 电子病历拉取数据传输类
 */
public class EmrInfoDTO {

    private ResultDTO result;

    private EmrInfoDetailDTO dataSet;

    public EmrInfoDTO(ResultDTO result, EmrInfoDetailDTO dataSet) {
        this.result = result;
        this.dataSet = dataSet;
    }

    public ResultDTO getResult() {
        return result;
    }

    public void setResult(ResultDTO result) {
        this.result = result;
    }

    public EmrInfoDetailDTO getDataSet() {
        return dataSet;
    }

    public void setDataSet(EmrInfoDetailDTO dataSet) {
        this.dataSet = dataSet;
    }


}
