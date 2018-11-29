package com.alpha.utils;

import com.alpha.commons.core.framework.SpringContextHolder;
import com.alpha.commons.core.mapper.*;
import com.alpha.commons.core.pojo.OutPatientInfo;
import com.alpha.commons.core.pojo.inspcetion.JYRequest;
import com.alpha.commons.core.util.SoapUtil;
import com.alpha.commons.core.util.StaticHttpclientCall;
import com.google.gson.Gson;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/22.
 */
public class InspectionUtils {

    private static Logger logger = LoggerFactory.getLogger(InspectionUtils.class);


    public static String push160(String cardNo ,String content,String phone,String title,String note, String url){
        try {

            StringBuffer stringBuffer=new StringBuffer();
            stringBuffer.append("<soapenv:Envelope\n" +
                    "xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
                    "xmlns:ser=\"http://service.bd.com/\">\n" +
                    "<soapenv:Header/>\n" +
                    "<soapenv:Body>\n" +
                    "<ser:requestWS>\n" +
                    "<request>\n" +
                    "<![CDATA[\n" +
                    "<request><head><key>hisRemind_common_message</key><hospcode>111</hospcode><token></token><time>20180425201845</time></head><body>");
            stringBuffer.append("<cardNo>"+cardNo+"</cardNo>");
            stringBuffer.append("<title>"+title+"</title>");
            stringBuffer.append("<content>"+content+"</content>");
            stringBuffer.append("<note>"+note+"</note>");
            stringBuffer.append("<phone>"+phone+"</phone>");
            stringBuffer.append("<url>"+url+"</url>");
            stringBuffer.append("</body></request>" +
                    "]]>\n" +
                    "</request>\n" +
                    "</ser:requestWS>\n" +
                    "</soapenv:Body>\n" +
                    "</soapenv:Envelope>");
            String soapRequestData =stringBuffer.toString();
            PostMethod postMethod = new PostMethod("http://121.15.136.85:17001/hdepc/services/hisWebService?wsdl");
            byte[] b = soapRequestData.getBytes("utf-8");
            InputStream is = new ByteArrayInputStream(b, 0, b.length);
            RequestEntity re = new InputStreamRequestEntity(is, b.length,
                    "application/soap+xml; charset=utf-8");
            postMethod.setRequestEntity(re);
            HttpClient httpClient = new HttpClient();
            int statusCode = httpClient.executeMethod(postMethod);
            if(statusCode == 200) {
                String soapResponseData = postMethod.getResponseBodyAsString();
                return soapResponseData;
            }
            else {
                return "调用失败错误码：" + statusCode;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void recordPush(Map<Integer,String> map,Integer type){
        Map<String,Integer> param=new HashMap<>();
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            JYRequest jyRequest=new JYRequest();
            if(entry.getValue().equals("00")){
                param.put("id",entry.getKey());
                param.put("pushStatus",3);
            }else if(entry.getValue().contains("成功")){
                param.put("id",entry.getKey());
                param.put("pushStatus",1);
            }else {
                param.put("id",entry.getKey());
                param.put("pushStatus",2);
            }
            if(type==1){
                SpringContextHolder.getBean(JYRequestMapper.class).updateByMap(param);
            }else if (type==2){
                SpringContextHolder.getBean(XNDRequestMapper.class).updateByMap(param);
            }else if(type==3){
                SpringContextHolder.getBean(FSRequestMapper.class).updateByMap(param);
            }else {
                SpringContextHolder.getBean(CSReportMapper.class).updateByMap(param);
            }
        }
    }

    public static Object getInfo(Map<String,String> map){
        String wsdl="http://172.16.240.124:7807/BS10008?wsdl";	// 一卡通信息
        String resultXml= StaticHttpclientCall.getInfo(wsdl,"BS10008",map);
        SoapUtil.parseSoapXml(resultXml);
        List<String> list = SoapUtil.parseETYYxml(resultXml);
//        OutPatientInfo outPatientInfo=new OutPatientInfo();
//        String str=outPatientInfo.getClass().toString();
//        str=str.replace("class ","");
//        str=str.replace(" ","");
//        List<Object> objectList=SoapUtil.string2obj(list,str,SoapUtil.outpatientmap);
//        outPatientInfo=(OutPatientInfo)objectList.get(0);
        return list;

    }

    public static void main(String[] args) {
        Map<String,String> param=new HashMap<>();
        param.put("PATIENT_ID","7615264");
//        param.put("mzhm","1803294157");
//        OutPatientInfo outPatientInfo=getInfo(param);
//        System.out.println(new Gson().toJson(outPatientInfo));
    }

}
