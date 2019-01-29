package com.sdp.sqlModel.function;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DateUtils
{
	private static final Logger log = LoggerFactory.getLogger(DateUtils.class);

    public DateUtils(){
    
    }

    public static GregorianCalendar getCalendar(java.util.Date date)
    {
    	
        int year = getYear(date);
        int month = getMonth(date);
        int day = getDay(date);
        int hour = getHour(date);
        int minute = getMinute(date);
        return new GregorianCalendar(year, intToCalendarMonth(month), day, hour, minute);
    }

    public static final java.util.Date getDate(int year, int month, int day, int hour, int minute)
    {
        Calendar cal = new GregorianCalendar(year, intToCalendarMonth(month), day, hour, minute);
        return cal.getTime();
    }

    public static final java.util.Date getDate(int year, int month, int day, int hour, int minute, int second)
    {
        Calendar cal = new GregorianCalendar(year, intToCalendarMonth(month), day, hour, minute, second);
        return cal.getTime();
    }

    public static final java.util.Date getDate(int year, int month, int day)
    {
        Calendar cal = new GregorianCalendar(year, intToCalendarMonth(month), day);
        return cal.getTime();
    }

    public static final java.util.Date getDate(String date, String pattern)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        java.util.Date t = null;
        try
        {
            t = formatter.parse(date);
        }
        catch(ParseException pee)
        {
        	log.info("context", pee);
        }
        return t;
    }

    public static Timestamp getTimestamp(java.util.Date date)
    {
        return new Timestamp(date.getTime());
    }

    public static Date getSqlDate(java.util.Date date)
    {
        return new Date(date.getTime());
    }

    public static Timestamp getTimestamp(Calendar date)
    {
        return new Timestamp(date.getTime().getTime());
    }

    public static Date getSqlDate(Calendar date)
    {
        return new Date(date.getTime().getTime());
    }

    public static Timestamp getTimestamp(String date, String pattern)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        try
        {
            return new Timestamp(formatter.parse(date).getTime());
        }
        catch(Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static Date getSqlDate(String date, String pattern)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        try
        {
            return new Date(formatter.parse(date).getTime());
        }
        catch(Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static final java.util.Date addDays(java.util.Date target, int days)
    {
        GregorianCalendar gc = getCalendar(target);
        gc.add(5, days);
        return gc.getTime();
    }

    public static final java.util.Date addMonths(java.util.Date target, int months)
    {
        GregorianCalendar gc = getCalendar(target);
        gc.add(2, months);
        return gc.getTime();
    }

    public static final java.util.Date addWeeks(java.util.Date target, int weeks)
    {
        GregorianCalendar gc = getCalendar(target);
        gc.add(4, weeks);
        return gc.getTime();
    }

    public static int hourDiff(java.util.Date first, java.util.Date second)
    {
        long msPerHour = 0x36ee80L;
        long diff = (second.getTime() - first.getTime()) / msPerHour;
        Long convertLong = new Long(diff);
        return convertLong.intValue();
    }

    public static int dayDiff(java.util.Date first, java.util.Date second)
    {
        long msPerDay = 0x5265c00L;
        long diff = (second.getTime() - first.getTime()) / msPerDay;
        Long convertLong = new Long(diff);
        return convertLong.intValue();
    }

    public static int monthDiff(java.util.Date first, java.util.Date second)
    {
        GregorianCalendar firstcal = getCalendar(first);
        GregorianCalendar secondcal = getCalendar(second);
        int elapsed = 0;
        GregorianCalendar gc1;
        GregorianCalendar gc2;
        if(second.after(first))
        {
            gc2 = (GregorianCalendar)second.clone();
            gc1 = (GregorianCalendar)first.clone();
        } else
        {
            gc2 = (GregorianCalendar)first.clone();
            gc1 = (GregorianCalendar)second.clone();
        }
        gc1.clear(14);
        gc1.clear(13);
        gc1.clear(12);
        gc1.clear(11);
        gc1.clear(5);
        gc2.clear(14);
        gc2.clear(13);
        gc2.clear(12);
        gc2.clear(11);
        gc2.clear(5);
        while(gc1.before(gc2)) 
        {
            gc1.add(2, 1);
            elapsed++;
        }
        if(first.before(second))
            return elapsed;
        else
            return -elapsed;
    }

    public static double yearDiff(java.util.Date first, java.util.Date second)
    {
        int days = dayDiff(first, second);
        if(first.before(second))
            return (double)(days / 365);
        else
            return (double)(-days / 365);
    }

    public static int getYear(java.util.Date date)
    {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(1);
    }

    public static int getMonth(java.util.Date date)
    {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        int calendarMonth = cal.get(2);
        return calendarMonthToInt(calendarMonth);
    }

    public static int getDay(java.util.Date date)
    {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(5);
    }

    public static int getHour(java.util.Date date)
    {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(11);
    }

    public static int getMinute(java.util.Date date)
    {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(12);
    }

    private static int calendarMonthToInt(int calendarMonth)
    {
        switch(calendarMonth)
        {
        case 0: // '\0'
            return 1;

        case 1: // '\001'
            return 2;

        case 2: // '\002'
            return 3;

        case 3: // '\003'
            return 4;

        case 4: // '\004'
            return 5;

        case 5: // '\005'
            return 6;

        case 6: // '\006'
            return 7;

        case 7: // '\007'
            return 8;

        case 8: // '\b'
            return 9;

        case 9: // '\t'
            return 10;

        case 10: // '\n'
            return 11;

        case 11: // '\013'
            return 12;
        }
        return 1;
    }

    public static String format(java.util.Date date, String pattern)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

    public static String FormatDate(java.util.Date date, String pattern)
    {
        if(date == null)
        {
            return "";
        } else
        {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(date);
        }
    }

    public static String FormatDate(Date date)
    {
        if(date == null)
            return "";
        try
        {
            return FormatDate(((java.util.Date) (date)), "yyyy-MM-dd");
        }
        catch(Exception ex)
        {
            return "";
        }
    }

    private static int intToCalendarMonth(int month)
    {
        switch(month)
        {
        case 1: // '\001'
            return 0;

        case 2: // '\002'
            return 1;

        case 3: // '\003'
            return 2;

        case 4: // '\004'
            return 3;

        case 5: // '\005'
            return 4;

        case 6: // '\006'
            return 5;

        case 7: // '\007'
            return 6;

        case 8: // '\b'
            return 7;

        case 9: // '\t'
            return 8;

        case 10: // '\n'
            return 9;

        case 11: // '\013'
            return 10;

        case 12: // '\f'
            return 11;
        }
        return 0;
    }
}
