package com.alpha.commons.core.sql.dto;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.Alias;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * 用于映射数据行的POJO类，以map的形式存在。
 * 并用于传递查询参数。
 */
@Alias("DataRecord")
public class DataRecord extends LinkedHashMap<String, Object> implements DTO {
    private static final Logger logger = LoggerFactory.getLogger(DataRecord.class);

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DataRecord() {
    }

    public DataRecord(Object... args) {
        put(args);
    }

    public void put(Object... args) {
        for (int i = 1; i < args.length; i += 2) {
            put(String.valueOf(args[i - 1]), args[i]);
        }
    }

    public Object get(String key) {
        if (key == null) {
            return super.get(null);
        }
        return super.get(((String) key));
    }

    public String getString(String key) {
        try {
            Object temp = get(key);
            if (null != temp) {
                return temp.toString();
            } else {
                return null;
            }
            //return (String)(get(key));
        } catch (ClassCastException e) {
            logger.warn("转换数据异常。key=" + key + ", value=" + get(key), e);
            return null;
        }
    }

    public Long getLong(String key) {
        try {
            Object temp = get(key);
            if (temp == null) {
                return null;
            } else if (temp instanceof BigDecimal) {
                BigDecimal val = ((BigDecimal) (get(key)));
                return val == null ? null : val.longValue();
            } else if (temp instanceof Long) {
                return (Long) temp;
            } else {
                return Long.parseLong(temp.toString());
            }
        } catch (Exception e) {
            logger.warn("转换数据异常。key=" + key + ", value=" + get(key), e);
            return null;
        }
    }

    public Integer getInteger(String key) {
        try {
            BigDecimal val = ((BigDecimal) (get(key)));
            return val == null ? null : val.intValue();
        } catch (Exception e) {
            logger.warn("转换数据异常。key=" + key + ", value=" + get(key), e);
            return null;
        }
    }

    public Double getDouble(String key) {
        try {
            BigDecimal val = ((BigDecimal) (get(key)));
            return val == null ? null : val.doubleValue();
        } catch (Exception e) {
            logger.warn("转换数据异常。key=" + key + ", value=" + get(key), e);
            return null;
        }
    }

    public Date getDate(String key) {
        try {
            return (Date) (get(key));
        } catch (ClassCastException e) {
            logger.warn("转换数据异常。key=" + key + ", value=" + get(key), e);
            return null;
        }
    }

    public String getDateString(String key, String format) {
        Date date = getDate(key);
        ;
        if (date == null) {
            return null;
        }
        if (StringUtils.isEmpty(format)) {
            getDateString(key);
        }
        try {
            DateFormat df = new SimpleDateFormat(format);
            return df.format(date);
        } catch (Exception e) {
            logger.warn("转换数据异常。key=" + key + ", value=" + get(key), e);
            return null;
        }
    }

    public String getDateString(String key) {
        return getDateString(key, "yyyy-MM-dd hh:mm:ss");
    }

    public Object put(String key, Object value) {
        return super.put(key, value);
    }
}
