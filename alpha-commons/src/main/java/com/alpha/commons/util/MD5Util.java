package com.alpha.commons.util;

import java.security.MessageDigest;

public class MD5Util {
    public final static String MD5(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            //转为统一编码以免结果不一
            md.update(s.getBytes("UTF-8"));
            byte[] bb = md.digest();
            String hs = "", tmp = "";
            for (byte e : bb) {
                tmp = (Integer.toHexString(e & 0xFF));
                hs = tmp.length() == 1 ? hs + "0" + tmp : hs + tmp;
            }
            return hs;
        } catch (Exception e) {
            return "";
        }
    }

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }


    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            //转为统一编码以免结果不一
            md.update(origin.getBytes("UTF-8"));
            resultString = new String(origin);
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
        } catch (Exception exception) {
        }
        return resultString;
    }

    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};


    public static void main(String[] args) {
        String s = "testappid10001reqinfo{\"clerkid\":\"gdxltest\",\"createtime\":\"2016-11-02 10:00:10.0\",\"orderdtls\":[{\"goodsid\":\"0\",\"goodsname\":\"氯雷他定片\",\"goodsqty\":1},{\"goodsid\":\"0\",\"goodsname\":\"曲安奈德鼻喷雾剂\",\"goodsqty\":1},{\"goodsid\":\"0\",\"goodsname\":\"通窍鼻炎片\",\"goodsqty\":1}],\"orderno\":\"112408\"}serviceSyncOrdertest";
        //获取大类redis key: e0f615c0b0ac3770ce598ff6889d54fa 原始名称:咳嗽
       // System.out.print(MD5Util.MD5("BM|XGXBM|null"));

        System.out.println(MD5("爱女人"));
    }
}