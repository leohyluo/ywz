package com.alpha.commons.core.util;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.*;
import java.util.UUID;

/**
 * Created by xc.xiong on 2017/6/19.
 */
public class StringUtil {

    public static boolean isPass(String answerContent, String answerContent2) {
        Double s1 = Double.valueOf(answerContent);
        Double s2 = Double.valueOf(answerContent2);
        if(s2 > s1)
            return true;
        return false;
    }

    public static String getMax(String str1, String str2){
        Double s1 = Double.valueOf(str1);
        Double s2 = Double.valueOf(str2);
        if(s1.compareTo(s2) > 0)
            return str1;
        return str2;
    }

    public static String getMin(String str1, String str2){
        Double s1 = Double.valueOf(str1);
        Double s2 = Double.valueOf(str2);
        if(s1.compareTo(s2) > 0)
            return str2;
        return str1;
    }


    /**
     * list 对象克隆
     *
     * @param obj
     * @return
     */
    public static Object cloneObject(Object obj) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bo);
            os.writeObject(obj);
            ByteArrayInputStream bo1 = new ByteArrayInputStream(bo.toByteArray());
            ObjectInputStream os1 = new ObjectInputStream(bo1);
            return os1.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * inputStream 对象克隆
     *
     * @param inputStream
     * @return
     */
    public static Object cloneInputStream(InputStream inputStream) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            IOUtils.copy(inputStream, baos);
            ByteArrayInputStream bo1 = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream os1 = new ObjectInputStream(bo1);
            return os1.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String genUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String replaceBrace(String source, String... args) {
        StringBuilder sb = new StringBuilder(source);
        for (int i = 0; i < args.length; i++) {
            sb.replace(sb.indexOf("{}"), sb.indexOf("{}") + 2, args[i]);
        }
        return sb.toString();
    }


}
