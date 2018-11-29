package com.alpha.wechar.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alpha.commons.api.tencent.offical.WecharConstant;
import com.alpha.commons.api.tencent.offical.dto.AccessTokenDTO;
import com.alpha.commons.util.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WeixinSignUtil {

    public static Map<String, Object> sign(AccessTokenDTO accessToken, String url) throws Exception {
        Map<String, Object> ret = new HashMap<String, Object>();
        //获取微信的accessToken
        if (StringUtils.isEmpty(accessToken.getAccess_token())) {
            ret.put("errcode", accessToken.getErrcode());
            ret.put("errmsg", accessToken.getErrmsg());
            return ret;
        }
        //获取jsapi_ticket
        String signUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken.getAccess_token() + "&type=jsapi";
        String resultSign = getHttpResult(signUrl);
        JSONObject ticket = JSON.parseObject(resultSign);
        if (StringUtils.isEmpty(ticket.getString("ticket"))) {
            ret.put("errcode", ticket.getInteger("errcode"));
            ret.put("errmsg", ticket.getString("errmsg"));
            return ret;
        }
        //组装参数
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + ticket.getString("ticket") +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        //签名
        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        crypt.reset();
        crypt.update(string1.getBytes("UTF-8"));
        signature = byteToHex(crypt.digest());

        ret.put("url", url);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        ret.put("jsapi_ticket", ticket);
        ret.put("appId", WecharConstant.APPID);
        return ret;
    }


    /**
     * 随机加密
     *
     * @param hash
     * @return
     */
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    /*
     * 获取访问地址链接返回值
     */
    private static String getHttpResult(String url) {
        String result = "";
        HttpGet httpRequest = new HttpGet(url);
        try {
            HttpResponse httpResponse = HttpClients.createDefault().execute(httpRequest);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(httpResponse.getEntity());
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            result = e.getMessage().toString();
        } catch (IOException e) {
            e.printStackTrace();
            result = e.getMessage().toString();
        }
        return result;
    }

    /**
     * 产生随机串--由程序自己随机产生
     *
     * @return
     */
    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }


    /**
     * 由程序自己获取当前时间
     *
     * @return
     */
    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
}
