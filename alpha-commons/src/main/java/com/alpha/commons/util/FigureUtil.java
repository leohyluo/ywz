package com.alpha.commons.util;


import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xc.xiong on 2017/1/17.
 * 类型转换器
 */
public class FigureUtil {
    public static Long valueOfLong(Object val) {
        try {
            if (StringUtils.isEmpty(val + "")) {
                return 0L;
            }
            return Long.valueOf(val.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public static Integer valueOfInteger(Object val) {
        try {
            if (StringUtils.isEmpty(val + "")) {
                return 0;
            }
            return valueOfDouble(val).intValue();
        } catch (Exception e) {
            return 0;
        }
    }
    public static Integer valueOfInteger(Object val, Integer defaultValue) {
        try {
            if (StringUtils.isEmpty(val + "")) {
                return defaultValue;
            }
            return valueOfDouble(val).intValue();
        } catch (Exception e) {
            return defaultValue;
        }
    }
    public static Double valueOfDouble(Object val) {
        try {
            if (val == null || StringUtils.isEmpty(val + "")) {
                return 0.00;
            }
            return Double.valueOf(val.toString());
        } catch (Exception e) {
            return 0.00;
        }
    }

    public static String valueOfString(Object val) {
        try {
            if (val == null) {
                return "";
            }
            return val.toString();
        } catch (Exception e) {
            return "";
        }
    }
    public static BigInteger valueOfBigInteger(Object val) {
        try {
            if (val == null) {
                return BigInteger.valueOf(0);
            }
            return new BigInteger(val+"");
        } catch (Exception e) {
            return BigInteger.valueOf(0);
        }
    }

    /**
     * 正则替换所有特殊字符
     *
     * @param orgStr
     * @return
     */
    public static String replaceSpecStr(String orgStr) {
        if (null != orgStr && !"".equals(orgStr.trim())) {
            String regEx = "[\\s~·`!！@#￥$%^……&*（()）\\-——\\-_=+【\\[\\]】｛{}｝\\|、\\\\；;：:‘'“”\"，,《<。.》>、/？?]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(orgStr);
            return m.replaceAll("");
        }
        return "";
    }


}
