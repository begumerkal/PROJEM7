package com.wyd.rolequery.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {
	/**
	 * 　　 * 获得当前时间一小时前的时间，格式化成yyyy-MM-dd HH:mm:ss:SS<br>
	 * 　　 * 　　 * @return 当前时间一小时前的时间 　　
	 */
	@SuppressWarnings("deprecation")
	public static String getOneHoursAgoTime(int h) {
		String twoHoursSith = "";
		Date mydate = new Date();
		mydate.setHours(mydate.getHours() - h);
		twoHoursSith = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS")
		.format(mydate);
		return twoHoursSith;
	}
	
	/**
     * 获取当前日期字符串
     * 
     * @return 当前日期字符串，格式为： yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentDateTime() {
        return getCurrentDate() + " " + getCurrentTime();
    }
    
    /**
     * 获取当前日期字符串
     * 
     * @return 当前日期字符串,格式： yyyy-MM-dd
     */
    public static String getCurrentDate() {
        String fullDate = getCurrentYearMonth();
        int iCurrentDay = getCurrentDay();
        if (iCurrentDay < 10) {
            fullDate += "-0" + iCurrentDay;
        } else {
            fullDate += "-" + iCurrentDay;
        }
        return fullDate;
    }
    
    /**
     * 获取当前日期-日数
     * 
     * @return 当前日期-日数
     */
    public static int getCurrentDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * 获取当前年月日期字符串
     * 
     * @return 当前年月日期字符串,格式： yyyy-MM
     */
    public static String getCurrentYearMonth() {
        String fullDate = String.valueOf(getCurrentYear());
        int iCurrentMonth = getCurrentMonth();
        if (iCurrentMonth < 10) {
            fullDate += "-0" + iCurrentMonth;
        } else {
            fullDate += "-" + iCurrentMonth;
        }
        return fullDate;
    }
    
    /**
     * 获取当前日期-月份
     * 
     * @return 当前日期-月份
     */
    public static int getCurrentMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }
    
    /**
     * 获取当前日期-年份
     * 
     * @return 当前日期-年份
     */
    public static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    
    /**
     * 获取当前日期时、分、秒组成字符串
     * 
     * @return 当前日期时、分、秒组成字符串， 格式： HH:mm:ss
     */
    public static String getCurrentTime() {
        String time = getCurrentHourMinute();
        int iCurrentSecond = getCurrentSecond();
        if (iCurrentSecond < 10) {
            time += ":0" + iCurrentSecond;
        } else {
            time += ":" + iCurrentSecond;
        }
        return time;
    }
    
    /**
     * 获取当前日期时、分组成字符串
     * 
     * @return 当前日期时、分、秒组成字符串， 格式： HH:mm
     */
    public static String getCurrentHourMinute() {
        String time;
        int temp = getCurrentHour();
        if (temp < 10) {
            time = "0" + temp + ":";
        } else {
            time = temp + ":";
        }
        temp = getCurrentMinute();
        if (temp < 10) {
            time += "0" + temp;
        } else {
            time += temp;
        }
        return time;
    }
    
    /**
     * 获取当前日期-分钟
     * 
     * @return 当前日期-分钟
     */
    public static int getCurrentMinute() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }
    
    /**
     * 获取当前日期-小时
     * 
     * @return 当前日期-小时
     */
    public static int getCurrentHour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    
    /**
     * 获取当前日期-秒钟
     * 
     * @return 当前日期-秒钟
     */
    public static int getCurrentSecond() {
        return Calendar.getInstance().get(Calendar.SECOND);
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		getOneHoursAgoTime(1);
	}
}
