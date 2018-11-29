package com.alpha.his;


import com.alpha.commons.api.tencent.qcloud.Utilities.MD5;
import com.alpha.commons.core.util.ParamUtil;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2018/3/14.
 */
public class testSebService {


    /**
     * 拉取电子病历
     */
    public static void pull() {
        long a = System.currentTimeMillis();
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://192.168.29.61:19092/services/UserCardService?wsdl");
        Object[] objects = new Object[0];
        try {
            // invoke("方法名",参数1,参数2,参数3....);
            String cardNo = "16060612006";
            String pNo = "24323771";
            String doctorName = "内科专家综合副高";
            String sign = paramGene(cardNo, pNo);
            objects = client.invoke("getEmrInfoUrlNew", cardNo, pNo, doctorName, sign);
            String data = (String) objects[0];
            System.out.println(data);
            System.out.println((System.currentTimeMillis() - a) / 1000);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 导入电子病历
     */
    public static void importMedical() {
        long a = System.currentTimeMillis();
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://192.168.29.66:19092/services/UserCardService?wsdl");
        Object[] objects = new Object[0];
        try {
            // getEmrInfoDataNew(String cardNo, String pno, String importName, String doctorName, String sign)
            String cardNo = "123";
            String pNo = "239820002";
            String doctorName = "内科专家综合副高";
            String importName = "mainSymptomName";
            String sign = paramGene(cardNo, pNo);
            sign = "8F470175AE626F33B59FF10A5AA80A7E";

            objects = client.invoke("getEmrInfoDataNew", cardNo, pNo, importName, doctorName,sign);
            String data = (String) objects[0];
            System.out.println(data);
            System.out.println((System.currentTimeMillis() - a) / 1000);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 加密参数生成
     */

    public static String paramGene(String cardNo, String pNo) {
        String key = "zhihuiyixue@#$%123456";
        String timestamp = String.valueOf(System.currentTimeMillis());
        Map paraMap = new HashMap();
        paraMap.put("cardNo", "202671752");
//        paraMap.put("timestamp",timestamp);
//        paraMap.put("doctorName","王培");
        paraMap.put("pno", "202671752");
        paraMap.put("importName","mainSymptomName");
//        paraMap.put("idCard","123");
        String url = ParamUtil.formatUrlMap(paraMap, true, false);
        System.out.println(url);
//        url="cardNo=4545109@importName=61@pno=202671752@key=zhihuiyixue@#$%123456";
        url = url + "&key=" + key;
        System.out.println(url);
        String sign = MD5.stringToMD5(url);
        return sign;
    }


    public static void test2() {

        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://127.0.0.1:19092/services/UserCardService?wsdl");

                String data = null;
                try {
                    long a = System.currentTimeMillis();
                    Object[] objects = new Object[0];
                    // invoke("方法名",参数1,参数2,参数3....);
                    objects = client.invoke("getEmrInfoDataNew", "202671752", "202671752","mainSymptomName","王培","7ac314f425d6d09f21b75840ae71cd28");
                    data = (String) objects[0];
                    System.out.println(data);
                    System.out.println("共计耗时:" + (System.currentTimeMillis() - a));

                } catch (Exception e) {
                    e.printStackTrace();
                }


    }



    public static void main(String[] args) {


//        System.out.println( paramGene("",""));
        test2();



    }

}
