package com.alpha.commons.util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringJoiner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class CipherUtils {

	private static byte[] kbytes = "qwertyuiopl".getBytes();
    private static String KEY = "Blowfish";
    private static Logger logger = LoggerFactory.getLogger(CipherUtils.class);

    public static String encrypt(String secret) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKeySpec key = new SecretKeySpec(kbytes, KEY);

        Cipher cipher = Cipher.getInstance(KEY);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encoding = cipher.doFinal(secret.getBytes());
        BigInteger n = new BigInteger(encoding);
        return n.toString(16);
    }

    public static String decrypt(String secret) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKeySpec key = new SecretKeySpec(kbytes, KEY);

        BigInteger n = new BigInteger(secret, 16);
        byte[] encoding = n.toByteArray();

        Cipher cipher = Cipher.getInstance(KEY);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decode = cipher.doFinal(encoding);
        return new String(decode);
    }

    public static String MD5(Map<String, String> map,String key) {
        Iterator<Map.Entry<String, String>> entryIterator = map.entrySet().stream().sorted(Map.Entry.<String, String> comparingByKey()).iterator();
        StringJoiner stringJoiner = new StringJoiner("&");
        while (entryIterator.hasNext()) {
            Map.Entry<String, String> o = entryIterator.next();
            String str = o.getKey() + "=" + o.getValue();
            stringJoiner.add(str);
        }
        stringJoiner.add("key="+key);
        String str = stringJoiner.toString();
        String result = MD5(str).toUpperCase();
        logger.info("参与签名的字符串为{},签名结果为{}", str, result);
        return result;
    }

 // MD5加码。32位   
    public static String MD5(String inStr) {   
     MessageDigest md5 = null;   
     try {   
      md5 = MessageDigest.getInstance("MD5");   
     } catch (Exception e) {   
      System.out.println(e.toString());   
      e.printStackTrace();   
      return "";   
     }   
     char[] charArray = inStr.toCharArray();   
     byte[] byteArray = new byte[charArray.length];   
     
     for (int i = 0; i < charArray.length; i++)   
      byteArray[i] = (byte) charArray[i];   
     
     byte[] md5Bytes = md5.digest(byteArray);   
     
     StringBuffer hexValue = new StringBuffer();   
     
     for (int i = 0; i < md5Bytes.length; i++) {   
      int val = ((int) md5Bytes[i]) & 0xff;   
      if (val < 16)   
       hexValue.append("0");   
      hexValue.append(Integer.toHexString(val));   
     }   
     
     return hexValue.toString();   
    }   
     
    // 可逆的加密算法   
    public static String KL(String inStr) {   
     // String s = new String(inStr);   
     char[] a = inStr.toCharArray();   
     for (int i = 0; i < a.length; i++) {   
      a[i] = (char) (a[i] ^ 't');   
     }   
     String s = new String(a);   
     return s;   
    }   
     
    // 加密后解密   
    public static String JM(String inStr) {   
     char[] a = inStr.toCharArray();   
     for (int i = 0; i < a.length; i++) {   
      a[i] = (char) (a[i] ^ 't');   
     }   
     String k = new String(a);   
     return k;   
    }   
       
    public static void main(String[] args) {
        String account = "2001489978062134";
        String password = "66753677";
        String key = "rlMFhT8zHOTKM6Ar";
        String str="Account="+account+"&Password="+password+"&Key="+key; 
        
        System.out.println("原始：" + str);   
        System.out.println("MD5后：" + MD5(str));

        Map<String, String> signMap = new HashMap<>();
        key = "123456789";
        signMap.put("cardNo", "A002");
        signMap.put("pno", "1519665041514");
        String result = MD5(signMap,key);
        System.out.println(result);
    }
}
