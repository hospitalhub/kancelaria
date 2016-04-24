package pl.kalisz.szpital.kancelaria.utils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * The Class DateUtils.
 */
public class DateUtils {

  /** The Constant YMD. */
  public static final SimpleDateFormat YMD = new SimpleDateFormat(Strings.YYYY_MM_DD);

  /** The Constant DMY. */
  public static final SimpleDateFormat DMY = new SimpleDateFormat(Strings.DD_MM_YYYY);

  /** The Constant D_M_Y. */
  public static final SimpleDateFormat D_M_Y = new SimpleDateFormat(Strings.DD_MM_YYYY2);

  /** The Constant dmyhmsS. */
  public static final SimpleDateFormat DMYHMSS = new SimpleDateFormat(Strings.DDMMYYYYHHMMSSSSS);

  /** The Constant DdashMdashY. */
  public static final SimpleDateFormat DMY_DASH = new SimpleDateFormat(Strings.DD_MM_YYYY_DASH);

  /**
   * Format.
   *
   * @param pattern the pattern
   * @param date the date
   * @return the string
   */
  public static final String format(String pattern, Date date) {
    return new SimpleDateFormat().format(date);
  }
  //
  // /**
  // * Gets the begginning.
  // *
  // * @param d the d
  // * @return the begginning
  // */
  // @Deprecated
  // public static final Date getBegginning(Date d) {
  // Calendar calendar = new GregorianCalendar();
  // calendar.setTime(d);
  // calendar.set(Calendar.HOUR_OF_DAY, 0);
  // calendar.set(Calendar.MINUTE, 0);
  // calendar.set(Calendar.SECOND, 0);
  // return calendar.getTime();
  // }
  //
  // /**
  // * Gets the end.
  // *
  // * @param d the d
  // * @return the end
  // */
  // @Deprecated
  // public static final Date getEnd(Date d) {
  // Calendar calendar = new GregorianCalendar();
  // calendar.setTime(d);
  // calendar.set(Calendar.HOUR_OF_DAY, 23);
  // calendar.set(Calendar.MINUTE, 59);
  // calendar.set(Calendar.SECOND, 59);
  // return calendar.getTime();
  // }

  public static Date getFirstDayOfTheYear() {
    LocalDateTime firstDayOfTheYear =
        timePoint.withDayOfYear(1).withHour(0).withMinute(0).withSecond(0);
    Instant instant = firstDayOfTheYear.atZone(ZoneId.systemDefault()).toInstant();
    return Date.from(instant);
  }

  public static Date getFirstDayOfMonth() {
    LocalDateTime firstDayOfTheYear =
        timePoint.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
    Instant instant = firstDayOfTheYear.atZone(ZoneId.systemDefault()).toInstant();
    return Date.from(instant);
  }

  public static Date getWeekAgo() {
    LocalDateTime firstDayOfTheYear =
        timePoint.minusDays(7).withHour(0).withMinute(0).withSecond(0);
    Instant instant = firstDayOfTheYear.atZone(ZoneId.systemDefault()).toInstant();
    return Date.from(instant);
  }

  public static Date getYesterday() {
    LocalDateTime firstDayOfTheYear =
        timePoint.minusDays(1).withHour(0).withMinute(0).withSecond(0);
    Instant instant = firstDayOfTheYear.atZone(ZoneId.systemDefault()).toInstant();
    return Date.from(instant);
  }

  public static Date getToday() {
    Instant instant = timePoint.withHour(0).withMinute(0).withSecond(0)
        .atZone(ZoneId.systemDefault()).toInstant();
    return Date.from(instant);
  }

  public static Date getTonight() {
    Instant instant = timePoint.withHour(23).withMinute(59).withSecond(59)
        .atZone(ZoneId.systemDefault()).toInstant();
    return Date.from(instant);
  }

  public static Date get1900() {
    Instant instant = timePoint.withYear(1900).withDayOfYear(1).withHour(0).withMinute(0)
        .withSecond(0).atZone(ZoneId.systemDefault()).toInstant();
    return Date.from(instant);
  }

  public static Date getLastYear() {
    LocalDateTime firstDayOfTheYear =
        timePoint.minusYears(1).withDayOfYear(1).withHour(0).withMinute(0).withSecond(0);
    Instant instant = firstDayOfTheYear.atZone(ZoneId.systemDefault()).toInstant();
    return Date.from(instant);
  }

  public static Date getLastMonth() {
    LocalDateTime firstDayOfTheYear =
        timePoint.minusMonths(1).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
    Instant instant = firstDayOfTheYear.atZone(ZoneId.systemDefault()).toInstant();
    return Date.from(instant);
  }

  public static void update() {
    timePoint = LocalDateTime.now();
  }

  private static LocalDateTime timePoint = LocalDateTime.now();


}
