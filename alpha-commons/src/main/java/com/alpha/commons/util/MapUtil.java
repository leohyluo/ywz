package com.alpha.commons.util;

import java.util.Map;

/**
 * Created by MR.Wu on 2018-06-27.
 */
public class MapUtil {

    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;
        Object obj = beanClass.newInstance();
        org.apache.commons.beanutils.BeanUtils.populate(obj, map);
        return obj;
    }
}
