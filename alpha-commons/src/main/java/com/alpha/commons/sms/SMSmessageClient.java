package com.alpha.commons.sms;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.alpha.commons.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2016/4/7.
 */
public class SMSmessageClient {

    private static String profileCode;
    private static String apikey;
    private final static Logger s_logger = LoggerFactory.getLogger(SMSmessageClient.class);
    private static String baseURL = "https://apis.baidu.com/kingtto_media/106sms/106sms?tag=2&mobile={}&content={}";

    public static int sendSMS(String phoneNumber, String contentVar) {
        InputStream stream = null;
        try {
            contentVar = URLEncoder.encode(contentVar, "UTF-8");
            String url = StringUtils.replaceBrace(baseURL, phoneNumber, contentVar);
            HttpClient client = new HttpClient();
            GetMethod method = new GetMethod(url);
            method.setRequestHeader("apikey", apikey);
            method.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            int statusCode = client.executeMethod(method);
            if (statusCode == HttpStatus.SC_OK) {
                byte[] responseBody = method.getResponseBody();
                stream = method.getResponseBodyAsStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
                StringBuffer buffer = new StringBuffer();
                String line;
                while (null != (line = br.readLine())) {
                    buffer.append(line);
                }
                s_logger.info(url);
                s_logger.info("响应内容:{}", buffer.toString());
                return getCode(buffer.toString());
            }
        } catch (Exception e) {
            s_logger.error("he server ip.taobao.com failed to respond: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (stream != null)
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return -1;
    }

    public SMSmessageClient() {
    }

    public SMSmessageClient(String profileCode, String apikey)
            throws Exception {
        this.apikey = apikey;
        this.profileCode = profileCode;
    }

    public static int getCode(String content) {
        try {
            if (StringUtils.isNotEmpty(content)) {
                JSONObject jsonObject = JSONObject.parseObject(content);
                String returnstatus = jsonObject.getString("returnstatus");
                if (returnstatus.equals("Success")) {
                    return 1;
                } 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int getCodeByBaidu(String content) {
        try {
            if (StringUtils.isNotEmpty(content)) {
                JSONObject jsonObject = JSONObject.parseObject(content);
                int code = jsonObject.getInteger("code");
                if (code == 1000) {
                    int submitStatus = jsonObject.getJSONObject("date").getInteger("submitStatus");
                    if (submitStatus == 1000) {
                        return 1000;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
