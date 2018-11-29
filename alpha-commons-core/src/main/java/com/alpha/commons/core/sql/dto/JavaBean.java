package com.alpha.commons.core.sql.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class JavaBean {

    private static final Logger logger = LoggerFactory.getLogger(JavaBean.class);

    private List<Field> fields;

    private Class<?> clz;

    private List<String> fieldsName;
    private Map<String, String> columnsName = new HashMap<String, String>();

    private String pkName;
    private Class<?> pkType;

    public JavaBean(Class<?> clz) {
        this.clz = clz;
        init();
    }

    private void init() {
        BeanInfo beaninfo = null;
        try {
            beaninfo = Introspector.getBeanInfo(clz);
        } catch (IntrospectionException e) {
            logger.error("加载类失败，class可能尚未被加载", e);
        }


        PropertyDescriptor[] pds = beaninfo.getPropertyDescriptors();
        fieldsName = new ArrayList<String>();

        fields = new ArrayList<Field>();
        for (int i = 0; i < pds.length; i++) {
            String columnName = pds[i].getName();

            if ("class".equals(columnName)) {
                continue;
            }

            //获取主键
            try {
                Field field = clz.getDeclaredField(columnName);
                Column column = field.getAnnotation(Column.class);
                if (column != null) {
                    columnsName.put(columnName, column.name());
                } else {
                    columnsName.put(columnName, columnName);
                }
                Transient aTransient = field.getAnnotation(Transient.class);
                if (aTransient != null)
                    continue;
                fields.add(field);

                Annotation annotationId = field.getAnnotation(Id.class);
                if (annotationId != null) {
                    pkType = field.getType();
                    pkName = columnName;
                }
            } catch (SecurityException e) {
                logger.error("反射获得属性值失败，可能是权限问题", e);
            } catch (NoSuchFieldException e) {
                logger.error("反射获得属性值失败，没有找到对应的属性", e);
            }

            fieldsName.add(columnName);
        }
    }

    public Map<String, String> getColumnsName() {
        return columnsName;
    }

    public void setColumnsName(Map<String, String> columnsName) {
        this.columnsName = columnsName;
    }

    public String getPkName() {
        return pkName;
    }

    public Class<?> getPkType() {
        return pkType;
    }

    public List<String> getFieldsName() {
        return fieldsName;
    }

    public List<Field> getFields() {
        return fields;
    }

    public static Object getValue(String propertyName, Object obj) throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        PropertyDescriptor p = new PropertyDescriptor(propertyName, obj.getClass());
        return p.getReadMethod().invoke(obj);
    }

    public static void setValue(String propertyName, Object obj, Object value) {

        PropertyDescriptor p = null;
        try {
            p = new PropertyDescriptor(propertyName, obj.getClass());
        } catch (IntrospectionException e) {
            logger.error("没有找到对应的属性：" + propertyName, e);
        }
        Class<?> clz = p.getPropertyType();
        Object temp = null;
        if (value == null) {
            return;
        }
        if (value == "" && !clz.getName().equals(String.class.getName())) {
            return;
        }
        if (clz.getName().equals(String.class.getName())) {
            temp = String.valueOf(value);
        } else if (clz.getName().equals(int.class.getName())) {
            temp = Integer.valueOf(value.toString());
        } else if (clz.getName().equals(Integer.class.getName())) {
            temp = Integer.valueOf(value.toString());
        } else if (clz.getName().equals(Double.class.getName())) {
            temp = Double.valueOf(value.toString());
        } else if (clz.getName().equals(Long.class.getName())) {
            temp = Long.valueOf(value.toString());
        } else if (clz.getName().equals(Float.class.getName())) {
            temp = Float.valueOf(value.toString());
        } else if (clz.getName().equals(Date.class.getName())) {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                temp = sdf.parse(value.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            temp = value;
        }

        try {
            p.getWriteMethod().invoke(obj, temp);
        } catch (IllegalArgumentException e) {
            logger.error("通过反射赋值失败，可能是参数或者类型错误，属性名：" + propertyName, e);
        } catch (IllegalAccessException e) {
            logger.error("通过反射赋值失败，可能是权限不足，属性名：" + propertyName, e);
        } catch (InvocationTargetException e) {
            logger.error("通过反射赋值失败，可能是被赋值的对象有问题，属性名：" + propertyName, e);
        }
    }

}
