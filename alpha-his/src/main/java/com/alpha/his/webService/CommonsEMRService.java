package com.alpha.his.webService;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Created by HP on 2018/9/30.
 */
@WebService(targetNamespace = "http://webService.his.alpha.com")
public interface CommonsEMRService {

    /**
     * 拉取电子病历  H5 页面
     * @param cardNo        门诊号
     * @param pno           挂号号码
     * @return
     */
    @WebMethod
    String getEmrInfoUrl(@WebParam(name = "cardNo")String cardNo, @WebParam(name = "pno")String pno, @WebParam(name = "doctorName")String doctorName, @WebParam(name = "sign") String sign);



    /**
     * 拉取电子病历   数据
     * @param cardNo        门诊号
     * @param pno           挂号号码
     * @return
     */
    @WebMethod
    String getEmrInfoData(@WebParam(name = "cardNo")String cardNo,@WebParam(name = "pno")String pno,@WebParam(name = "importName")String importName,@WebParam(name = "doctorName")String doctorName, @WebParam(name = "sign")String sign);


    public void recordImport(String cardNo, String pno, String importName, String doctorName);
    public String recordShowUrl(String cardNo, String pno,String doctorName,String sign);
}
