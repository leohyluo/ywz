/*
package com.alpha.his.webservice;

import com.alpha.commons.util.CipherUtils;
import com.google.gson.Gson;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class HisOperateApiServiceTest {

    private Client client = null;

    @Before
    public void setup() {
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        client = dcf.createClient("http://192.168.29.66:19092/services/UserCardService?wsdl");
    }

    @Test
    public void testGetEmrInfoURL() {
        try {
            // invoke("方法名",参数1,参数2,参数3....);
            //150531107 15343206

            String key = "zhihuiyixue@#$%123456";
            String outPatientNo = "150531129";
            String registerNo = "15343228";

            Map<String, String> signMap = new HashMap<>();
            signMap.put("cardNo", outPatientNo);
            signMap.put("pno", registerNo);
            String sign = CipherUtils.MD5(signMap, key);


            Object[] objects = new Object[0];
            objects = client.invoke("getEmrInfoUrl",outPatientNo,registerNo, sign);
            //objects = client.invoke("getEmrInfoData","150531107","15353143", "35DE198DE70DC7A14C88093298A323EF");
            String data=(String)objects[0];
            System.out.println(data);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testGetEmrInfoData() {
        try {
            // invoke("方法名",参数1,参数2,参数3....);

            String key = "zhihuiyixue@#$%123456";
            String outPatientNo = "150531129";
            String registerNo = "15343228";

            Map<String, String> signMap = new HashMap<>();
            signMap.put("cardNo", outPatientNo);
            signMap.put("pno", registerNo);
            String sign = CipherUtils.MD5(signMap, key);


            Object[] objects = new Object[0];
            objects = client.invoke("getEmrInfoData",outPatientNo,registerNo, sign);
            String data=(String)objects[0];
            System.out.println(data);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }
}
*/
