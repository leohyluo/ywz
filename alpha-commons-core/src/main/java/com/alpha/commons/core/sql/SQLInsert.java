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
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class SQLInsert {

    private static final Logger logger = LoggerFactory.getLogger(SQLInsert.class);

    private Class<?> clz;
    private Object obj;

    private String tableName;
    private Map<String, Object> fields;

    private Set<String> columnSet;

    private String pk;
    private Boolean insertWithPK = false;

    /**
     * sql插入工具对象
     *
     * @param obj 要插入数据库的对象
     */
    public SQLInsert(Object obj) {
        this.clz = obj.getClass();

        Table table = clz.getAnnotation(Table.class);
        if (table != null)
            this.tableName = table.name();
        else
            this.tableName = clz.getName().substring(clz.getName().lastIndexOf(".") + 1);
        this.obj = obj;
        initFields();
    }

    /**
     * sql插入工具对象
     *
     * @param obj     要插入数据库的对象
     * @param columns 要插入的列
     */
    public SQLInsert(Object obj, String... columns) {
        this.clz = obj.getClass();

        Table table = clz.getAnnotation(Table.class);
        if (table != null)
            this.tableName = table.name();
        else
            this.tableName = clz.getName().substring(clz.getName().lastIndexOf(".") + 1);
        this.obj = obj;
        columnSet = new LinkedHashSet<String>();
        for (int i = 0; i < columns.length; i++) {
            columnSet.add(columns[i]);
        }
        initFields();
    }

    public SQLInsert(Object obj, Boolean insertWithPK) {
        this.clz = obj.getClass();
        Table table = clz.getAnnotation(Table.class);
        if (table != null)
            this.tableName = table.name();
        else
            this.tableName = clz.getName().substring(clz.getName().lastIndexOf(".") + 1);
        this.obj = obj;
        setInsertWithPK(insertWithPK);
        initFields();
    }


    /**
     * 插入时，是否插入主键
     *
     * @param insertWithPK
     */
    private void setInsertWithPK(Boolean insertWithPK) {
        this.insertWithPK = insertWithPK;
    }

    private void initFields() {
        BeanInfo beaninfo = null;
        try {
            beaninfo = Introspector.getBeanInfo(clz);
        } catch (IntrospectionException e) {
            logger.error("SQLInser初始化，反射生成属性失败，class可能尚未被加载", e);
        }

        fields = new LinkedHashMap<String, Object>();

        PropertyDescriptor[] pds = beaninfo.getPropertyDescriptors();
        for (int i = 0; i < pds.length; i++) {
            this.clz = obj.getClass();
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
                    Annotation aId = field.getAnnotation(Id.class);
                    Column column = field.getAnnotation(Column.class);
                    if (column != null)
                        columnName = column.name();
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


            try {
                fields.put(columnName, pds[i].getReadMethod().invoke(obj));
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
        //insert into ${tablename}(${columns}) values(${values})
        StringBuffer sql = new StringBuffer("insert into ${tablename}(${columns}) values(${values})");

        int tableNameIndex = sql.indexOf("${tablename}");
        sql.replace(tableNameIndex, tableNameIndex + "${tablename}".length(), tableName);

        int columnIndex = sql.indexOf("${columns}");
        sql.replace(columnIndex, columnIndex + "${columns}".length(), getColumnSQL());

        int valuesIndex = sql.indexOf("${values}");
        sql.replace(valuesIndex, valuesIndex + "${values}".length(), getValueSQL());

        return sql.toString();

    }

    private String getColumnSQL() {
        int length = fields.size();
        int i = 0;
        StringBuffer sql = new StringBuffer();
        for (String key : fields.keySet()) {
            sql.append(key);
            if (i < length - 1) {
                sql.append(',');
            }
            i++;
        }
        return sql.toString();
    }

    public String getValueSQL() {
        int length = fields.size();
        int i = 0;
        StringBuffer sql = new StringBuffer();
        for (String key : fields.keySet()) {
            sql.append("#{");
            sql.append(key);
            sql.append("}");
            if (i < length - 1) {
                sql.append(',');
            }
            i++;
        }
        return sql.toString();
    }

    @Override
    public String toString() {
        return getInsertSQL();
    }

    public Map<String, Object> getValues() {
        Map<String, Object> temp = new LinkedHashMap<String, Object>();
        temp.putAll(fields);
        return temp;
    }

//	public static void main(String[] args) {
//		String sql = "";
//		String ids = "1,3,4";
//
//		Map<String,String> param = new HashMap<String, String>();
//
//		String[] idArr = ids.split(",");
//		for (int i = 0; i < idArr.length; i++) {
//			sql = sql + "#{";
//			sql = sql + "k"+i;
//			sql = sql + "}";
//			param.put("k"+i,idArr[i]);
//		}
//
//		param.put("sql",sql);
//
//		"update a set b=1 where c in (#{k1},#{k2},#{k3})";
//
//
//
//	}

}
