package com.github.aracwong.commons;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.Locale;

public class DateKit {

    /**
     * LocalDate转 Date
     *
     * @param localDate
     * @return
     */
    public static Date toDate(LocalDate localDate) {
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        Instant instant = zonedDateTime.toInstant();
        return Date.from(instant);
    }

    /**
     * LocalDateTime转Date
     *
     * @param localDateTime
     * @return
     */
    public static Date toDate(LocalDateTime localDateTime) {
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        Instant instant = zonedDateTime.toInstant();
        return Date.from(instant);
    }

    /**
     * Date转LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate toLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
        return zonedDateTime.toLocalDate();
    }

    /**
     * Date转LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
        return zonedDateTime.toLocalDateTime();
    }

    /**
     * 获取一天的开始时间
     *
     * @param localDate
     * @return
     */
    public static LocalDateTime getStartOfDay(LocalDate localDate) {
        return LocalDateTime.of(localDate, LocalTime.MIN);
    }

    /**
     * 获取一天的结束时间
     *
     * @param localDate
     * @return
     */
    public static LocalDateTime getEndOfDay(LocalDate localDate) {
        return LocalDateTime.of(localDate, LocalTime.MAX);
    }

    /**
     * 获取一周的第一天
     *
     * @param localDate
     * @return
     */
    public static LocalDate getFirstDayOfWeek(LocalDate localDate) {
        TemporalField temporalField = WeekFields.of(Locale.CHINA).dayOfWeek();
        LocalDate firstDayOfWeek = localDate.with(temporalField, 1);
        return firstDayOfWeek;
    }

    /**
     * 获取一周的最后一天
     *
     * @param localDate
     * @return
     */
    public static LocalDate getLastDayOfWeek(LocalDate localDate) {
        TemporalField temporalField = WeekFields.of(Locale.CHINA).dayOfWeek();
        LocalDate lastDayOfWeek = localDate.with(temporalField, 7);
        return lastDayOfWeek;
    }

    /**
     * 获取给定月的第一天
     *
     * @param localDate
     * @return
     */
    public static LocalDate getFirstDayOfMonth(LocalDate localDate) {
        LocalDate firstDayOfMonth = localDate.with(TemporalAdjusters.firstDayOfMonth());
        return firstDayOfMonth;
    }

    /**
     * 获取给定月的最后一天
     *
     * @param localDate
     * @return
     */
    public static LocalDate getLastDayOfMonth(LocalDate localDate) {
        LocalDate lastDayOfMonth = localDate.with(TemporalAdjusters.lastDayOfMonth());
        return lastDayOfMonth;
    }

}
