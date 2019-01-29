package com.sdp.cop.octopus.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author ke_wang
 * @version 2016年6月16日
 * @see DateUtils
 * @since
 */
public class DateUtils {
	
    /**
     * pattern
     */
    private static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	
    /**
     * 取得当前时间
     * @see Date
     * @see Calender
     * @return Date 当前日期
     */
    public static Date getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
	
    /**
     * 
     * Description: <br>
     * Date转String
     * @param date Date
     * @param pattern String
     * @return String
     * @see
     */
    public static String formatDateToString(Date date,String pattern){
        SimpleDateFormat dateFormater = new SimpleDateFormat(pattern);
        return dateFormater.format(date);
    }
	
    /**
     * 
     * Description:
     * String转Date
     * @param date String
     * @return Date 
     * @see
     */
    public static Date formatStringToDate(String date){
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        try {
            return sdf.parse(date);
        }
        catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 
     * Description:
     * Date计算
     * @param date Date
     * @param type String
     * @param amount int
     * @return  Date 
     * @see
     */
    public static Date dateCompute(Date date, String type,  int amount){
        GregorianCalendar cal = new GregorianCalendar();  
        cal.setTime(date);  
        int field = 0;
        switch (type) {
            case "year":
                field = 1;
                break;
            case "month":
                field = 2;
                break;
            case "week":
                field = 3;
                break;
            case "day":
                field = 5;
                break;
            case "hour":
                field = 10;
                break;
            case "minute":
                field = 12;
                break;
            case "second":
                field = 13;
                break;
            default:
                field = 0;
        }
        cal.add(field, amount);
        return cal.getTime();
    }
}
