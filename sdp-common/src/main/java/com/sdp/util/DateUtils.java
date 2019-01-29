package com.sdp.util;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import java.util.Date;

/**
 * <p>
 * Title: 系统时间公共操作方法类
 * </p>
 * <li>提供取得系统时间的所有共用方法</li>
 */
public class DateUtils {
    /**
     * from Joda DateTime to JDK Date
     * @param dt
     * @return
     */
    private static Date jodaToJdk(DateTime dt) {
        return dt.toDate();
    }

    /**
     * from JDK Date to Joda DateTime
     * @param date
     * @return
     */
    private static DateTime jdkToJoda(Date date) {
        return new DateTime(date);
    }

    /**
     * 取得当前时间
     * @return 当前日期
     */
    public static Date getCurrentDate() {
        DateTime dt1 = new DateTime();
        return jodaToJdk(dt1);
    }

    /**
     * 获取指定日期之后N天的时间
     * @param date
     * @param n
     * @return N天之后的日期
     */
    public static Date getAfterDaysDate(Date date, Integer n) {
        DateTime dt1 = jdkToJoda(date);
        return jodaToJdk(dt1.plusDays(n));
    }

    /**
     * 获取指定日期之前N天的时间
     * @param date
     * @param n
     * @return N天之前的日期
     */
    public static Date getBeforeDaysDate(Date date, Integer n) {
        DateTime dt1 = jdkToJoda(date);
        return jodaToJdk(dt1.minusDays(n));
    }

    /**
     * 获取指定日期之后的N月的时间
     * @param date 起始日期
     * @param n 月数
     * @return N月之后的日期
     */
    public static Date getAfterMonthDate(Date date, Integer n) {
        DateTime dt1 = jdkToJoda(date);
        return jodaToJdk(dt1.plusMonths(n));
    }

    /**
     * 取得当前时间的长整型表示
     * @return 当前时间（long）
     */
    public static long getCurTimeMillis() {
        DateTime dt1 = new DateTime();
        return dt1.getMillis();
    }

    /**
     * 取得某日期时间的特定表示格式的字符串
     * @param format
     *            时间格式
     * @param date
     *            某日期
     */
    public static synchronized String getDate2Str(String format, Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(format);
    }

    /**
     * 将某长整型数据转换为日期
     * @param l
     *            长整型数
     * @return 转换后的日期
     */
    public static Date getLongToDate(long l) {
        DateTime dateTime = new DateTime(l);
        return dateTime.toDate();
    }

    /**
     * 计算from和to时间相隔的计算区间毫秒数
     * @return
     */
    public static Long getOffMills(Date from, Date to) {
        DateTime dtFrom = new DateTime(from);
        DateTime dtTo = new DateTime(to);
        Duration d = new Duration(dtFrom, dtTo);
        return d.getMillis();
    }

    /**
     * 获取两个日期相差的分钟数
     */
    public static Integer getOffMinute(Date startDate, Date endDate) {
        DateTime dtFrom = new DateTime(startDate);
        DateTime dtTo = new DateTime(endDate);

        Period p = new Period(dtFrom, dtTo, PeriodType.minutes());
        return p.getMinutes();
    }

    /**
     * 获取两个日期相差的天数(仅比较yyyy-MM-dd部分)
     * @param endDate
     * @param startDate
     * @return
     */
    public static Integer getDateDiffDay(Date startDate, Date endDate) {
        DateTime dtFrom = new DateTime(startDate);
        DateTime dtTo = new DateTime(endDate);

        Period p = new Period(dtFrom, dtTo, PeriodType.days());
        return p.getDays();
    }
    
    public static void main(String[] args) {
    	StringBuilder sBuilder = new StringBuilder();
		if(StringUtils.isBlank(sBuilder.toString())){
			System.out.println("11");
		}
    	int a = DateUtils.getOffMinute(new Date(), new Date());
    	System.out.println(a);
	}
}
