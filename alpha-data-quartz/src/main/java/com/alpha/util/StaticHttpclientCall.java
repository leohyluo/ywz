package com.alpha.util;

/**
 * Created by HP on 2018/7/2.
 */



import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.HttpException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


public class StaticHttpclientCall {

    private static Logger logger = LoggerFactory.getLogger(StaticHttpclientCall.class);

    /**
     * post 请求 WS
     * @throws IOException
     * @throws HttpException
     */
    public static String servicetest(String wsdl, String input ) throws HttpException, IOException {
       long a=System.currentTimeMillis();
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
        // 最后生成一个HttpClient对象，并发出postMethod请求
        HttpClient httpClient = new HttpClient();
        int statusCode = httpClient.executeMethod(postMethod);
        if(statusCode == 200) {
            String soapResponseData = postMethod.getResponseBodyAsString();
            soapResponseData=soapResponseData.replace("&lt;","<");
            soapResponseData=soapResponseData.replace("&gt;",">");
            logger.info("请求平台数据耗时："+(System.currentTimeMillis()-a)+"毫秒");
            return soapResponseData;
        } else {
            logger.info("请求平常数据异常"+statusCode);
            return null;
        }
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
            logger.info("请求住院通知单信息--》参数是：{}",input);
            result = servicetest(wsdl,input);
//            logger.info("请求住院通知单信息--》结果是：{}",result);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("请求通知单信息异常："+e.toString());
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
    public static String all(String wdsl,String fid,String starttime,String endtime) {
        String result = null;
        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(getFid(fid));
            stringBuffer.append(" and createTime between to_date('" + starttime);
            stringBuffer.append("','yyyy-mm-dd hh24:mi:ss') and to_date('").append(endtime).append("'," +
                    "'yyyy-mm-dd hh24:mi:ss')");
            stringBuffer.append(" </Msg></MsgInfo></ESBEntry>");
            String input = stringBuffer.toString();
            logger.info("请求挂号信息参数是：{}", input);
            result = servicetest(wdsl, input);
//            logger.info("请求挂号结果是：{}", result);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("请求挂号信息异常：" + e.toString());
        }
        return result;
    }


    public static String getFid(String fid){
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("<ESBEntry><MessageHeader><Fid>").append(fid);
        stringBuffer.append("</Fid><SourceSysCode>S23</SourceSysCode><TargetSysCode>S01</TargetSysCode><MsgDate></MsgDate></MessageHeader><MsgInfo><Msg>");
        return stringBuffer.toString();
    }


}
