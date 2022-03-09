package com.stonedt.util;

import com.alibaba.fastjson.JSONObject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil {
  private static final long ONE_MINUTE = 60000L;
  
  private static final long ONE_HOUR = 3600000L;
  
  private static final long ONE_DAY = 86400000L;
  
  private static final long ONE_WEEK = 604800000L;
  
  private static final String ONE_SECOND_AGO = "秒前";
  
  private static final String ONE_MINUTE_AGO = "分钟前";
  
  private static final String ONE_HOUR_AGO = "小时前";
  
  private static final String ONE_DAY_AGO = "天前";
  
  private static final String ONE_MONTH_AGO = "月前";
  
  private static final String ONE_YEAR_AGO = "年前";
  
  public static String nowTime() {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String nowTimeString = LocalDateTime.now().format(dateTimeFormatter);
    return nowTimeString;
  }
  
  public static long toTimestamp(String time) {
    long times = 0L;
    try {
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date date = format.parse(time);
      times = date.getTime();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return times;
  }
  
  public static Date parse(String str, String pattern, Locale locale) {
    if (str == null || pattern == null)
      return null; 
    try {
      return (new SimpleDateFormat(pattern, locale)).parse(str);
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public static String stampToDate(String time) {
    String res = "";
    try {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      long lt = (new Long(time)).longValue();
      Date date = new Date(lt);
      res = simpleDateFormat.format(date);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return res;
  }
  
  public static String convertYear(String timeStr, String format, String toformat) {
    try {
      Date date = (new SimpleDateFormat(format)).parse(timeStr);
      String now = (new SimpleDateFormat(toformat)).format(date);
      return now;
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    } 
  }
  
  public static String format(Date date) {
    long delta = (new Date()).getTime() - date.getTime();
    if (delta < 60000L) {
      long seconds = toSeconds(delta);
      return ((seconds <= 0L) ? 1L : seconds) + "秒前";
    } 
    if (delta < 2700000L) {
      long minutes = toMinutes(delta);
      return ((minutes <= 0L) ? 1L : minutes) + "分钟前";
    } 
    if (delta < 86400000L) {
      long hours = toHours(delta);
      return ((hours <= 0L) ? 1L : hours) + "小时前";
    } 
    if (delta < 172800000L)
      return "昨天"; 
    if (delta < 2592000000L) {
      long days = toDays(delta);
      return ((days <= 0L) ? 1L : days) + "天前";
    } 
    if (delta < 29030400000L) {
      long months = toMonths(delta);
      return ((months <= 0L) ? 1L : months) + "月前";
    } 
    long years = toYears(delta);
    return ((years <= 0L) ? 1L : years) + "年前";
  }
  
  private static long toSeconds(long date) {
    return date / 1000L;
  }
  
  private static long toMinutes(long date) {
    return toSeconds(date) / 60L;
  }
  
  private static long toHours(long date) {
    return toMinutes(date) / 60L;
  }
  
  private static long toDays(long date) {
    return toHours(date) / 24L;
  }
  
  private static long toMonths(long date) {
    return toDays(date) / 30L;
  }
  
  private static long toYears(long date) {
    return toMonths(date) / 365L;
  }
  
//  public static String getDate() {
//    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    Calendar calendar = Calendar.getInstance();
//    calendar.setTime(new Date());
//    Date starttimeDate = calendar.getTime();
//    return format.format(starttimeDate);
//  }
  
  public static String getDateday() {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    Date starttimeDate = calendar.getTime();
    return format.format(starttimeDate);
  }
  
  public static String getDateyear() {
    SimpleDateFormat format = new SimpleDateFormat("yyyy");
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    Date starttimeDate = calendar.getTime();
    return format.format(starttimeDate);
  }
  
  public static String minusMinute(int num) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.add(12, -num);
    Date starttimeDate = calendar.getTime();
    return format.format(starttimeDate);
  }
  
  public static String minusHour(int num) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.add(10, -num);
    Date starttimeDate = calendar.getTime();
    return format.format(starttimeDate);
  }
  
  public static String minusDay(int num) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.add(5, -num);
    Date starttimeDate = calendar.getTime();
    return format.format(starttimeDate);
  }
  
  public static String getNumber(String str) {
    String regEx = "[^0-9]";
    Pattern pattern = Pattern.compile(regEx);
    return pattern.matcher(str).replaceAll("").trim();
  }
  
  
  
  public static String getDateTimeNow() {
    DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return ofPattern.format(LocalDateTime.now());
  }
  
  public static Map<String, String> twentyFourHours() {
    DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime localDateTime = LocalDateTime.now();
    String endTime = ofPattern.format(localDateTime);
    String startTime = ofPattern.format(localDateTime.minusHours(24L));
    Map<String, String> time = new HashMap<>();
    time.put("startTime", startTime);
    time.put("endTime", endTime);
    return time;
  }
  
  public static List<Map<String, String>> historicalTrend(String startTime, String endTime) {
    List<Map<String, String>> result = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar startCalendar = Calendar.getInstance();
    Calendar endCalendar = Calendar.getInstance();
    Calendar yearCalendar = Calendar.getInstance();
    int days = 0;
    try {
      startCalendar.setTime(sdf.parse(startTime));
      endCalendar.setTime(sdf.parse(endTime));
      int day1 = startCalendar.get(6);
      int day2 = endCalendar.get(6);
      int year1 = startCalendar.get(1);
      int year2 = endCalendar.get(1);
      if (year1 != year2) {
        int timeDistance = 0;
        for (int j = year1; j < year2; j++) {
          if ((j % 4 == 0 && j % 100 != 0) || j % 400 == 0) {
            timeDistance += 366;
          } else {
            timeDistance += 365;
          } 
        } 
        days = timeDistance + day2 - day1;
      } else {
        days = day2 - day1;
      } 
      for (int i = 0; i <= days; i++) {
        String nowDate = sdf.format(endCalendar.getTime());
        yearCalendar.setTime(endCalendar.getTime());
        yearCalendar.add(1, -1);
        String yearDate = sdf.format(yearCalendar.getTime());
        endCalendar.add(5, -1);
        Map<String, String> map = new HashMap<>();
        map.put("nowDate", nowDate);
        map.put("yearDate", yearDate);
        result.add(map);
      } 
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  public static String dataMonitorYYYYMMDDHHMMSS(Integer timeType) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
    Calendar calendar = Calendar.getInstance();
    String times = "";
    String timee = "";
    if (timeType.intValue() == 0) {
      timee = sdf.format(calendar.getTime());
      times = timee.substring(0, 10) + " 00:00:00";
    } else if (timeType.intValue() == 1) {
      calendar.add(5, -1);
      timee = sdf2.format(calendar.getTime()) + " 23:59:59";
      times = sdf2.format(calendar.getTime()) + " 00:00:00";
    } else if (timeType.intValue() == 2) {
      timee = sdf.format(calendar.getTime());
      calendar.add(5, -7);
      times = sdf.format(calendar.getTime());
    } else if (timeType.intValue() == 3) {
      timee = sdf.format(calendar.getTime());
      calendar.add(5, -30);
      times = sdf.format(calendar.getTime());
    } else if (timeType.intValue() == 5) {
      timee = sdf.format(calendar.getTime());
      calendar.add(5, -1);
      times = sdf.format(calendar.getTime());
    } 
    return times + "&" + timee;
  }
  
  public static String convertYear(String timeStr) {
    try {
      Date date = (new SimpleDateFormat("yyyyhh:mm:ss")).parse(timeStr);
      String now = (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).format(date);
      return now;
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    } 
  }
  
  public static JSONObject getDifferOneDayTimes(Integer num) {
    JSONObject returnTimes = new JSONObject();
    SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
    Date endDate = new Date();
    String endTime = simple.format(endDate);
    Calendar cal = Calendar.getInstance();
    cal.add(5, num.intValue());
    Date date = cal.getTime();
    String startTime = simple.format(date);
    returnTimes.put("times", startTime);
    returnTimes.put("TIMEBEGIN", startTime);
    returnTimes.put("timee", endTime);
    returnTimes.put("TIMEEND", endTime);
    return returnTimes;
  }
 
  
  public static String fullTextYYYYMMDDHHMMSS(Integer timeType) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Calendar calendar = Calendar.getInstance();
    String times = "";
    String timee = "";
    if (timeType.intValue() == 0) {
      timee = sdf.format(calendar.getTime());
      times = timee.substring(0, 10) + " 00:00:00";
    } else if (timeType.intValue() == 1) {
      timee = sdf.format(calendar.getTime());
      calendar.add(5, -1);
      times = sdf.format(calendar.getTime());
    } else if (timeType.intValue() == 2) {
      timee = sdf.format(calendar.getTime());
      calendar.add(5, -2);
      times = sdf.format(calendar.getTime());
    } else if (timeType.intValue() == 3) {
      timee = sdf.format(calendar.getTime());
      calendar.add(5, -3);
      times = sdf.format(calendar.getTime());
    } else if (timeType.intValue() == 4) {
      timee = sdf.format(calendar.getTime());
      calendar.add(5, -7);
      times = sdf.format(calendar.getTime());
    } 
    return times + "&" + timee;
  }
  
  public static String PushForwardTwoMinute(String timee) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      Date parse = sdf.parse(timee);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(parse);
      calendar.add(12, -2);
      timee = sdf.format(calendar.getTime());
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    return timee;
  }
  
  public static int calculateTimeDiff(String startTime, String endTime) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date startTimeDate = null;
    Date endTimeDate = null;
    int days = 0;
    try {
      startTimeDate = format.parse(startTime);
      endTimeDate = format.parse(endTime);
      days = (int)((endTimeDate.getTime() - startTimeDate.getTime()) / 86400000L);
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    return days;
  }
  
  public static List<Date> addTime(String startTime, String endTime) {
    int days = calculateTimeDiff(startTime, endTime);
    int count = days * 12;
    List<Date> timeList = new ArrayList<>();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date startTimeDate = null;
    String startTimeStr = "";
    try {
      startTimeDate = format.parse(startTime);
      Calendar c = Calendar.getInstance();
      c.setTime(startTimeDate);
      for (int i = 0; i < count; i++) {
        c.add(11, 2);
        Date newDate = c.getTime();
        startTimeStr = format.format(newDate);
        timeList.add(newDate);
        if (startTimeStr.equals(endTime));
      } 
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    return timeList;
  }
  
  public static String addEndTime(String startTime, Integer reportNav) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date startTimeDate = null;
    String endTimeStr = "";
    Random random = new Random();
    int step = 0;
    if (reportNav.intValue() == 1) {
      step = 24;
    } else if (reportNav.intValue() == 2) {
      step = 168;
    } else if (reportNav.intValue() == 3) {
      step = 720;
    } else if (reportNav.intValue() == 4) {
      step = random.nextInt(10) * 24;
    } 
    try {
      startTimeDate = format.parse(startTime);
      Calendar c = Calendar.getInstance();
      c.setTime(startTimeDate);
      c.add(11, step);
      Date newDate = c.getTime();
      endTimeStr = format.format(newDate);
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    return endTimeStr;
  }
  
  public static String getNowTime() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Calendar calendar = Calendar.getInstance();
    return sdf.format(calendar.getTime());
  }
  
  public static Map<String, String> YearOnYearCycle(String startTime, String endTime) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Calendar calendar = Calendar.getInstance();
    try {
      calendar.setTime(sdf.parse(startTime));
      calendar.add(1, -1);
      startTime = sdf.format(calendar.getTime());
      calendar.setTime(sdf.parse(endTime));
      calendar.add(1, -1);
      endTime = sdf.format(calendar.getTime());
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    Map<String, String> map = new HashMap<>();
    map.put("startTime", startTime);
    map.put("endTime", endTime);
    return map;
  }
  
  public static Map<String, String> RingRatioCycle(String startTime, String endTime) {
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Calendar startCalendar = Calendar.getInstance();
    Calendar endCalendar = Calendar.getInstance();
    int days = 0;
    try {
      startCalendar.setTime(sdf1.parse(startTime));
      endCalendar.setTime(sdf1.parse(endTime));
      int day1 = startCalendar.get(6);
      int day2 = endCalendar.get(6);
      int year1 = startCalendar.get(1);
      int year2 = endCalendar.get(1);
      if (year1 != year2) {
        int timeDistance = 0;
        for (int i = year1; i < year2; i++) {
          if ((i % 4 == 0 && i % 100 != 0) || i % 400 == 0) {
            timeDistance += 366;
          } else {
            timeDistance += 365;
          } 
        } 
        days = timeDistance + day2 - day1;
      } else {
        days = day2 - day1;
      } 
      startCalendar.add(5, -days);
      endCalendar.add(5, -days);
      startTime = sdf1.format(startCalendar.getTime());
      endTime = sdf1.format(endCalendar.getTime());
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    Map<String, String> map = new HashMap<>();
    map.put("startTime", startTime);
    map.put("endTime", endTime);
    return map;
  }
  
  public static String dealDateFormat(String oldDateStr) {
    String returnStr = "";
    try {
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
      Date date = df.parse(oldDateStr);
      SimpleDateFormat df1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
      Date date1 = df1.parse(date.toString());
      DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      returnStr = df2.format(date1);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return returnStr;
  }
  
  public static JSONObject dateRoll(Date date, int i, int d) {
    JSONObject timeJson = new JSONObject();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(i, d);
    date = calendar.getTime();
    String timee = format.format(new Date());
    String times = format.format(date);
    timeJson.put("times", times);
    timeJson.put("timee", timee);
    return timeJson;
  }
  
  public static void main(String[] args) {
    getDifferOneDayTimes(Integer.valueOf(-2));
  }


  //比较两个时间，判断时间1是否在时间2后面
  public static boolean compareTime(String time1 , String time2 ) {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date1 = null;
    Date date2 = null;
    try {
      date1 = simpleDateFormat.parse(time1);
      date2 = simpleDateFormat.parse(time2);
    } catch (ParseException e) {
      e.printStackTrace();
    }


    return date1.after(date2);

  }



  public static String  addTerm_of_validityTime( Long term_of_validity){


      long days = term_of_validity * (1 * 24 * 60 * 60 * 1000);

      Date date = new Date();
      long nowTime = date.getTime();
      date.setTime(nowTime + days);

      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      String time = simpleDateFormat.format(date);

      return time;
  }
}
