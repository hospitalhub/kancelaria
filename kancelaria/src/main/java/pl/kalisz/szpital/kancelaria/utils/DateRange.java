package pl.kalisz.szpital.kancelaria.utils;

import java.util.Date;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.filter.Between;

import pl.kalisz.szpital.kancelaria.data.pojo.Transaction;

public class DateRange {

  private static Date getStart(DateRangeEnum drEnum) {
    DateUtils.update();
    switch (drEnum) {
      case TODAY:
        return DateUtils.getToday();
      case YESTERDAY:
        return DateUtils.getYesterday();
      case CURRENT_WEEK:
        return DateUtils.getWeekAgo();
      case CURRENT_MONTH:
        return DateUtils.getFirstDayOfMonth();
      case PREVIOUS_MONTH:
        return DateUtils.getLastMonth();
      case CURRENT_YEAR:
        return DateUtils.getFirstDayOfTheYear();
      case ALL_TIME:
        return DateUtils.get1900();
      case PREVIOUS_YEAR:
        return DateUtils.getLastYear();
    }
    return new Date();
  }

  private static Date getEnd(DateRangeEnum drEnum) {
    DateUtils.update();
    switch (drEnum) {
      case YESTERDAY:
        return DateUtils.getToday();
      case PREVIOUS_YEAR:
        return DateUtils.getFirstDayOfTheYear();
      case PREVIOUS_MONTH:
        return DateUtils.getFirstDayOfMonth();
      default:
        return DateUtils.getTonight();
    }
  }

  public static Filter getFilter(DateRangeEnum drEnum) {
    return new Between(Transaction.DATA_STR, getStart(drEnum), getEnd(drEnum));
  }

  public static Filter getFilter(Date begin, Date end) {
    return new Between(Transaction.DATA_STR, begin, end);
  }

}
