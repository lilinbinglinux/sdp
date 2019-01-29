package com.sdp.sqlModel.function;

import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DateStyle
    implements Serializable
{

    private String year;
    private String month;
    private String date;
    private String dateString;
    private String errorCode;
    public static String lastYear = "2050";
    public static String firstYear = "1900";
    public static String firstMonth = "01";
    public static String lastMonth = "12";
    public static String separator = "-";
    public ArrayList doubleMonth;
    public ArrayList singleMonth;
    public ArrayList specialMonth;
    private String minute;
    private String hour;
    private String second;

    public DateStyle()
    {
        doubleMonth = new ArrayList();
        singleMonth = new ArrayList();
        specialMonth = new ArrayList();
        doubleMonth.add("01");
        doubleMonth.add("03");
        doubleMonth.add("05");
        doubleMonth.add("07");
        doubleMonth.add("08");
        doubleMonth.add("10");
        doubleMonth.add("12");
        singleMonth.add("04");
        singleMonth.add("06");
        singleMonth.add("09");
        singleMonth.add("11");
        specialMonth.add("02");
    }

    public String getYear()
    {
        return year;
    }

    public void setYear(String year)
    {
        this.year = year;
    }

    public void setMonth(String month)
    {
        if(month.length() == 1)
            month = "0" + month;
        this.month = month;
    }

    public String getMonth()
    {
        return month;
    }

    public void setDate(String date)
    {
        if(date.length() == 1)
            date = "0" + date;
        this.date = date;
    }

    public String getDate()
    {
        return date;
    }

    public void setDateString(String dateString)
    {
        this.dateString = dateString;
        parseDateString();
    }

    public void setDateString_noseparator(String dateString)
    {
        this.dateString = dateString;
        parseDateString_noseparator();
    }

    protected void parseDateString_noseparator()
    {
        if(dateString != null)
        {
            if(dateString.length() >= 4)
                year = dateString.substring(0, 4);
            if(dateString.length() >= 6)
                month = dateString.substring(4, 6);
            if(dateString.length() >= 8)
                date = dateString.substring(6, 8);
        }
    }

    public String getDateString_noseparator()
    {
        String res = "";
        if(year != null && !year.equals(""))
        {
            res = res + year;
            if(month != null && !month.equals(""))
            {
                res = res + month;
                if(date != null && !date.equals(""))
                    res = res + date;
            }
        }
        return res;
    }

    public String getDataStringToMonth_noseparator()
    {
        String res = "";
        if(year != null && !year.equals(""))
        {
            res = res + year;
            if(month != null && !month.equals(""))
                res = res + month;
        }
        return res;
    }

    public Date getSqlDate()
    {
        Calendar c = Calendar.getInstance();
        c.set(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(date));
        return new Date(c.getTime().getTime());
    }

    public String getDataStringToDate_noseparator()
    {
        String res = "";
        if(year != null && !year.equals(""))
        {
            res = res + year;
            if(month != null && !month.equals(""))
            {
                res = res + month;
                if(date != null && !date.equals(""))
                    res = res + date;
            }
        }
        return res;
    }

    public String getDateString()
    {
        String res = "";
        if(year != null && !year.equals(""))
        {
            res = res + year;
            if(month != null && !month.equals(""))
            {
                res = res + separator + month;
                if(date != null && !date.equals(""))
                    res = res + separator + date;
            }
        }
        return res;
    }

    public String getDataStringToYear()
    {
        if(year != null && !year.equals(""))
            return year;
        else
            return "";
    }

    public String getDataStringToMonth()
    {
        String res = "";
        if(year != null && !year.equals(""))
        {
            res = res + year;
            if(month != null && !month.equals(""))
                res = res + separator + month;
        }
        return res;
    }

    public String getDataStringToDate()
    {
        String res = "";
        if(year != null && !year.equals(""))
        {
            res = res + year;
            if(month != null && !month.equals(""))
            {
                res = res + separator + month;
                if(date != null && !date.equals(""))
                    res = res + separator + date;
            }
        }
        return res;
    }

    public String getDataStringToSecond()
    {
        String res = "";
        if(year != null && !year.equals(""))
        {
            res = res + year;
            if(month != null && !month.equals(""))
            {
                res = res + separator + month;
                if(date != null && !date.equals(""))
                {
                    res = res + separator + date;
                    if(hour != null && !hour.equals(""))
                    {
                        res = res + " " + hour;
                        if(minute != null && !minute.equals(""))
                        {
                            res = res + ":" + minute;
                            if(second != null && !second.equals(""))
                                res = res + ":" + second;
                        }
                    }
                }
            }
        }
        return res;
    }

    public String getDataStringToMinute()
    {
        String res = "";
        if(year != null && !year.equals(""))
        {
            res = res + year;
            if(month != null && !month.equals(""))
            {
                res = res + separator + month;
                if(date != null && !date.equals(""))
                {
                    res = res + separator + date;
                    if(hour != null && !hour.equals(""))
                    {
                        res = res + " " + hour;
                        if(minute != null && !minute.equals(""))
                            res = res + ":" + minute;
                    }
                }
            }
        }
        return res;
    }

    public String getDataStringToHour()
    {
        String res = "";
        if(year != null && !year.equals(""))
        {
            res = res + year;
            if(month != null && !month.equals(""))
            {
                res = res + separator + month;
                if(date != null && !date.equals(""))
                {
                    res = res + separator + date;
                    if(hour != null && !hour.equals(""))
                        res = res + " " + hour;
                }
            }
        }
        return res;
    }

    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }

    public String getErrorCode()
    {
        return errorCode;
    }

    public boolean checkContent(int i)
        throws Exception
    {
        if(i == 0)
            return checkYear() && checkMonth() && checkDate();
        if(i == 1)
            return checkYear() && checkMonth();
        if(i == 2)
            return checkYear();
        else
            return false;
    }

    public boolean checkContentYear()
        throws Exception
    {
        try
        {
            return checkContent(2);
        }
        catch(Exception e)
        {
            year = "";
            throw e;
        }
    }

    public boolean checkContentMonth()
        throws Exception
    {
        try
        {
            return checkContent(1);
        }
        catch(Exception e)
        {
            year = "";
            month = "";
            throw e;
        }
    }

    public boolean checkContentDate()
        throws Exception
    {
        try
        {
            return checkContent(0);
        }
        catch(Exception e)
        {
            year = "";
            month = "";
            date = "";
            throw e;
        }
    }

    public boolean checkContentDateNoneDate()
        throws Exception
    {
        try
        {
            return checkContent(1);
        }
        catch(Exception e)
        {
            year = "";
            month = "";
            throw e;
        }
    }

    protected boolean checkYear()
        throws Exception
    {
        if(year == null || year.trim().equals(""))
        {
            errorCode = "DateStyle.yearNotNull";
            throw new Exception(errorCode);
        }
        try
        {
            Integer.parseInt(year);
        }
        catch(Exception e)
        {
            errorCode = "DateStyle.yearNoNumber";
            throw new Exception(errorCode);
        }
        if(year.length() != 4)
        {
            errorCode = "DateStyle.yearLengthNotRight";
            throw new Exception(errorCode);
        }
        if(year.compareTo(lastYear) > 0 || year.compareTo(firstYear) < 0)
        {
            errorCode = "DateStyle.yearOutOfFlow";
            throw new Exception(errorCode);
        } else
        {
            return true;
        }
    }

    protected boolean checkMonth()
        throws Exception
    {
        if(month == null || month.trim().equals(""))
        {
            errorCode = "DateStyle.monthNotNull";
            throw new Exception(errorCode);
        }
        try
        {
            Integer.parseInt(month);
        }
        catch(Exception e)
        {
            errorCode = "DateStyle.monthNoNumber";
            throw new Exception(errorCode);
        }
        if(month.length() > 2 || month.length() == 0)
        {
            errorCode = "DateStyle.monthLengthNotRight";
            throw new Exception(errorCode);
        }
        if(month.length() == 1)
            month = "0" + month;
        if(month.compareTo(lastMonth) > 0 || month.compareTo(firstMonth) < 0)
        {
            errorCode = "DateStyle.monthOutOfFlow";
            throw new Exception(errorCode);
        } else
        {
            return true;
        }
    }

    protected boolean checkDate()
        throws Exception
    {
        if(date == null || date.trim().equals(""))
        {
            errorCode = "DateStyle.dateNotNull";
            throw new Exception(errorCode);
        }
        try
        {
            Integer.parseInt(date);
        }
        catch(Exception e)
        {
            errorCode = "DateStyle.dateNoNumber";
            return false;
        }
        if(date.length() > 2 || date.length() == 0)
        {
            errorCode = "DateStyle.dateLengthNotRight";
            throw new Exception(errorCode);
        }
        if(date.length() == 1)
            date = "0" + date;
        if(date.compareTo("01") < 0)
        {
            errorCode = "DateStyle.dateOutOfFlow";
            throw new Exception(errorCode);
        }
        if(Integer.parseInt(date) > getMonthDays())
        {
            errorCode = "DateStyle.dateOutOfFlow";
            throw new Exception(errorCode);
        } else
        {
            return true;
        }
    }

    protected void parseDateString()
    {
        if(dateString != null)
        {
            if(dateString.length() >= 4)
                year = dateString.substring(0, 4);
            if(dateString.length() >= 7)
                month = dateString.substring(5, 7);
            if(dateString.length() >= 10)
                date = dateString.substring(8, 10);
        }
    }
/*
    public static void main(String a[])
    {
        DateStyle test = new DateStyle();
        test.setYear("1998");
        test.setMonth("11");
        test.setDate("1");
    }*/

    public static String dateformat(java.util.Date thedate, String format)
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat simple = new SimpleDateFormat(format);
        return simple.format(thedate);
    }

    public static DateStyle exchangeDateFormat(java.util.Date thedate)
    {
        Calendar c = Calendar.getInstance();
        DateStyle res = new DateStyle();
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        res.setDateString(simple.format(thedate));
        return res;
    }

    public static java.util.Date parseDate(String strdate)
    {
        java.util.Date date = null;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            date = simple.parse(strdate);
        }
        catch(ParseException pex) { }
        return date;
    }

    public static DateStyle getSystemDate()
    {
        Calendar c = Calendar.getInstance();
        java.util.Date date = c.getTime();
        DateStyle res = new DateStyle();
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        res.setDateString(simple.format(date));
        return res;
    }

    public static String getSystemTime()
    {
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simple.format(new java.util.Date());
    }

    public void setMinute(String minute)
    {
        this.minute = minute;
    }

    public String getMinute()
    {
        return minute;
    }

    public void setHour(String hour)
    {
        this.hour = hour;
    }

    public String getHour()
    {
        return hour;
    }

    public void setSecond(String second)
    {
        this.second = second;
    }

    public String getSecond()
    {
        return second;
    }

    public int compare(DateStyle obj)
    {
        String temp1 = getDateString();
        String temp2 = obj.getDateString();
        return temp1.compareTo(temp2);
    }

    public void decreaseMonth(int i)
    {
        int oldMonth = Integer.parseInt(month);
        int oldYear = Integer.parseInt(year);
        for(; oldMonth - i <= 0; i -= 12)
            oldYear--;

        month = Integer.toString(oldMonth - i);
        if(month.length() == 1)
            month = "0" + month;
        year = Integer.toString(oldYear);
        if(date != null && !date.equals(""))
        {
            int oldDate = Integer.parseInt(date);
            if(oldDate > getMonthDays())
                setDate(Integer.toString(getMonthDays()));
        }
    }

    public void increaseMonth(int i)
    {
        int oldMonth = Integer.parseInt(month);
        int oldYear = Integer.parseInt(year);
        for(; oldMonth + i > 12; i -= 12)
            oldYear++;

        month = Integer.toString(oldMonth + i);
        if(month.length() == 1)
            month = "0" + month;
        year = Integer.toString(oldYear);
        if(date != null && date.equals(""))
        {
            int oldDate = Integer.parseInt(date);
            if(oldDate > getMonthDays())
                oldDate = getMonthDays();
        }
    }

    public void decreaseDays(int i)
    {
        int oldDay;
        for(oldDay = Integer.parseInt(date); oldDay - i <= 0; i -= getMonthDays())
        {
            decreaseMonth(1);
            System.out.println("getMonthDays=" + getMonthDays() + "month=" + month);
        }

        date = Integer.toString(oldDay - i);
        if(date.length() == 1)
            date = "0" + date;
    }

    public void increaseDays(int i)
    {
        int oldDay;
        for(oldDay = Integer.parseInt(date); oldDay + i > getMonthDays(); i -= getMonthDays())
            increaseMonth(1);

        date = Integer.toString(oldDay + i);
        if(date.length() == 1)
            date = "0" + date;
    }

    private int getMonthDays()
    {
        if(doubleMonth.contains(month))
            return 31;
        if(singleMonth.contains(month))
            return 30;
        if(specialMonth.contains(month))
        {
            boolean leapYear = false;
            int iYear = Integer.parseInt(year);
            if(iYear % 400 == 0)
                leapYear = true;
            else
            if(iYear % 100 == 0)
                leapYear = false;
            else
            if(iYear % 4 == 0)
                leapYear = true;
            else
                leapYear = false;
            return !leapYear ? 28 : 29;
        } else
        {
            return 30;
        }
    }

    public static DateStyle ConvertString(String table)
    {
        DateStyle returnString = new DateStyle();
        if(table.length() <= 10)
        {
            String Year;
            String Month;
            String Day;
            if(table == null || table.trim().equals(""))
            {
                Year = "";
                Month = "";
                Day = "";
            } else
            {
                int length = table.length();
                int Index = table.indexOf("-");
                if(Index == -1)
                {
                    Year = table;
                    Month = "";
                    Day = "";
                } else
                {
                    Year = table.substring(0, Index);
                    String temp = table.substring(Index + 1, length);
                    int Index2 = temp.indexOf("-");
                    if(Index2 == -1)
                    {
                        Month = table.substring(Index + 1, length);
                        Day = "";
                    } else
                    {
                        Month = temp.substring(0, Index2);
                        Day = temp.substring(Index2 + 1, temp.length());
                    }
                }
            }
            returnString.setYear(Year);
            returnString.setMonth(Month);
            returnString.setDate(Day);
            return returnString;
        } else
        {
            int length = table.length();
            int Index = table.indexOf("-");
            String Year = table.substring(0, Index);
            String temp = table.substring(Index + 1, length);
            int Index2 = temp.indexOf("-");
            String Month = temp.substring(0, Index2);
            String temp1 = temp.substring(Index2 + 1, temp.length());
            int Index3 = temp1.indexOf(" ");
            String Day = temp1.substring(0, Index3);
            String temp2 = temp1.substring(Index3 + 1, temp1.length());
            int Index4 = temp2.indexOf(":");
            String Hour = temp2.substring(0, Index4);
            String temp3 = temp2.substring(Index4 + 1, temp2.length());
            int Index5 = temp3.indexOf(":");
            String Minute = temp3.substring(0, Index5);
            String Second = temp3.substring(Index5 + 1, temp3.length());
            returnString.setYear(Year);
            returnString.setMonth(Month);
            returnString.setDate(Day);
            returnString.setHour(Hour);
            returnString.setMinute(Minute);
            returnString.setSecond(Second);
            return returnString;
        }
    }

}
