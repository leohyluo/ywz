package com.alpha.his.pojo.dto;

/**
 * Created by HP on 2018/3/21.
 */
public class EmrInfoUrlDTO {

    private ResultDTO result;
    private EmrInfoUrlDetailDTO dataset;

    public EmrInfoUrlDTO(ResultDTO result, EmrInfoUrlDetailDTO dataset) {
        this.result = result;
        this.dataset = dataset;
    }

    public ResultDTO getResult() {
        return result;
    }

    public void setResult(ResultDTO result) {
        this.result = result;
    }

    public EmrInfoUrlDetailDTO getDataset() {
        return dataset;
    }

    public void setDataset(EmrInfoUrlDetailDTO dataset) {
        this.dataset = dataset;
    }
}
