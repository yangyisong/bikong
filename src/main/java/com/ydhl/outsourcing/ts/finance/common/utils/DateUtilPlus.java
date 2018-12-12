package com.ydhl.outsourcing.ts.finance.common.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;


public final class DateUtilPlus extends DateUtils {

    private DateUtilPlus() {

    }

    /**
     * 设置指定日期的开始时间，将时分秒置为00:00:00
     *
     * @param date 指定日期
     * @return 格式化后的日期
     */
    public static Date setDayStart(final Date date) {
        final Calendar c = Calendar.getInstance();
        c.setLenient(false);
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 设置指定日期的加特定的年份
     *
     * @param date 指定日期
     * @return 格式化后的日期
     */
    public static Date getDateAddYear(final Date date, Integer num) {
        final Calendar c = Calendar.getInstance();
        c.setLenient(false);
        c.setTime(date);
        c.set(Calendar.YEAR, c.get(Calendar.YEAR) + num);
        return c.getTime();
    }

    /**
     * 设置指定日期的结束时间，将时分秒置为23:59:59
     *
     * @param date 指定日期
     * @return 格式化后的日期
     */
    public static Date setDayEnd(final Date date) {
        final Calendar c = Calendar.getInstance();
        c.setLenient(false);
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    /**
     * 获取当前自然月1号，将时分秒置为00:00:00
     *
     * @param date 指定日期
     * @return 格式化后的日期
     */
    public static Date setMonthStart(final Date date) {
        final Calendar c = Calendar.getInstance();
        c.setLenient(false);
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取当前自然月1号，将时分秒置为00:00:00
     *
     * @param date 指定日期
     * @return 格式化后的日期
     */
    public static Date setMonthEnd(final Date date) {
        final Calendar c = Calendar.getInstance();
        c.setLenient(false);
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, 30);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 时间相减后取秒
     *
     * @param date1 时间1
     * @param date2 时间2
     * @return 相减后的秒数
     */
    public static long subtractBySecond(final Date date1, final Date date2) {
        long milliSeconds = date1.getTime() - date2.getTime(); //毫秒
        long seconds = milliSeconds / 1000L;
        return seconds;
    }

    /**
     * 时间相减后取秒
     *
     * @param date1 时间1
     * @param date2 时间2
     * @return 相减后的秒数
     */
    public static long subtractByDay(final Date date1, final Date date2) {
        long milliSeconds = date1.getTime() - date2.getTime(); //毫秒
        return milliSeconds / (1000L * 86400);
    }

    /**
     * 获取两个时间的年差
     *
     * @param date1 时间1
     * @param date2 时间2
     * @return 年差
     */
    public static int subtractByYear(final Date date1, final Date date2) {
        return getYear(date1) - getYear(date2);
    }

    /**
     * 返回日期的年
     *
     * @param date
     *          Date
     * @return int
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取当前时间的整点
     *
     * @param date 时间
     * @return 整点时间
     */
    public static Date setHourStart(final Date date) {
        final Calendar c = Calendar.getInstance();
        c.setLenient(false);
        c.setTime(date);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取指定的整点时间
     *
     * @param hour   小时
     * @param minute 分
     * @param second 秒
     * @return 整点时间
     */
    public static Date getSpecifyDate(final Date date, int hour, int minute, int second) {
        Calendar todayStart = Calendar.getInstance();
        todayStart.setLenient(false);
        todayStart.setTime(date);
        todayStart.set(Calendar.HOUR_OF_DAY, hour);
        todayStart.set(Calendar.MINUTE, minute);
        todayStart.set(Calendar.SECOND, second);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

}
