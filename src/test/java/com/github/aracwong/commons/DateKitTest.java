package com.github.aracwong.commons;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;

public class DateKitTest {

    /**
     * 示例1:Java 8中获取今天的日期
     */
    @Test
    public void now() {
        LocalDate now = LocalDate.now();
        System.out.println(now);
    }

    /**
     * 示例2:Java 8中获取年、月、日信息
     */
    @Test
    public void yearMonthDay() {
        LocalDate now = LocalDate.now(ZoneId.systemDefault());
        System.out.println(now.getYear());
        System.out.println(now.getMonth());
        System.out.println(now.getMonthValue());
        System.out.println(now.getDayOfMonth());
    }

    /**
     * 示例3:Java 8中处理特定日期
     */
    @Test
    public void of() {
        LocalDate localDate = LocalDate.of(2019, 9, 7);
        System.out.println(localDate);
    }

    /**
     * 示例4:Java 8中判断两个日期是否相等
     */
    @Test
    public void eq() {
        LocalDate now = LocalDate.now();
        LocalDate other = LocalDate.of(2019, 9, 1);
        System.out.println(now.equals(other));
    }

    /**
     * 示例5:Java 8中检查像生日这种周期性事件
     */
    @Test
    public void cycle() {
        LocalDate now = LocalDate.now();
        LocalDate other = LocalDate.of(2019, 2, 17);
        MonthDay monthDay = MonthDay.of(other.getMonth(), other.getDayOfMonth());
        MonthDay currentDay = MonthDay.from(now);
        System.out.println(currentDay.equals(monthDay));
    }

    /**
     * 示例6:Java 8中获取当前时间
     */
    @Test
    public void time() {
        LocalTime now = LocalTime.now();
        System.out.println(now);
    }

    /**
     * 示例7:Java 8中获取当前时间
     * 通过增加小时、分、秒来计算将来的时间很常见。Java 8除了不变类型和线程安全的好处之外，还提供了更好的plusHours()方法替换add()，并且是兼容的。注意，这些方法返回一个全新的LocalTime实例，由于其不可变性，返回后一定要用变量赋值
     */
    @Test
    public void hourMinuteSecond() {
        LocalTime localTime = LocalTime.now();
        LocalTime newLocalTime = localTime.plusHours(2);
        System.out.println(newLocalTime);
    }

    /**
     * 示例8:Java 8如何计算一周后的日期
     */
    @Test
    public void internalDays() {
        LocalDate now = LocalDate.now();
        LocalDate nextWeek = now.plus(1, ChronoUnit.WEEKS);
        System.out.println(nextWeek);
    }

    /**
     * 示例9:Java 8计算一年前或一年后的日期
     */
    @Test
    public void internalYears() {
        LocalDate now = LocalDate.now();
        System.out.println(now);
        LocalDate nextYear = now.plus(1, ChronoUnit.YEARS);
        System.out.println(nextYear);
    }

    /**
     * 示例10:Java 8的Clock时钟类
     * Java 8增加了一个Clock时钟类用于获取当时的时间戳，或当前时区下的日期时间信息。以前用到System.currentTimeInMillis()和TimeZone.getDefault()的地方都可用Clock替换。
     */

    @Test
    public void clock() {
        // Returns the current time based on your system clock and set to UTC.
        Clock clock = Clock.systemUTC();
        System.out.println(clock.millis());

        // Returns time based on system clock zone
        Clock defaultClock = Clock.systemDefaultZone();
        System.out.println(defaultClock.millis());
    }

    /**
     * 示例11:如何用Java判断日期是早于还是晚于另一个日期
     */
    @Test
    public void compare() {
        LocalDate now = LocalDate.now();
        LocalDate other = LocalDate.of(2019, 9, 1);
        System.out.println(now.isAfter(other));

        LocalDate yesterday = now.minus(1, ChronoUnit.DAYS);
        System.out.println(yesterday.isBefore(now));
    }

    /**
     * 示例12:Java 8中处理时区
     */
    @Test
    public void timezone() {
        // 调用ZoneId类的getAvailableZoneIds
        Set<String> set = ZoneId.getAvailableZoneIds();
//        set.forEach(System.out::println);
        System.out.println("==========================");
        System.out.println(LocalDate.now(ZoneId.of("America/Louisville")));


        System.out.println("====================================");
        // 以时区格式显示时间
        ZoneId america = ZoneId.of("Europe/Paris");
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, america);
        System.out.println(zonedDateTime);
        System.out.println(zonedDateTime.toLocalDateTime());
    }

    /**
     * 示例13:如何表示信用卡到期这类固定日期，答案就在YearMonth
     */
    @Test
    public void timeline() {
        YearMonth currentYearMonth = YearMonth.now();
        System.out.printf("Days in month year %s: %d%n", currentYearMonth, currentYearMonth.lengthOfMonth());
        YearMonth creditCardExpiry = YearMonth.of(2019, Month.FEBRUARY);
        System.out.printf("Your credit card expires on %s %n", creditCardExpiry);
    }

    /**
     * 示例14:如何在Java 8中检查闰年
     */
    @Test
    public void checkLeapYear() {
        LocalDate today = LocalDate.now();
        if(today.isLeapYear()){
            System.out.println("This year is Leap year");
        }else {
            System.out.println("2018 is not a Leap year");
        }
    }


    /**
     * 示例15:计算两个日期之间的天数和月数
     */
    @Test
    public void internalDaysAndMonth() {
        LocalDate today = LocalDate.now();
        System.out.println(today);
        LocalDate java8Release = LocalDate.of(2018, 11, 10);
        System.out.println(java8Release);
        Period periodToNextJavaRelease = Period.between(java8Release, today);
        System.out.println("Months left between today and Java 8 release : "
                + periodToNextJavaRelease.getDays());
    }

    /**
     * 示例16:在Java 8中获取当前的时间戳
     */
    @Test
    public void instant() {
        Instant instant = Instant.now();
        System.out.println(instant.toEpochMilli());
    }

    /**
     * 示例17:Java 8中如何使用预定义的格式化工具去解析或格式化日期
     */
    @Test
    public void format() {
        String dayAfterTomorrow = "20180205";
        LocalDate formatted = LocalDate.parse(dayAfterTomorrow,
                DateTimeFormatter.BASIC_ISO_DATE);
        System.out.println(dayAfterTomorrow + "  格式化后的日期为:  " + formatted);
    }

    /**
     * 示例18:字符串互转日期类型
     */
    @Test
    public void parse() {
        LocalDateTime date = LocalDateTime.now();

        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        //日期转字符串
        String str = date.format(format1);

        System.out.println("日期转换为字符串:"+str);

        DateTimeFormatter format2 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        //字符串转日期
        LocalDate date2 = LocalDate.parse(str,format2);
        System.out.println("日期类型:"+date2);
    }

    /**
     * DateKit
     */
    @Test
    public void testDateKit() {
        System.out.println(DateKit.toLocalDate(new Date()));
        System.out.println(DateKit.toLocalDateTime(new Date()));
        System.out.println(DateKit.toDate(LocalDate.now()));
        System.out.println(DateKit.toDate(LocalDateTime.now()));
        System.out.println(DateKit.getStartOfDay(LocalDate.now()));
        System.out.println(DateKit.getEndOfDay(LocalDate.now()));
        System.out.println(DateKit.getFirstDayOfWeek(LocalDate.now()));
        System.out.println(DateKit.getLastDayOfWeek(LocalDate.now()));
        System.out.println(DateKit.getFirstDayOfMonth(LocalDate.now()));
        System.out.println(DateKit.getLastDayOfMonth(LocalDate.now()));
    }

}
