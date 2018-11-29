package com.alpha.commons.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import com.alpha.commons.api.tencent.offical.dto.AccessTokenDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WecharUtils {
	
	private static final String TOKEN = "zxcvbnm12345678";
	private static Map<String, AccessTokenDTO> accessTokenMap = new HashMap<>();
	private static final String ACCESSTOKEN_KEY = "AccessToken";
	private static Logger logger = LoggerFactory.getLogger(WecharUtils.class);

	/**
     * 校验签名
     * @param signature 签名
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @return 布尔值
     */
    public static boolean checkSignature(String signature,String timestamp,String nonce){
        String checktext = null;
        if (null != signature) {
            //对ToKen,timestamp,nonce 按字典排序
            String[] paramArr = new String[]{TOKEN,timestamp,nonce};
            Arrays.sort(paramArr);
            //将排序后的结果拼成一个字符串
            String content = paramArr[0].concat(paramArr[1]).concat(paramArr[2]);

            try {
                MessageDigest md = MessageDigest.getInstance("SHA-1");
                //对接后的字符串进行sha1加密
                byte[] digest = md.digest(content.toString().getBytes());
                checktext = byteToStr(digest);
            } catch (NoSuchAlgorithmException e){
                e.printStackTrace();
            }
        }
        //将加密后的字符串与signature进行对比
        return checktext !=null ? checktext.equals(signature.toUpperCase()) : false;
    }
   
    public static String getCallBackEvent(String text) {
    	try {
    		Document document = XMLUtils.getXMLByString(text);
    		Element root = document.getRootElement();
    		Element event = root.element("Event");
    		String eventText = event.getText();
    		return eventText;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    public static AccessTokenDTO getAccessTokenDTO(){
        logger.info("从本地获取AccessToken");
    	return accessTokenMap.get(ACCESSTOKEN_KEY);
    }
    
    public static Boolean AccessTokenExpired(AccessTokenDTO tokenDTO) {
    	Long expireTime = tokenDTO.getExpireTime();
    	Long now = System.currentTimeMillis();
    	if(expireTime.longValue() > now.longValue()) {
    		return false;
    	}
    	return true;
    }
    
    public static void saveAccessTokenDTO(AccessTokenDTO tokenDTO) {
        try {
            logger.info("将AccessToken保存到本地");
            String expireInStr = tokenDTO.getExpires_in();
            Long expireIn = Long.valueOf(expireInStr); //单位秒
            Long expireInms = expireIn * 1000;
            Long expireTime = System.currentTimeMillis() + expireInms;
            tokenDTO.setExpireTime(expireTime);
            accessTokenMap.put(ACCESSTOKEN_KEY, tokenDTO);
            logger.info("将AccessToken保存到本地成功");
        } catch (Exception e) {
            logger.error("将AccessToken保存到本地失败", e);
        }
    }
    
    /**
     * 将字节数组转化我16进制字符串
     * @param byteArrays 字符数组
     * @return 字符串
     */
    private static String byteToStr(byte[] byteArrays){
        String str = "";
        for (int i = 0; i < byteArrays.length; i++) {
            str += byteToHexStr(byteArrays[i]);
        }
        return str;
    }

    /**
     *  将字节转化为十六进制字符串
     * @param myByte 字节
     * @return 字符串
     */
    private static String byteToHexStr(byte myByte) {
        char[] Digit = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        char[] tampArr = new char[2];
        tampArr[0] = Digit[(myByte >>> 4) & 0X0F];
        tampArr[1] = Digit[myByte & 0X0F];
        String str = new String(tampArr);
        return str;
    }
    
}
