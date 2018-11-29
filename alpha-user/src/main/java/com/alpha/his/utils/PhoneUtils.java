package com.alpha.his.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 手机校验工具
 * Created by Administrator on 2018/3/13.
 */

public class PhoneUtils {

    /**
     * 校验是不是正确的手机号
     * @param str
     * @return
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8,9][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

}

