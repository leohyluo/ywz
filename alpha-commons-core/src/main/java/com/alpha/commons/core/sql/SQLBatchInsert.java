package com.alpha.commons.core.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class SQLBatchInsert {

    private static final Logger logger = LoggerFactory.getLogger(SQLInsert.class);

    private Class<?> clz;
    private List<? extends Object> list;

    private String tableName;
    private Map<String, Object> values;

    //要插入的列
    private List<String> fieldList;

    private Set<String> columnSet;

    private String pk;
    private Boolean insertWithPK = false;

    /**
     * sql插入工具对象
     *
     * @param list 要插入数据库的对象
     */
    public SQLBatchInsert(List<? extends Object> list) {
        this.clz = list.get(0).getClass();

        Table table = clz.getAnnotation(Table.class);
        if (table != null)
            this.tableName = table.name();
        else
            this.tableName = clz.getName().substring(clz.getName().lastIndexOf(".") + 1);
        this.list = list;
        initFields();
    }

    /**
     * sql插入工具对象
     *
     * @param list    要插入数据库的对象
     * @param columns 要插入的列
     */
    public SQLBatchInsert(List<? extends Object> list, String... columns) {
        this.clz = list.get(0).getClass();

        Table table = clz.getAnnotation(Table.class);
        if (table != null)
            this.tableName = table.name();
        else
            this.tableName = clz.getName().substring(clz.getName().lastIndexOf(".") + 1);
        this.list = list;
        columnSet = new LinkedHashSet<String>();
        for (int i = 0; i < columns.length; i++) {
            columnSet.add(columns[i]);
        }
        initFields();
    }

    /**
     * 插入时，是否插入主键
     *
     * @param insertWithPK
     */
    public void setInsertWithPK(Boolean insertWithPK) {
        this.insertWithPK = insertWithPK;
    }

    private void initFields() {
        BeanInfo beaninfo = null;
        try {
            beaninfo = Introspector.getBeanInfo(clz);
        } catch (IntrospectionException e) {
            logger.error("SQLInser初始化，反射生成属性失败，class可能尚未被加载", e);
        }

        values = new LinkedHashMap<String, Object>();
        fieldList = new ArrayList<String>();

        PropertyDescriptor[] pds = beaninfo.getPropertyDescriptors();
        for (int i = 0; i < pds.length; i++) {
            String columnName = pds[i].getName();

            if ("class".equals(columnName)) {
                continue;
            }

            //插入时，不插入主键列
            if (!insertWithPK) {
                if (columnName.equals(pk)) {
                    continue;
                }
                //获取主键
                try {
                    Field field = clz.getDeclaredField(columnName);
                    Column column = field.getAnnotation(Column.class);
                    if (column != null)
                        columnName = column.name();
                    Annotation aId = field.getAnnotation(Id.class);
                    if (aId != null) {//如果是主键，则跳过
                        pk = columnName;
                        continue;
                    }
                    Transient aTransient = field.getAnnotation(Transient.class);
                    if (aTransient != null)
                        continue;
                } catch (SecurityException e) {
                    logger.error("SQLInser初始化，反射获得属性值失败，可能是权限问题", e);
                } catch (NoSuchFieldException e) {
                    logger.error("SQLInser初始化，反射获得属性值失败，没有找到对应的属性", e);
                }
            }

            if (columnSet != null && columnSet.size() > 0) {
                if (!columnSet.contains(columnName)) {
                    continue;
                }
            }

            fieldList.add(columnName);
            try {
                for (int j = 0; j < list.size(); j++) {
                    values.put(columnName + "(" + j + ")", pds[i].getReadMethod().invoke(list.get(j)));
                }
            } catch (IllegalArgumentException e) {
                logger.error("SQLInser初始化，反射获得属性值失败，可能是传入参数有误", e);
            } catch (IllegalAccessException e) {
                logger.error("SQLInser初始化，反射获得属性值失败，可能是反射权限不足", e);
            } catch (InvocationTargetException e) {
                logger.error("SQLInser初始化，反射获得属性值失败，可能是传入目标类错误", e);
            }
        }
    }

    public String getInsertSQL() {
        //insert into ${tablename}(${columns}) values${values}
        StringBuffer sql = new StringBuffer("insert into ${tablename}(${columns}) values${values}");

        int tableNameIndex = sql.indexOf("${tablename}");
        sql.replace(tableNameIndex, tableNameIndex + "${tablename}".length(), tableName);

        int columnIndex = sql.indexOf("${columns}");
        sql.replace(columnIndex, columnIndex + "${columns}".length(), getColumnSQL());

        int valuesIndex = sql.indexOf("${values}");
        sql.replace(valuesIndex, valuesIndex + "${values}".length(), getValueSQL());

        return sql.toString();

    }

    private String getColumnSQL() {
        int length = fieldList.size();
        int i = 0;
        StringBuffer sql = new StringBuffer();
        for (String key : fieldList) {
            sql.append(key);
            if (i < length - 1) {
                sql.append(',');
            }
            i++;
        }
        return sql.toString();
    }

    public String getValueSQL() {
        int length = fieldList.size();

        StringBuffer sql = new StringBuffer();
        for (int j = 0; j < list.size(); j++) {
            sql.append("(");
            for (int i = 0; i < fieldList.size(); i++) {
                String key = fieldList.get(i);
                sql.append("#{");
                sql.append(key);
                sql.append("(");
                sql.append(j);
                sql.append(")");
                sql.append("}");
                if (i < length - 1) {
                    sql.append(',');
                }
            }
            sql.append(")");
            if (j < list.size() - 1) {
                sql.append(',');
            }
        }
        return sql.toString();
    }

    @Override
    public String toString() {
        return getInsertSQL();
    }

    public Map<String, Object> getValues() {
        Map<String, Object> temp = new LinkedHashMap<String, Object>();
        temp.putAll(values);
        return temp;
    }

    /**
     * 测试
     * @param args
     */
//	@Deprecated
//	public static void main(String[] args) {
//		Car_brand brand = new Car_brand();
//		brand.setBrand_name("123");
//
//		Car_brand brand1 = new Car_brand();
//		brand.setBrand_name("456");
//
//		List<Car_brand> listCar = new ArrayList<Car_brand>();
//		listCar.add(brand);
//		listCar.add(brand1);
//
//		SQLBatchInsert sql = new SQLBatchInsert(listCar, "brand_code","brand_details");
//		System.out.println(sql.toString());
//	}
}
