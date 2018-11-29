package com.alpha.redis;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


/**
 * @author wfh
 */
public class ConfReaderImpl {

    private static final Logger logger = LoggerFactory.getLogger(ConfReaderImpl.class);

    private static Map<String, String> memy = new HashMap<String, String>();

    /**
     * 根据KEY获取缓中的数据
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getValueFromMe(String key, String defaultValue) {
        String value = memy.get(key);
        if (null == value) {
            return defaultValue;
        }
        return value;
    }

    public static Map<String, String> getMemy() {
        return memy;
    }

    public static void setMemy(Map<String, String> memy) {
        ConfReaderImpl.memy = memy;
    }

}
