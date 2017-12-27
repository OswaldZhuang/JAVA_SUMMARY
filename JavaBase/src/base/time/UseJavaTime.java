package base.time;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.Year;
import java.time.YearMonth;

//Java8新增了java.time 包
public class UseJavaTime {
    //用于获取指定市区的当前日期,时间
    static Clock clock;
    //表示一段时间
    static Duration duration;
    //表示某个时刻,可以精确到纳秒
    static Instant instant;
    //代表不带时区的日期,比如：2017-11-19
    static LocalDate localDate;
    //代表不带时区的时间,比如：10:25:01.292
    static LocalTime localTime;
    //代表不带时区的日期,时间,比如：2017-11-19T10:23:45.991
    static LocalDateTime localDateTime;
    //月日
    static MonthDay monthDay;
    //年月
    static YearMonth yearMonth;
    //年
    static Year year;
    //月
    static Month month;
    
    static void method() {
        clock = Clock.systemUTC();
        duration = Duration.ofSeconds(6000L);
        instant = Instant.now();
        localDate = LocalDate.now();
        localTime = LocalTime.now();
        localDateTime = LocalDateTime.now();
        System.out.println(localTime);
    }
    
    public static void main(String[] args) {
        method();
    }
}
