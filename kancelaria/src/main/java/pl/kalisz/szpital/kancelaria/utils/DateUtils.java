package pl.kalisz.szpital.kancelaria.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import pl.kalisz.szpital.utils.Strings;

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

  /**
   * Gets the begginning.
   *
   * @param d the d
   * @return the begginning
   */
  public static final Date getBegginning(Date d) {
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(d);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    return calendar.getTime();
  }

  /**
   * Gets the end.
   *
   * @param d the d
   * @return the end
   */
  public static final Date getEnd(Date d) {
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(d);
    calendar.set(Calendar.HOUR_OF_DAY, 23);
    calendar.set(Calendar.MINUTE, 59);
    calendar.set(Calendar.SECOND, 59);
    return calendar.getTime();
  }

}
