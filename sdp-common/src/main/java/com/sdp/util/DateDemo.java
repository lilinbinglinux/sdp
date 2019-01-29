package com.sdp.util;

import java.text.ParseException; 
import java.text.SimpleDateFormat; 
import java.util.Calendar; 
import java.util.Date;
import java.util.HashMap;
import java.util.Map; 
public class DateDemo { 
  public static void main(String args[]) { 
    System.out.println("---------------获取当前时间的年月日-----------------"); 
    getMonthDay2(); 
    System.out.println("---------------获取自定义时间的年月日-----------------"); 
    getMonthDay2Set(); 
  } 
  /** 
   * 获取自定义时间的年月日 
   */
  public static void getMonthDay2Set() { 
    String dateStr = "2013-11-10 18:45:39"; 
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
    Calendar cal = Calendar.getInstance(); 
    Date dt = null; 
    try { 
      dt = sdf.parse(dateStr); 
    } catch (ParseException e) { 
      // TODO Auto-generated catch block 
      e.printStackTrace(); 
    } 
    cal.setTime(dt); 
    int year = cal.get(Calendar.YEAR); 
    int month = cal.get(Calendar.MONTH) + 1; 
    int day = cal.get(Calendar.DAY_OF_MONTH); 
    int hour = cal.get(Calendar.HOUR_OF_DAY); 
    int minute = cal.get(Calendar.MINUTE); 
    int second = cal.get(Calendar.SECOND); 
    System.out.println("===年===" + year); 
    System.out.println("===月===" + month); 
    System.out.println("===日===" + day); 
    System.out.println("===时===" + hour); 
    System.out.println("===分===" + minute); 
    System.out.println("===秒===" + second); 
  } 
  /** 
   * 获取自定义时间的年月日 
   */
  public static Map<String, Object> getMonthDay(String dateStr) { 
	  Map<String,Object> map = new HashMap<String,Object>();
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	  Calendar cal = Calendar.getInstance(); 
	  Date dt = null; 
	  try { 
		  dt = sdf.parse(dateStr); 
	  } catch (ParseException e) { 
		  // TODO Auto-generated catch block 
		  e.printStackTrace(); 
	  } 
	  cal.setTime(dt); 
	  int year = cal.get(Calendar.YEAR); 
	  int month = cal.get(Calendar.MONTH) + 1; 
	  int day = cal.get(Calendar.DAY_OF_MONTH); 
//	  int hour = cal.get(Calendar.HOUR_OF_DAY); 
//	  int minute = cal.get(Calendar.MINUTE); 
//	  int second = cal.get(Calendar.SECOND); 
	  map.put("year", year);
	  map.put("month", month);
	  map.put("day", day);
	return map; 
  } 
  /** 
   * 获取当前时间的年月日 
   */
  public static void getMonthDay2() { 
    Calendar cal = Calendar.getInstance(); 
    int year = cal.get(Calendar.YEAR); 
    int month = cal.get(Calendar.MONTH) + 1; 
    int day = cal.get(Calendar.DAY_OF_MONTH); 
    int hour = cal.get(Calendar.HOUR_OF_DAY); 
    int minute = cal.get(Calendar.MINUTE); 
    int second = cal.get(Calendar.SECOND); 
    System.out.println("===年===" + year); 
    System.out.println("===月===" + month); 
    System.out.println("===日===" + day); 
    System.out.println("===时===" + hour); 
    System.out.println("===分===" + minute); 
    System.out.println("===秒===" + second); 
  } 
}
