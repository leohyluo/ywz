package com.alpha.commons.core.his;

/**
 * Created by edz on 2018/10/23.
 * 检验检查和病例
 */
public interface InspectionService {
    //病例
    void getEMR(String startTime,String endTime);
    //检验检查
    void getInspcetion(String startTime,String endTime);
}
