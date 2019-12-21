package com.github.aracwong.commons;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Alpin on 2015/11/17.
 */
@Slf4j
public class DateUtil {


    /**
     * 字符串转换成时间对象<br />
     *
     * @param time   字符串时间
     * @param format 格式化字符串，如：yyyy-MM-dd HH:mm:ss.SSS
     */
    public static Date parse(String time, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return (Date) dateFormat.parse(time);
        } catch (ParseException ex) {
            return null;
        }
    }

    /**
     * 时间对象转换成字符串<br />
     *
     * @param time   Date对象
     * @param format 格式化字符串，如：yyyy-MM-dd HH:mm:ss.SSS
     */
    public static String formatToString(Date time, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(time);
    }

    public static String formatDateStr(String sourceDateStr, String format) {
        return formatToString(parse(sourceDateStr, format), format);
    }

    /**
     * 获得日期年份<br />
     *
     * @param time Calendar对象
     * @return int 日期年份
     */
    public static int getYear(Calendar time) {
        return time.get(Calendar.YEAR);
    }

    /**
     * 获得日期分钟<br />
     *
     * @param time Calendar对象
     * @return int 日期分钟
     */
    public static int getMinute(Calendar time) {
        return time.get(Calendar.MINUTE);
    }

    /**
     * 获得日期分钟<br />
     *
     * @param time Date对象
     * @return int 日期分钟
     */
    public static int getMinute(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return getMinute(time);
    }

    /**
     * 获得日期年份<br />
     *
     * @param time Date对象
     * @return int 日期年份
     */
    public static int getYear(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return getYear(calendar);
    }

    /**
     * 获得日期月份<br />
     *
     * @param time Calendar对象
     * @return int 日期月份
     */
    public static int getMonth(Calendar time) {
        return time.get(Calendar.MONTH);
    }

    /**
     * 获得日期月份<br />
     *
     * @param time Date对象
     * @return int 日期月份
     */
    public static int getMonth(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return getMonth(calendar);
    }

    /**
     * 获得年份所对应的天<br />
     *
     * @param time Calendar对象
     * @return int 年份所对应的天
     */
    public static int getDayOfYear(Calendar time) {
        return time.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 获得年份所对应的天<br />
     *
     * @param time Date对象
     * @return int 年份所对应的天
     */
    public static int getDayOfYear(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return getDayOfYear(calendar);
    }

    /**
     * 获得日期所对应的小时<br />
     *
     * @param time Date对象
     * @return int 小时
     */
    public static int getHour(Date time) {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR);
    }

    /**
     * 增加天<br />
     *
     * @param time Calendar对象
     * @param days 要增加的天数
     * @return Calendar Calendar对象
     */
    public static Calendar addDays(Calendar time, int days) {
        time.set(Calendar.DAY_OF_YEAR, time.get(Calendar.DAY_OF_YEAR) + days);
        return time;
    }

    /**
     * 增加天<br />
     *
     * @param time Date对象
     * @param days 要增加的天数
     * @return Calendar Calendar对象
     */
    public static Calendar addDays(Date time, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return addDays(calendar, days);
    }

    /**
     * 增加月<br />
     *
     * @param time   Calendar对象
     * @param months 要增加的月数
     * @return Calendar Calendar对象
     */
    public static Calendar addMonths(Calendar time, int months) {
        time.set(Calendar.MONTH, time.get(Calendar.MONTH) + months);
        return time;
    }

    /**
     * 增加月<br />
     *
     * @param time   Date对象
     * @param months 要增加的月数
     * @return Calendar Calendar对象
     */
    public static Calendar addMonths(Date time, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return addMonths(calendar, months);
    }

    /**
     * 增加年<br />
     *
     * @param time  Calendar对象
     * @param years 要增加的年数
     * @return Calendar Calendar对象
     */
    public static Calendar addYears(Calendar time, int years) {
        time.set(Calendar.YEAR, time.get(Calendar.YEAR) + years);
        return time;
    }

    /**
     * 增加年<br />
     *
     * @param time  Date对象
     * @param years 要增加的年数
     * @return Calendar Calendar对象
     */
    public static Calendar addYears(Date time, int years) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return addYears(calendar, years);
    }

    /**
     * 普通时间转换成unix时间戳<br />
     *
     * @param time Date对象
     * @return long unix时间戳
     */
    public static long toUnixTimestamp(Date time) {
        return time.getTime() / 1000L;
    }

    /**
     * 普通时间转换成unix时间戳<br />
     *
     * @return long unix时间戳
     */
    public static long getCurrentUnixTimestamp() {
        return System.currentTimeMillis() / 1000L;
    }

    public static Long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static long internalTime(LocalDate from, LocalDate to, TemporalUnit unit) {
        return from.until(to, unit);
    }

    public static List<String> getInternalDateStr(LocalDate startDate, LocalDate endDate, String format, TemporalUnit unit) {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern(format, Locale.getDefault());
        List<String> dateStrList = Lists.newArrayList();
        // 相隔天数
        long internalDays = internalTime(startDate, endDate, unit);
        for (int i = 0; i <= internalDays; i++) {
            dateStrList.add(pattern.format(startDate));
            startDate = startDate.plus(1, unit);
        }
        return dateStrList;
    }

    public static List<String> getInternalMonthStr(Date startDate, Date endDate) {
        LocalDate beginDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate lastDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return getInternalDateStr(beginDate, lastDate, "yyyyMM", ChronoUnit.MONTHS);
    }


    public static List<String> getInternalDayStr(Date startDate, Date endDate) {
        LocalDate beginDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate lastDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return getInternalDateStr(beginDate, lastDate, "yyyyMMdd", ChronoUnit.DAYS);
    }
}