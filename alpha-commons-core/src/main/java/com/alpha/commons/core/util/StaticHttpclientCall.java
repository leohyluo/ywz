package com.alpha.commons.core.util;

import com.alpha.commons.util.DateUtils;
import com.alpha.commons.util.StringUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class StaticHttpclientCall {

    private static Logger logger = LoggerFactory.getLogger(StaticHttpclientCall.class);

    /**
     * post 请求 WS
     * @throws IOException
     * @throws HttpException
     */
    public static String servicetest(String wsdl, String input ) throws HttpException, IOException {
        String CDATA="<![CDATA["+input+"]]>";
        String soapRequestData = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:esb=\"http://esb.ewell.cc\">"
                + "<soapenv:Header/>"
                + " <soapenv:Body>"
                + "<esb:HIPMessageServer>"
                + "<esb:input>"
                + CDATA
                + "</esb:input>"
                + " </esb:HIPMessageServer>"
                + " </soapenv:Body>"
                + "</soapenv:Envelope>";
        PostMethod postMethod = new PostMethod(wsdl);
        byte[] b = soapRequestData.getBytes("utf-8");
        InputStream is = new ByteArrayInputStream(b, 0, b.length);
        RequestEntity re = new InputStreamRequestEntity(is, b.length,
                "application/soap+xml; charset=utf-8");
        postMethod.setRequestEntity(re);
        HttpClient httpClient = new HttpClient();
        int statusCode = httpClient.executeMethod(postMethod);
        if(statusCode == 200) {
            String soapResponseData = postMethod.getResponseBodyAsString();
            soapResponseData=soapResponseData.replace("&lt;","<");
            soapResponseData=soapResponseData.replace("&gt;",">");
            return soapResponseData;
        } else {
        	return null;
        }
    }

    /**
     * 获取挂号信息
     * @param wdsl
     * @param fid
     * @param outPatientNo   门诊号
     * @param visitTime   就诊时间
     * @return
     */
    public static String registrationInfo(String wdsl,String fid,String outPatientNo, String visitTime){
        String result=null;
        try {
            String tomorrow= DateUtils.addOneDay(visitTime);
            StringBuffer stringBuffer=new StringBuffer();
            stringBuffer.append(getFid(fid));
            stringBuffer.append(" and  outPatientNo ='");
            stringBuffer.append(outPatientNo+"'");
            stringBuffer.append(" and visitTime between to_date('"+visitTime);
            stringBuffer.append("','yyyy-mm-dd') and to_date('").append(tomorrow).append("','yyyy-mm-dd')");
            stringBuffer.append(" </Msg></MsgInfo></ESBEntry>");
            String input=stringBuffer.toString();
            logger.info("请求挂号信息参数是：{}",input);
            result = servicetest(wdsl,input);
            logger.info("请求挂号结果是：{}",result);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("请求挂号信息异常："+e.toString());
        }

        return result;
    }
    /**
     *门诊信息
     * @param wsdl   WS地址
     * @param fid
     * @param mzhm    门诊号码
     * @return   返回soap xml
     */

    public static String outPatientParam(String wsdl,String fid,String mzhm){
        String result= null;
        try {
            StringBuffer stringBuffer=new StringBuffer();
            stringBuffer.append(getFid(fid));
            stringBuffer.append(" and MZHM ='");
            stringBuffer.append(mzhm);
            stringBuffer.append("'</Msg></MsgInfo></ESBEntry>");
            String input=stringBuffer.toString();
           logger.info("请求门诊信息参数是：{}",input);
            result = servicetest(wsdl,input);
            logger.info("请求门诊结果是：{}",result);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("请求门诊信息异常："+e.toString());
        }
        return result;
    }

    /**
     * 住院信息
     * @param wsdl
     * @param fid
     * @param mzhm  门诊号码
     * @return  xml
     */

    public static String hospitalizedInfo(String wsdl,String fid,String mzhm){
        String result=null;
        try {
            StringBuffer stringBuffer=new StringBuffer();
            stringBuffer.append(getFid(fid));
            stringBuffer.append(" and  MZHM ='");
            stringBuffer.append(mzhm+"'");
            stringBuffer.append(" order by VISIT_ID desc");  //多条获取第一条就是最新的
            stringBuffer.append("</Msg></MsgInfo></ESBEntry>");
            String input=stringBuffer.toString();
            logger.info("请求住院信息--》条件是：{}",input);
            result = servicetest(wsdl,input);
            logger.info("请求住院信息--》结果是：{}",result);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("请求住院信息异常："+e.toString());
        }
        return result;
    }


    /**
     * 根据住院号获取
     * @param wsdl
     * @param fid
     * @param inpno  门诊号码
     * @return  xml
     */

    public static String hospitalizedInfobyinpno(String wsdl,String fid,String inpno){
        String result=null;
        try {
            StringBuffer stringBuffer=new StringBuffer();
            stringBuffer.append(getFid(fid));
            stringBuffer.append(" and  INP_NO ='");
            stringBuffer.append(inpno+"'");
            stringBuffer.append(" order by VISIT_ID desc");  //多条获取第一条就是最新的
            stringBuffer.append("</Msg></MsgInfo></ESBEntry>");
            String input=stringBuffer.toString();
            logger.info("请求住院信息--》条件是：{}",input);
            result = servicetest(wsdl,input);
            logger.info("请求住院信息--》结果是：{}",result);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("请求住院信息异常："+e.toString());
        }
        return result;
    }

    /**
     * 住院通知单信息
     * @param mzhm  门诊号码
     * @return  xml
     */

    public static String hospitalizedNotice(String wsdl,String fid,String mzhm){
        String result=null;
        try {
            StringBuffer stringBuffer=new StringBuffer();
            stringBuffer.append(getFid(fid));
            stringBuffer.append(" and  MZHM ='");
            stringBuffer.append(mzhm+"'");
            stringBuffer.append(" order by NOTIFYTIME desc "); //无法远程分页，获取第一条就是最新的
            stringBuffer.append("</Msg></MsgInfo></ESBEntry>");
            String input=stringBuffer.toString();
            logger.info("请求住院通知单信息--》条件是：{}",input);
            result = servicetest(wsdl,input);
            logger.info("请求住院通知单信息--》结果是：{}",result);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("请求通知单信息异常："+e.toString());
        }
        return result;
    }
    /**
     * 住院通知单信息
     * @param  startTime
     * @return  xml
     */

    public static String hospitalizedNoticeAll(String wsdl,String fid,String startTime,String endTime){
        String result=null;
        try {
            StringBuffer stringBuffer=new StringBuffer();
            stringBuffer.append(getFid(fid));
            stringBuffer.append(" and NOTIFYTIME between to_date('"+startTime);
            stringBuffer.append("','yyyy-mm-dd hh24:mi:ss') and to_date('").append(endTime).append("'," +
                    "'yyyy-mm-dd hh24:mi:ss')");
            stringBuffer.append(" order by NOTIFYTIME desc "); //无法远程分页，获取第一条就是最新的
            stringBuffer.append("</Msg></MsgInfo></ESBEntry>");
            String input=stringBuffer.toString();
            logger.info("请求住院通知单信息--》条件是：{}",input);
            result = servicetest(wsdl,input);
            logger.info("请求住院通知单信息--》结果是：{}",result);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("请求通知单信息异常："+e.toString());
        }
        return result;
    }
    /**
     * 医院全天实时挂号数
     * @param wdsl
     * @param fid
     * @param outPatientNo
     * @param visitTime
     * @return
     */
    public static String allNo(String wdsl,String fid,String outPatientNo, String visitTime){
        String result=null;
        try {
            String tomorrow= DateUtils.addOneDay(visitTime);
            StringBuffer stringBuffer=new StringBuffer();
            stringBuffer.append(getFid(fid));
            stringBuffer.append(" and visitTime between '2018-06-01 13:00:00' and '2018-06-01 23:59:59'");
            stringBuffer.append(" </Msg></MsgInfo></ESBEntry>");
            String input=stringBuffer.toString();
            logger.info("请求挂号信息参数是：{}",input);
            result = servicetest(wdsl,input);
            logger.info("请求挂号结果是：{}",result);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("请求挂号信息异常："+e.toString());
        }

        return result;
    }

    /**
     * 医院所有挂号预约信息
     * @param wdsl
     * @param fid
     * @param starttime
     * @param endtime
     * @return
     */
    public static String all(String wdsl,String fid,String starttime,String endtime){
        String result=null;
        try {

            StringBuffer stringBuffer=new StringBuffer();
            stringBuffer.append(getFid(fid));
//            stringBuffer.append("and deptName like '%内科%' ");
//            stringBuffer.append("and type = 3 ");
            stringBuffer.append(" and createTime between to_date('"+starttime);
            stringBuffer.append("','yyyy-mm-dd hh24:mi:ss') and to_date('").append(endtime).append("'," +
                    "'yyyy-mm-dd hh24:mi:ss')");
            stringBuffer.append(" </Msg></MsgInfo></ESBEntry>");
            String input=stringBuffer.toString();
            logger.info("请求预约挂号信息参数是：{}",input);
            result = servicetest(wdsl,input);
            logger.info("请求预约挂号结果是：{}",result);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("请求挂号信息异常："+e.toString());
        }

        return result;
    }
    /**
     *
     * 根据 医生名字，和 看病时间，查询医生看病的人数，和电子病历
     * @param wdsl
     * @param fid
     * @param doctorName  医生名字
     * @param visitTime   看病时间   时间格式：2018-05-25  ，必须传 医生名字，和时间
     * @return
     */

    public static String getHtml(String wdsl,String fid,String doctorName, String visitTime){
        String result=null;
        if(StringUtils.isEmpty(doctorName,visitTime)){
            return null;
        }
        try {
            StringBuffer stringBuffer=new StringBuffer();
            stringBuffer.append(getFid(fid));
            stringBuffer.append(" and doctorName ='");
            stringBuffer.append(doctorName+"'");
            stringBuffer.append(" and updateTime = to_date('"+visitTime+"','yyyy-mm-dd')");
            stringBuffer.append(" </Msg></MsgInfo></ESBEntry>");
            String input=stringBuffer.toString();
            logger.info("请求病历信息参数是：{}",input);
            result = servicetest(wdsl,input);
            logger.info("请求病历结果是：{}",result);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("请求病历信息异常："+e.toString());
        }
        return result;
    }


    public static String getreport(String wdsl,String fid,String doctorName, String visitTime){
        String result=null;
         wdsl="http://172.16.240.124:7803/BS20008/BS20008?wsdl";
         fid="BS20006";
         doctorName="9447492";
        try {
            StringBuffer stringBuffer=new StringBuffer();
            stringBuffer.append(getFid(fid));
            stringBuffer.append(" and PATIENT_ID ='");
            stringBuffer.append(doctorName+"'");
            stringBuffer.append(" </Msg></MsgInfo></ESBEntry>");
            String input=stringBuffer.toString();
            logger.info("请求病历信息参数是：{}",input);
            result = servicetest(wdsl,input);
            logger.info("请求病历结果是：{}",result);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("请求病历信息异常："+e.toString());
        }
        return result;
    }

    public static String getFid(String fid){
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("<ESBEntry><MessageHeader><Fid>").append(fid);
        stringBuffer.append("</Fid><SourceSysCode>S23</SourceSysCode><TargetSysCode>S01</TargetSysCode><MsgDate></MsgDate></MessageHeader><MsgInfo><Msg>");
        return stringBuffer.toString();
    }

    /**
     *用户挂号的时候，扫码一维码 获取门诊号
     * @param wsdl   WS地址
     * @param fid
     * @return   返回soap xml
     */

    public static String outPatientNo(String wsdl,String fid,String cardNo){
        String result= null;
        try {
            StringBuffer stringBuffer=new StringBuffer();
            stringBuffer.append(getFid(fid));
            stringBuffer.append(" and CARD_NO='");
            stringBuffer.append(cardNo);
            stringBuffer.append("'</Msg></MsgInfo></ESBEntry>");
            String input=stringBuffer.toString();
            logger.info("请求门诊信息参数是：{}",input);
            result = servicetest(wsdl,input);
            logger.info("请求门诊结果是：{}",result);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("请求门诊信息异常："+e.toString());
        }
        return result;
    }

    public static String getInfo(String wsdl, String fid, Map<String,String> map){
        String result= null;
        try {
            StringBuffer stringBuffer=new StringBuffer();
            stringBuffer.append(getFid(fid));
            for(Map.Entry<String, String> entry : map.entrySet()){
                stringBuffer.append(" and ");
                stringBuffer.append(entry.getKey()).append("='");
                stringBuffer.append(entry.getValue()).append("'");
            }
            stringBuffer.append("</Msg></MsgInfo></ESBEntry>");
            String input=stringBuffer.toString();
            logger.info("请求门诊信息参数是：{}",input);
            result = servicetest(wsdl,input);
            logger.info("请求门诊结果是：{}",result);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("请求门诊信息异常："+e.toString());
        }

        return result;
    }


    public static String getGhByMZHM(String wdsl,String transCode,String param){

        logger.info("请求逸夫医院参数：{}",param);
        long a=System.currentTimeMillis();
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(wdsl);
        Object[] objects = new Object[0];
        try {
            objects = client.invoke("DoTrans",transCode,param);
            String data=(String)objects[0];
            logger.info("请求逸夫医院结果：{}",data);
            logger.info("请求逸夫医院耗时：{} 毫秒",(System.currentTimeMillis()-a));
            return data;

        } catch (java.lang.Exception e) {
            e.printStackTrace();
            logger.info("请求信息异常了"+e.toString());
        }
        return null;
    }
}