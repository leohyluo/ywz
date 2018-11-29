package com.alpha.his.util;

import net.sourceforge.pinyin4j.PinyinHelper;

/**
 * Created by Administrator on 2018/10/22.
 */
public class PinYinUtil {

    // 返回中文的首字母
    public static String getPinYinHeadChar(String str) {
        String convert = "";
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            } else {
                convert += word;
            }
        }
        return convert.toUpperCase().trim();
    }
}
