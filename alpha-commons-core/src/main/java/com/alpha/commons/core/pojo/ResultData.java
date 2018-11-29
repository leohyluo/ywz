package com.alpha.commons.core.pojo;


import com.alpha.commons.web.ResponseStatus;



/**
 * 返回数据基本结构
 * Created by Administrator on 2018/3/15.
 */

public class ResultData<T> {

    private BaseData result;
    private T dataSet;

    public ResultData(){
        this.result=new BaseData();
    }
    public ResultData(ResponseStatus status){
        this.result=new BaseData(status);
    }
    public ResultData(T data){
        this.result=new BaseData();
        this.dataSet=data;
    }
    public BaseData getResult() {
        return result;
    }

    public void setResult(BaseData result) {
        this.result = result;
    }

    public T getDataSet() {
        return dataSet;
    }

    public void setDataSet(T dataSet) {
        this.dataSet = dataSet;
    }
}
