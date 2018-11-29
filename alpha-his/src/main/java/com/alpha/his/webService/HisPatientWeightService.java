package com.alpha.his.webService;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Created by HP on 2018/5/17.
 * his 系统获取 智慧医学 进行预问诊 患者的体重
 */

@WebService(targetNamespace = "http://webService.his.alpha.com")
public interface HisPatientWeightService {
    /**
     * his 开处方，或者其他检查获取 患者的体重自动填入
     * @param doctorName  医生名字
     * @param pno 挂号号码
     * @param mzhm 门诊号码
     * @return  返回xml 数据格式 节点
     */

    @WebMethod
    String patientWeight(@WebParam(name = "doctorName") String doctorName,
                         @WebParam(name = "pno") String pno, @WebParam(name = "mzhm") String mzhm);
}
