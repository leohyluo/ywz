package com.alpha.commons.core.util;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class DateUtil extends DateUtils {

    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static Integer daysBetween(Date smdate, Date bdate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(smdate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(bdate);
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / (1000 * 3600 * 24);
            return Integer.parseInt(String.valueOf(between_days));
        } catch (ParseException e) {
            logger.error("日期比较失败", e);
        }
        return null;
    }

    public static Date parseDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = sdf.parse(dateStr);
            return new Date(d.getTime());
        } catch (ParseException e) {
            logger.error("日期转换失败", e);
        }
        return null;
    }

    public static String DateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static String DateFormatString(Date date,String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static Date reduceYear() {
        int n = (int) (Math.random() * 16 + 1);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.YEAR, c.get(Calendar.YEAR) - n);
        c.set(Calendar.MONTH,(int) (Math.random() * 11 + 1));
        c.set(Calendar.DAY_OF_MONTH,(int) (Math.random() * 29 + 1));
        return new Date(c.getTimeInMillis());
    }

    public static void main(String[] args) {
        //System.out.println(DateUtil.DateFormatString(reduceYear(), "yyyy-MM-dd HH:mm:ss"));
        LocalDate localDate = LocalDate.now().plusYears(18);
        System.out.println(localDate.toString());
    }

}
