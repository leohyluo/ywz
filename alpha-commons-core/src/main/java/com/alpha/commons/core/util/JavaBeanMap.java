package com.alpha.commons.core.util;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/5/28.
 */

public class JavaBeanMap {

    private static final Logger LOGGER = LoggerFactory.getLogger(JavaBeanMap.class);

    //将javabean转为map类型，然后返回一个map类型的值
    public static Map<String, Object> beanToMap(Object obj) {
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
            for (int i = 0; i < descriptors.length; i++) {
                String name = descriptors[i].getName();
                if (!StringUtils.equals(name, "class")) {
                    params.put(name, propertyUtilsBean.getNestedProperty(obj, name));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    // map 转成成 Javabean
    public static <T> Object convertMap2JavaBean(Map map, Class clz) {
        if (map == null)
            return null;
        try {
            //拿到所有属性
            Object object = clz.newInstance();
            Field[] fs = clz.getDeclaredFields();
            for (Field f : fs) {
                try {
                    Column column = f.getAnnotation(Column.class);
                    String propertyname = f.getName();
                    String key = f.getName();
                    if (column != null) {
                        key = column.name();
                    }
                    if (map.containsKey(key)) {
                        String methodname = "set" + propertyname.substring(0, 1).toUpperCase() + propertyname.substring(1);
                        //拿到 set 方法
                        Method mset = object.getClass().getDeclaredMethod(methodname, f.getType());
                        //String getmethodname="get"+propertyname.substring(0,1).toUpperCase()+propertyname.substring(1);
                        // 拿到 get 方法
                        //Method mget=obj.getClass().getDeclaredMethod(getmethodname);
                        //拿到 map 中的值
                        Object value = map.get(key);
                        Object temp = null;
                        if (value != null && f.getType() == Integer.class) {
                            temp = Integer.valueOf(value + "");
                            mset.invoke(object, temp);
                        } else if (value != null && f.getType() == Long.class) {
                            temp = Long.valueOf(value + "");
                            mset.invoke(object, temp);
                        } else if (value != null) {
//                                LOGGER.info("{},{},{}", value, f.getType(), mset.getName());
                            Class ty = f.getType();
                            value = ty.cast(value);
                            mset.invoke(object, value);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param arrayList list 里面是个map
     * @param
     */
    public static List convertListToJavaBean(List arrayList, Class clz) {
        if (arrayList == null || arrayList.size() == 0)
            return arrayList;
        List list = new ArrayList();
        for (Object objTemp : arrayList) {
            Map map = (Map) objTemp;
            try {
                //拿到所有属性
                Object object = clz.newInstance();
                Field[] fs = clz.getDeclaredFields();
                for (Field f : fs) {
                    try {
                        Column column = f.getAnnotation(Column.class);
                        String propertyname = f.getName();
                        String key = f.getName();
                        if (column != null) {
                            key = column.name();
                        }
                        if (map.containsKey(key)) {
                            String methodname = "set" + propertyname.substring(0, 1).toUpperCase() + propertyname.substring(1);
                            //拿到 set 方法
                            Method mset = object.getClass().getDeclaredMethod(methodname, f.getType());
                            //String getmethodname="get"+propertyname.substring(0,1).toUpperCase()+propertyname.substring(1);
                            // 拿到 get 方法
                            //Method mget=obj.getClass().getDeclaredMethod(getmethodname);
                            //拿到 map 中的值
                            Object value = map.get(key);
                            Object temp = null;
                            if (value != null && f.getType() == Integer.class) {
                                temp = Integer.valueOf(value + "");
                                mset.invoke(object, temp);
                            } else if (value != null && f.getType() == Long.class) {
                                temp = Long.valueOf(value + "");
                                mset.invoke(object, temp);
                            } else if (value != null) {
//                                LOGGER.info("{},{},{}", value, f.getType(), mset.getName());
                                Class ty = f.getType();
                                value = ty.cast(value);
                                mset.invoke(object, value);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                list.add(object);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    // map 转成成 Javabean
    public static void convertMapToJavaBean(Map map, Object object) {
        if (map == null)
            return;
        try {
            //拿到所有属性
            Class clz = object.getClass();
            Field[] fs = clz.getDeclaredFields();
            for (Field f : fs) {
                try {
                    Column column = f.getAnnotation(Column.class);
                    String propertyname = f.getName();
                    String key = f.getName();
                    if (column != null) {
                        key = column.name();
                    }
                    if (map.containsKey(key)) {
                        String methodname = "set" + propertyname.substring(0, 1).toUpperCase() + propertyname.substring(1);
                        //拿到 set 方法
                        Method mset = object.getClass().getDeclaredMethod(methodname, f.getType());
                        //String getmethodname="get"+propertyname.substring(0,1).toUpperCase()+propertyname.substring(1);
                        // 拿到 get 方法
                        //Method mget=obj.getClass().getDeclaredMethod(getmethodname);
                        //拿到 map 中的值
                        Object value = map.get(key);
                        Object temp = null;
                        if (value != null && f.getType() == Integer.class) {
                            temp = Integer.valueOf(value + "");
                            mset.invoke(object, temp);
                        } else if (value != null && f.getType() == Long.class) {
                            temp = Long.valueOf(value + "");
                            mset.invoke(object, temp);
                        } else if (value != null) {
//                                LOGGER.info("{},{},{}", value, f.getType(), mset.getName());
                            Class ty = f.getType();
                            value = ty.cast(value);
                            mset.invoke(object, value);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
