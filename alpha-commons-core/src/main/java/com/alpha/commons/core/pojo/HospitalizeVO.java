package com.alpha.commons.core.pojo;


import com.alpha.commons.util.XStreamUtils;

/**
 * Created by HP on 2018/3/30.
 */
public class HospitalizeVO {

    private BaseData result;
    private HospitalizedAo data;

    public HospitalizeVO(){
        this.result=new BaseData();
    }
    public BaseData getResult() {
        return result;
    }

    public void setResult(BaseData result) {
        this.result = result;
    }

    public HospitalizedAo getData() {
        return data;
    }

    public void setData(HospitalizedAo data) {
        this.data = data;
    }

}
