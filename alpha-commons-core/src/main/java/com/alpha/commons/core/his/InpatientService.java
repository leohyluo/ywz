package com.alpha.commons.core.his;

import java.util.List;

/**
 * Created by HP on 2018/10/8.
 * 住院部接口
 */
public interface InpatientService {
    /**
     * 根据住院号 查找患者住院记录
     * @param patientId
     * @return
     */
    public List<Object> InpatientRecord(String patientId);

    /**
     * 根据门诊号 查询患者住院的信息
     * @param outPatentNo
     * @return
     */
    public Object InpatientInfo(String outPatentNo);

}
