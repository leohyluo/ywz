package com.alpha.his.webService;


import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Created by HP on 2018/6/15.
 * his 调用患者在智慧医学填的表格
 */
@WebService(targetNamespace = "http://webService.his.alpha.com")
public interface HisPatientInfoService {
    /**
     * his 根据
      * @param outPatientNo
     * @param patientName
     * @return
     */
    @WebMethod
    String hisPatientInfo(@WebParam(name = "outPatientNo") String outPatientNo, @WebParam(name = "patientName")String
            patientName);
}
