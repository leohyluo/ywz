package com.alpha.commons.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.alpha.commons.enums.Unit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {

	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String TIME_FORMAT = "HH:mm:ss";
	private static Logger logger = LoggerFactory.getLogger(DateUtils.class);
	
	/*public static float getAge(Date date) {
		LocalDate birth = dateToLocalDate(date);
		return getAge(birth);
	}*/
	
	public static float getAge(LocalDate birth) {
		LocalDate today = LocalDate.now();
		long dayDiff = ChronoUnit.DAYS.between(birth, today);
		float dayUnit = 365;
		float age = dayDiff / dayUnit;
		float result = (float)(Math.round(age*10))/10;
		return result;
	}
	
	public static float getAge(Date date) {
		LocalDate birth = dateToLocalDate(date);
		LocalDate today = LocalDate.now();
		long year = ChronoUnit.YEARS.between(birth, today);
		LocalDate birth2 = birth.plusYears(year);
		long diffDay = ChronoUnit.DAYS.between(birth2, today);
		int lengthOfYear = 365;
		if(today.isLeapYear()) {
			lengthOfYear = 366;
		}
		float result = year + ((float)diffDay / (float)lengthOfYear);
		return result;
	}
	
	public static long getMonths(Date date) {
		LocalDate birth = dateToLocalDate(date);
		LocalDate today = LocalDate.now();
		long months = ChronoUnit.MONTHS.between(birth, today);
		return months;
	}
	
	public static String getAgeText(Date date, Boolean ignoreMonth) {
		LocalDate birth = dateToLocalDate(date);
		LocalDate today = LocalDate.now();
		long year = ChronoUnit.YEARS.between(birth, today);
		LocalDate birth2 = birth.plusYears(year);
		long diffMonth = ChronoUnit.MONTHS.between(birth2, today);
		String result = "";
		if(year >= 20 && ignoreMonth == true) {
            result = year + "岁";
            return result;
        }
		if(year == 0) {
			if(diffMonth == 0) {
				long diffDay = ChronoUnit.DAYS.between(birth2, today);
				result = diffDay + "天";
			} else {
				birth2 = birth2.plusMonths(diffMonth);
				long diffDay = ChronoUnit.DAYS.between(birth2, today);
				result = diffMonth + "月";
				if(diffDay > 0) {
					result += diffDay + "天";
				}
			}
			return result;
		} else if (diffMonth == 0) {
			long diffDay = ChronoUnit.DAYS.between(birth2, today);
			result = year + "岁";
			if(diffDay > 0) {
				result += diffDay + "天";
			}
			return result;
		} else {
			result = year + "岁" + diffMonth + "月";
		}
		return result;
	}

	public static int getAgeByBirth(Date date) {
		LocalDate birthDay = dateToLocalDate(date);
		LocalDate now = LocalDate.now();
		Period period = Period.between(birthDay, now);
		// 相距多少年
		int yearsBetween = period.getYears();
		return yearsBetween;
	}

	public static float getDiffMonth(Date birth) {
		float monthUnit = 30;
		LocalDate date = dateToLocalDate(birth);
		LocalDate today = LocalDate.now();
		long dayDiff = ChronoUnit.DAYS.between(date, today);
		float month = dayDiff / monthUnit;
		float result = (float)(Math.round(month * 10)) / 10;
		System.out.println(result);
		return result;
	}

	public static long getDiffDays(Date birth) {
		LocalDate date = dateToLocalDate(birth);
		LocalDate today = LocalDate.now();
		long dayDiff = ChronoUnit.DAYS.between(date, today);
		return dayDiff;
	}
	
	public static LocalDate dateToLocalDate(Date date) {
		LocalDateTime localDateTime = dateToLocalDateTime(date);
		LocalDate localDate = localDateTime.toLocalDate();
		return localDate;
	}
	
	public static LocalDateTime dateToLocalDateTime(Date date) {
		if(date==null)
			return  LocalDateTime.now();
		Instant instant = date.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
		return localDateTime;
	}


	public static Date dayDiffence(ChronoUnit unit, long value) {
		LocalDate today = LocalDate.now();
		LocalDate result = LocalDate.now();
		if (unit == ChronoUnit.DAYS) {
			result = today.plusDays(value);
		} else if (unit == ChronoUnit.MONTHS) {
			result = today.plusMonths(value);
		} else if (unit == ChronoUnit.YEARS) {
			result = today.plusYears(value);
		}
		return localDate2Date(result);
	}
	
	public static Date stringToDate(String dateStr) {
		if(dateStr.contains(" "))
			dateStr = dateStr.split(" ")[0];
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
		LocalDate localDate = LocalDate.parse(dateStr, formatter);
		ZoneId zoneId = ZoneId.systemDefault();
		ZonedDateTime zoneDateTime = localDate.atStartOfDay(zoneId);
		Date date = Date.from(zoneDateTime.toInstant());
		return date;
	}

	public static LocalDate string2LocalDate(String dateStr) {
		if(dateStr.contains(" "))
			dateStr = dateStr.split(" ")[0];
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
		LocalDate localDate = LocalDate.parse(dateStr, formatter);
		return localDate;
	}

	public static Date string2Date(String dateStr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Date date = sdf.parse(dateStr);
		return date;
	}
	
	public static String date2String(Date date, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		String result = "";
		if(pattern.equals(DATE_FORMAT)) {
			LocalDate datetime = dateToLocalDate(date);
			result = datetime.format(formatter);
		} else {
			LocalDateTime datetime = dateToLocalDateTime(date);
			result = datetime.format(formatter);
		}
		return result;
	}
	
	public static Date string2DateTime(String dateStr) throws ParseException {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
			date = sdf.parse(dateStr);
		} catch (Exception e) {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			date = sdf.parse(dateStr);
		}
		return date;
	}

	public static List<Integer> listMonth(int minValue,int maxValue){
		List<Integer> result = new ArrayList<>();
		int thisValue = minValue;
		while (thisValue != maxValue) {
			//当前值等于最大月份 设置为最小月份
			if (thisValue > 12)
				thisValue = 1;
			result.add(thisValue++);
		}
		result.add(maxValue);
		return result;
	}

	public static Date localDate2Date (LocalDate localDate) {
		ZoneId zoneId = ZoneId.systemDefault();
		ZonedDateTime zdt = localDate.atStartOfDay(zoneId);

		Date date = Date.from(zdt.toInstant());
		return  date;
	}
	
	public static Double toMillSeond(Double val, Unit unit) {
		double result = 0;
		
		if(unit == Unit.MINUTE) {
			result = val * 60 * 1000;
			return result;
		}
		if(unit == Unit.HOUR) {
			result = val * 60 * 60 * 1000;
			return result;		
		}
		if(unit == Unit.DAY) {
			result = val * 24 * 60 * 60 * 1000;
			return result;
		} 
		if(unit == Unit.WEEK) {
			result = val * 7 * 24 * 60 * 60 * 1000;
			return result;
		}
		if(unit == Unit.MONTH) {
			result = val * 30 * 24 * 60 * 60 * 1000;
			return result;
		}
		if(unit == Unit.YEAR) {
			result = val * 365 * 24 * 60 * 60 * 1000;
			return result;
		}
		return result;
	}

	public static Double toMillSeond(String input) {
		//1年3月2天10小时
		int indexOfYear = input.indexOf(Unit.YEAR.getText());
		Double millSecondOfYear = 0.0;
		if(indexOfYear > 0) {
			String yearStr = input.substring(0, indexOfYear);
			input = input.substring(indexOfYear + 1);
			millSecondOfYear = toMillSeond(Double.valueOf(yearStr), Unit.YEAR);
		}
		int indexOfMonth = input.indexOf(Unit.MONTH.getText());
		Double millSecondOfMonth = 0.0;
		if(indexOfMonth > 0) {
			String monthStr = input.substring(0, indexOfMonth);
			input = input.substring(indexOfMonth + 1);
			millSecondOfMonth = toMillSeond(Double.valueOf(monthStr), Unit.MONTH);
		}
		int indexOfDay = input.indexOf(Unit.DAY.getText());
		Double millSecondOfDay = 0.0;
		if(indexOfDay > 0) {
			String dayStr = input.substring(0, indexOfDay);
			input = input.substring(indexOfDay + 1);
			millSecondOfDay = toMillSeond(Double.valueOf(dayStr), Unit.DAY);
		}
		int indexOfHour = input.indexOf(Unit.HOUR.getText());
		Double millSecondOfHour = 0.0;
		if(indexOfHour > 0) {
			String hourStr = input.substring(0, indexOfHour);
			millSecondOfHour = toMillSeond(Double.valueOf(hourStr), Unit.HOUR);
		}
		Double result = millSecondOfYear + millSecondOfMonth + millSecondOfDay + millSecondOfHour;
		return result;
	}
	
	public static Double millSecond2Day(Double val) {
		double millSecondOfOneDay = 1 * 24 * 60 * 60 * 1000;
		double day = val / millSecondOfOneDay;
		double day2 = val % millSecondOfOneDay;
		double result = day + day2;
		return result;
	}

	public static String addOneDay(String str){
		try {
			if(null == str){
				return null;
			}
			Date date= string2Date(str);
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			Date tomorrow = calendar.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			return  sdf.format(tomorrow);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 返回生日天数前后5天（年月不变）
	 * @param date
	 * @return
	 */
	public static String getBirthDayRange(Date date, List<Integer> dayRange) {
		int diffDay = 5;
		LocalDate localDate = dateToLocalDate(date);
		int year = localDate.getYear();
		int month = localDate.getMonthValue();
		int dayOfBirth = localDate.getDayOfMonth();

		YearMonth yearMonth = YearMonth.of(year, month);
		LocalDate lastDayOfYearMonth = yearMonth.atEndOfMonth();

		int firstDayOfMonth = 1;
		int lastDayOfMonth = lastDayOfYearMonth.getDayOfMonth();
		int minDay = firstDayOfMonth;
		int maxDay = 0;
		if(dayOfBirth - diffDay <= 0) {
			maxDay = 10;
		} else if (lastDayOfMonth - diffDay <= dayOfBirth) {
			minDay = lastDayOfMonth - 10;
			maxDay = lastDayOfMonth;
		} else {
			minDay = dayOfBirth - 5;
			maxDay = dayOfBirth + 5;
		}
		System.out.println(minDay+ "-" + maxDay);
		for(int i = minDay; i <= maxDay; i++) {
			dayRange.add(i);
		}
		int randomNum = StringUtils.getRandomNumber(9);
		int randomDay = dayRange.get(randomNum);
		LocalDate randomDate = LocalDate.of(year, month, randomDay);
		String result = randomDate.toString();
		return result;
	}

	public static void main(String[] args) {
		String str = "2001-09-01";
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String result = getAgeText(date, false);
		System.out.println(result);
	}

	public static void printMonitorLog(long startTime, String msg) {
		long endTime = System.currentTimeMillis();
		String log = msg.concat("共耗时{}ms");
		long costTime = endTime - startTime;
		logger.info(log, costTime);
	}
}
