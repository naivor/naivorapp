package com.naivor.app.extras.utils;

import android.text.format.Time;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * DateUtil 是一个关于时间，日期转换和判断的类
 */
public class DateUtil {
    /**
     * 获取当前时间
     *
     * @return 毫秒
     */
    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间
     *
     * @return 标准时间字符串
     */
    public static String currentTime() {
        return getDate("yyyy-MM-dd HH:mm:ss",currentTimeMillis());
    }

    /**
     * 判断两个日期是否是同一天
     *
     * @param time1  日期一，毫秒表示
     * @param time2  日期二，毫秒表示
     * @return boolean
     */
    public static boolean isSameDay(long time1, long time2) {
        Time time = new Time();
        time.set(time1);

        int thenYear = time.year;
        int thenMonth = time.month;
        int thenMonthDay = time.monthDay;

        time.set(time2);
        return (thenYear == time.year) && (thenMonth == time.month) && (thenMonthDay == time.monthDay);
    }

    /**
     * 判断两个日期恰好相隔n天（n 为正整数）
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return boolean
     */
    public static boolean isAfterDays(long startTime, long endTime, int days) {
        Time time = new Time();

        long deadTime = getNextDay(startTime, days);
        time.set(deadTime);

        int thenYear = time.year;
        int thenMonth = time.month;
        int thenMonthDay = time.monthDay;

        time.set(endTime);

        return (thenYear == time.year) && (thenMonth == time.month) && (thenMonthDay == time.monthDay);
    }

    /**
     * 获取从某个日期开始计算未来日期的时间戳
     *
     * @param startTime 开始日期
     * @param index index表示今天后的第几天，0表示今天
     * @return boolean
     */
    public static long getNextDay(long startTime, int index) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(startTime);
        calendar.add(Calendar.DAY_OF_MONTH, index);
        return calendar.getTime().getTime();
    }

    /**
     * 获取今天开始计算未来日期的时间戳
     *
     * @param index index表示今天后的第几天，0表示今天
     * @return boolean
     */
    public static long getNextDay(int index) { // 获取日期，index表示今天后的第几天
        return getNextDay(currentTimeMillis(), index);
    }

    /**
     * 将时间date转化成String类型
     *
     * @param template 时间的格式，为正则表达式，如 “YYYY-MM-dd HH:mm:ss”
     * @param date 要转换日期，单位为毫秒
     * @return String类型日期，如 “2015年08月08日 12:00:00”
     */
    public static String getDate(String template, long date) {
        if (date > 0) {
            SimpleDateFormat format = new SimpleDateFormat(template, Locale.CHINA);
            return format.format(new Date(date));
        }
        return "";
    }

    /**
     * 通过判断将时间转换为更容易理解的具体词语
     *
     * @param time 要转换的时间
     * @return String（刚刚 几分钟前 几小时前 几天前 具体时间）
     */
    public static String transTime(long time) {
        if (time > 0) {
            long btm = (System.currentTimeMillis() - time) / 1000;

            if (btm <= 60) {
                return "刚刚";
            } else {
                btm = btm / 60;
                if (btm <= 60) {
                    return btm + "分钟前";
                } else {
                    btm = btm / 60;
                    if (btm <= 24) {
                        return btm + "小时前";
                    } else {
                        btm = btm / 24;
                        if (btm <= 3) {
                            return btm + "天前";
                        } else {
                            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd  HH:mm:ss", Locale.CHINA);
                            return format.format(new Date(time));
                        }
                    }
                }
            }
        }

        return "";
    }


}
