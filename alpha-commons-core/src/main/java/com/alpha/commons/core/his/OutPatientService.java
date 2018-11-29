package com.alpha.commons.core.his;

import com.alpha.commons.core.pojo.HisRegisterYygh;
import com.alpha.commons.core.pojo.RegisterDTO;

import java.util.List;

/**
 * Created by HP on 2018/10/8.
 * 门诊部接口
 */
public interface OutPatientService {

    /**
     * 根据门诊号+就诊时间获取 挂号信息
     * @param outPatientNo
     * @param visitTime
     * @return
     */
    public List<RegisterDTO> registrationInfo(String outPatientNo, String visitTime);

    /**
     * 接口待定,返回参数 待定
     * 根据卡号，或者身份证照获取 用户门诊信息
     * @param cardNo
     */
    public Object patientInfo(String cardNo);

    /**
     * 根据 开始时间，结束时间获取 时间的挂号信息
     * @param startTime
     * @param endTime
     * @return
     */
    public List<HisRegisterYygh> hisRegisterYyghInfo(String startTime, String endTime);

    /**
     * 住院通知单信息
     * @param startTime
     * @param endTime
     * @return
     */
    public void NoticeData(String startTime, String endTime);

}
