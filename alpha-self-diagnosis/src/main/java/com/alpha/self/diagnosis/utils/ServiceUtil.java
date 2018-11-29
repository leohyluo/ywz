package com.alpha.self.diagnosis.utils;

import com.alibaba.fastjson.JSON;
import com.alpha.commons.util.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xc.xiong on 2017/9/7.
 */
public class ServiceUtil {

    private static final SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * array 转String 【、】隔开
     *
     * @param arrays
     * @return
     */
    public static String arrayConvertToString(Collection<String> arrays) {
        if (arrays == null || arrays.size() == 0)
            return "";
        String str = JSON.toJSONString(arrays);
        return str.replace("[", "").replace("]", "")
                .replace(",", "、").replace("\"", "");
    }

    /**
     * 根据生日计算年龄
     *
     * @param brithday
     * @return
     */
    public static String createAge(String brithday) {
        int year = getYearByBirthdate(brithday);
        if (year > 0) {
            return year + "岁";
        }
        int month = getMonthByBirthdate(brithday);
        if (month > 0) {
            return month + "个月";
        }
        int day = getDayByBirthdate(brithday);
        if (day > 0) {
            return day + "天";
        }
        return "";
    }

    /**
     * 根据日期计算年龄
     *
     * @param brithday
     * @return
     * @throws
     * @throws Exception
     */
    public static int getYearByBirthdate(String brithday) {
        try {
            Calendar todayCal = Calendar.getInstance();
            todayCal.setTime(new Date());
            Calendar brithDayCal = Calendar.getInstance();
            brithDayCal.setTime(formatDate.parse(brithday));
            return todayCal.get(Calendar.YEAR) - brithDayCal.get(Calendar.YEAR);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 根据日期计算月份
     *
     * @param brithday
     * @return
     * @throws
     * @throws Exception
     */
    public static int getMonthByBirthdate(String brithday) {
        try {
            Calendar todayCal = Calendar.getInstance();
            todayCal.setTime(new Date());
            Calendar brithDayCal = Calendar.getInstance();
            brithDayCal.setTime(formatDate.parse(brithday));
            return todayCal.get(Calendar.MONTH) - brithDayCal.get(Calendar.MONTH);
        } catch (Exception e) {
            return 0;
        }
    }


    /**
     * 根据日期计算天数
     *
     * @param brithday
     * @return
     * @throws
     * @throws Exception
     */
    public static int getDayByBirthdate(String brithday) {
        try {
            Calendar todayCal = Calendar.getInstance();
            todayCal.setTime(new Date());
            Calendar brithDayCal = Calendar.getInstance();
            brithDayCal.setTime(formatDate.parse(brithday));
            return todayCal.get(Calendar.DATE) - brithDayCal.get(Calendar.DATE);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 随机最大的发病概率
     *
     * @return
     */
    public static Double getTempProbability() {
        int max = 80;
        int min = 60;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s / 100d;
    }

    /**
     * 计算发病概率
     *
     * @param maxProbability
     * @param probabilityWeight
     * @param weight
     * @return
     */
    public static Double getTempProbability(Double maxProbability, Double probabilityWeight, Double weight) {
        BigDecimal b = new BigDecimal(maxProbability / probabilityWeight * weight);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 过滤特殊字符
     *
     * @param str // 只允许字母和数字 // String regEx ="[^a-zA-Z0-9]";
     *            // 清除掉所有特殊字符
     * @return
     * @throws
     */
    public static String stringFilter(String str) {
        try {
            if (StringUtils.isEmpty(str)) {
                return str;
            }
            String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(str);
            return m.replaceAll("").trim();
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }
}
