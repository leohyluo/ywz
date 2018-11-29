package com.alpha.his.webService;



import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Created by Administrator on 2018/3/14.
 */

@WebService(targetNamespace = "http://webService.his.alpha.com")
public interface UserCardService {

    /**
     * his 系统扫码取卡 获取患者信息
     * @return
     */

    @WebMethod
    String patientInfo(@WebParam(name = "param") String param);


    /**
     * 拉取电子病历  H5 页面
     * @param cardNo        门诊号
     * @param pno           挂号号码
     * @return
     */
    @WebMethod
    String getEmrInfoUrl(@WebParam(name = "cardNo")String cardNo,@WebParam(name = "pno")String pno, @WebParam(name = "sign") String sign);

    /**
     * 获取url 连接地址
     * @param cardNo 门诊号
     * @param pno 挂号号码
     * @param doctorName 医生名字
     * @param sign
     * @return
     */
    @WebMethod
    String getEmrInfoUrlNew(@WebParam(name = "cardNo")String cardNo,@WebParam(name = "pno")String pno,@WebParam(name = "doctorName")String doctorName, @WebParam(name = "sign") String sign);


    /**
     * 拉取电子病历   数据
     * @param cardNo        门诊号
     * @param pno           挂号号码
     * @return
     */
    @WebMethod
    String getEmrInfoData(@WebParam(name = "cardNo")String cardNo,@WebParam(name = "pno")String pno, @WebParam(name = "sign")String sign);

    /**
     *
     * @param cardNo 门诊号
     * @param pno   挂号号码
     * @param importName  导入模块字符串名称  格式："xx,bb,yy"
     * @param doctorName  就诊医生名字
     * @param sign  签名
     * @return
     */
    @WebMethod
    String getEmrInfoDataNew(@WebParam(name = "cardNo")String cardNo,@WebParam(name = "pno")String pno, @WebParam(name = "importName")String importName,@WebParam(name = "doctorName")String doctorName,@WebParam(name = "sign")String sign);




}
