package com.ecgobike.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by ChenJun on 2018/3/10.
 */
public class DateUtils {
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String EASYUI_DATA_TIME_FORMATE = "MM/dd/yyyy HH:mm:ss";

    public static final long MILLIS_PER_SECOND = 1000;
    public static final long MILLIS_PER_MINUTE = 60 * MILLIS_PER_SECOND;
    public static final long MILLIS_PER_HOUR = 60 * MILLIS_PER_MINUTE;
    public static final long MILLIS_PER_DAY = 24 * MILLIS_PER_HOUR;

    /**
     * 当前时间秒
     *
     * @return 当前时间秒
     */
    public static int getNow() {
        return (int) (System.currentTimeMillis() / MILLIS_PER_SECOND);
    }

    public static String getNow(boolean ifdatetime) {
        if (ifdatetime) {
            return formatDate(DATE_TIME_FORMAT);
        } else {
            return formatDate(DATE_FORMAT);
        }
    }

    /**
     * 获取当前时间，格式返回毫秒
     * @return
     */
    public static long getMillisNow() {
        return System.currentTimeMillis();
    }

    /**
     * 格式化时间
     *
     * @param date
     * @param pattern 参照DATE_FORMAT、TIME_FORMAT、DATE_TIME_FORMAT
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static String formatDate(String pattern) {
        return formatDate(new Date(), pattern);
    }

    public static String formatTime(int time, String pattern) {
        return formatMillisTime(time * MILLIS_PER_SECOND, pattern);
    }

    public static String formatMillisTime(long time, String pattern) {
        return formatDate(new Date(time), pattern);
    }

    /**
     * 判断服务器时间是否在指定时间之内
     *
     * @param
     * @param
     * @return
     */
    public static boolean JudgeTime(long startTime, long endTime) {
        return (getMillisNow() > startTime && getMillisNow() < endTime) ? true : false;
    }

    /**
     * 判断服务器时间是否在指定时间之内
     * 时间默认为指定开始时间为00:00:00结束时间为24:00:00
     *
     * @param startTime格式year-month-day
     * @param endTime格式year-month-day
     * @return
     */
    public static boolean JudgeTime(String startTime, String endTime, String pattern) {
        return JudgeTime(string2Time(startTime, pattern), string2Time(endTime, pattern));
    }

    /**
     * 计算两个时间之间相隔的天数
     *
     * @param startTime
     *            开始时间,以秒为单位
     * @param endTime
     *            结束时间,以秒为单位
     * @return
     */
    public static int dayDelta(int startTime, int endTime) {
        if (startTime > endTime) {
            return 0;
        }

        GregorianCalendar cal1 = new GregorianCalendar();
        GregorianCalendar cal2 = new GregorianCalendar();

        cal1.setTime(new Date(startTime * 1000l));
        cal2.setTime(new Date(endTime * 1000l));

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);

        int delta = 0;
        if (year1 < year2) {
            if (cal1.isLeapYear(year1)) {
                delta = 366 - cal1.get(Calendar.DAY_OF_YEAR);
            } else {
                delta = 365 - cal1.get(Calendar.DAY_OF_YEAR);
            }

            delta += cal2.get(Calendar.DAY_OF_YEAR);
        } else {
            delta = cal2.get(Calendar.DAY_OF_YEAR) - cal1.get(Calendar.DAY_OF_YEAR);
        }

        return delta;
    }


    /**
     * 得到明天零点时间
     *
     * @return
     */
    public static long getTommorowStart() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * 字符串转date
     *
     * @param time
     * @param pattern
     * @return
     */
    public static Date string2Date(String time, String pattern) {
        if (time == null || time.length() == 0) {
            return null;
        }
        Date dttDate = null;
        try {
            SimpleDateFormat oFormatter = new SimpleDateFormat(pattern);
            dttDate = oFormatter.parse(time);
            oFormatter = null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dttDate;
    }

    /**
     * 字符串转long
     *
     * @param time
     * @param pattern
     * @return
     */
    public static Long string2Time(String time, String pattern) {
        if (time == null || time.length() == 0) {
            return null;
        }
        Date dttDate = null;
        try {
            SimpleDateFormat oFormatter = new SimpleDateFormat(pattern);
            dttDate = oFormatter.parse(time);
            oFormatter = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dttDate.getTime();
    }

    /**
     * 给定两个日期，返回日期的天数差
     *
     * @param start
     * @param end
     * @return int
     */
    public static int getDayNumber(Date start, Date end) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(start);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(end);
        if (startCal.after(endCal)) {
            return 0;
        }
        int num = 1;
        while (startCal.before(endCal)) {
            startCal.add(Calendar.DATE, 1);
            num++;
        }
        return num;
    }

    /**
     * 给定两个日期，返回日期的天数差
     *
     * @param start
     * @param end
     * @return int
     */
    public static int getDayNumber(Long start, Long end) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(new Date(start));
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(new Date(end));
        if (startCal.after(endCal)) {
            return 0;
        }
        int num = 1;
        while (startCal.before(endCal)) {
            startCal.add(Calendar.DATE, 1);
            num++;
        }
        return num;
    }

    /**
     * 获取指定时间当日开始时间
     *
     * @param date
     * @return
     */
    public static Date getDayStartDate(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取指定时间当日开始时间
     *
     * @param date
     * @return
     */
    public static Long getDayStartTime(Date date) {
        return getDayStartDate(date).getTime();
    }

    /**
     * 获取指定时间当日结束时间
     *
     * @param date
     * @return
     */
    public static Date getDayEndDate(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 获取指定时间当日结束时间
     *
     * @param date
     * @return
     */
    public static Long getDayEndTime(Date date) {
        return getDayEndDate(date).getTime();
    }

    /**
     * 获取指定时间下一日开始时间
     *
     * @param date
     * @return
     */
    public static Date getNextDayStartDate(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取指定时间下一日开始时间
     *
     * @param date
     * @return
     */
    public static Long getNextDayStartTime(Date date) {
        return getNextDayStartDate(date).getTime();
    }

    /**
     * 获取指定时间当月开始时间
     *
     * @return
     */
    public static Date getMonthStartDate(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取指定时间当月开始时间
     *
     * @param date
     * @return
     */
    public static Long getMonthStartTime(Date date) {
        return getMonthStartDate(date).getTime();
    }

    /**
     * 获取指定时间下月开始时间
     *
     * @param date
     * @return
     */
    public static Date getNextMonthStartDate(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }


    /**
     * 获取指定时间下月开始时间
     *
     * @param date
     * @return
     */
    public static Long getNextMonthStartTime(Date date) {
        return getNextMonthStartDate(date).getTime();
    }

    /**
     * 获取指定时间当年开始时间
     *
     * @return
     */
    public static Date getYearStartDate(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取指定时间当年开始时间
     *
     * @param date
     * @return
     */
    public static Long getYearStartTime(Date date) {
        return getYearStartDate(date).getTime();
    }


    /**
     * 获取指定时间下一年开始时间
     *
     * @return
     */
    public static Date getNextYearStartDate(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.YEAR, 1);
        return calendar.getTime();
    }

    /**
     * 获取指定时间下一年开始时间
     *
     * @param date
     * @return
     */
    public static Long getNextYearStartTime(Date date) {
        return getNextYearStartDate(date).getTime();
    }


    /**
     * 两个日期是否是同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
        boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }

    /**
     * 两个日期是否是同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDate(long date1, long date2) {
        return isSameDate(new Date(date1), new Date(date2));
    }

    public static Date plusSeconds(Date date, int seconds) {
        return new Date(date.getTime() + seconds * 1000);
    }

    /**
     * 根据参数获取系统时间的前N天或者后N天
     * @param day 当day为正时表示获取系统时间的后day天，为负时则为前day天
     * @return
     */
    public static String getTimeSegment(int day) {
        SimpleDateFormat df = new SimpleDateFormat(DATE_TIME_FORMAT);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(formatDate("yyyy")));
        cal.set(Calendar.MONTH, Integer.parseInt(formatDate("MM")) - 1);
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(formatDate("dd")));
        Date date = cal.getTime();
        cal.add(Calendar.DATE, day);
        date = cal.getTime();
        return df.format(date);
    }

    /**
     * 日期天数加减操作
     * @param dt 要操作的日期
     * @param howManyDays 操作的天数，正数代表加，负数为减
     */
    public static Date dateMinusDays(Date dt, int howManyDays) {
        if (dt == null) {
            dt = new Date();
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
        Date month = null;
        try {
            month = sdf.parse(sdf.format(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(month);
        rightNow.add(Calendar.DATE, howManyDays);
        return rightNow.getTime();
    }

}

