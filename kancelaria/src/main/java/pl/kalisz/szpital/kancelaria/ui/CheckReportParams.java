package pl.kalisz.szpital.kancelaria.ui;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.vaadin.gridutil.cell.GridCellFilter;

import com.vaadin.cdi.UIScoped;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

import pl.kalisz.szpital.kancelaria.data.pojo.Transaction;

@SuppressWarnings("serial")
@UIScoped
class CheckReportParams implements Serializable {

  private static final String MAX_7_DAYS_MSG =
      "Nie można wygenerować raportu dla okresu dłuższego niż 7 dni. Zmień datę początkową lub końcową";

  private final static String SET_DATE_MSG =
      "Ustaw date początkową i końcową raportu (w kolumnie Data) lub zaznacz wiersze do raportu";

  @Inject
  TransactionGrid grid;
  GridCellFilter gridCellFilter;

  @PostConstruct
  public void init() {
    gridCellFilter = grid.getFilter();
  }

  /**
   * 
   */
  public boolean checkParams() {
    if (!reportRowsSelected()) {
      if (!isDateFilterSet()) {
        Notification.show(SET_DATE_MSG, Type.WARNING_MESSAGE);
      } else {
        Date[] dateRange = getFilterDates();
        if (dateRange.length == 2 && dateRange[0] != null && dateRange[1] != null) {
          long days = daysDiffInclusive(dateRange);
          if (days > 6) {
            Notification.show(MAX_7_DAYS_MSG, Type.ERROR_MESSAGE);
          } else {
            // everything ok: dates are selected - report can be confirmed
            return true;
          }
        } else {
          Notification.show(SET_DATE_MSG, Type.WARNING_MESSAGE);
        }
      }
    } else {
      // everything ok: rows are selected - report can be confirmed
      return true;
    }
    return false;
  }

  private boolean reportRowsSelected() {
    return grid.getSelectedRows().size() > 0;
  }

  /**
   * days between (inclusive)
   * 
   * @param dateRange
   * @return
   */
  public long daysDiffInclusive(Date[] dateRange) {

    LocalDateTime d1Local =
        dateRange[0].toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay();

    // sets d2 inclusive (yyyy-mm-dd 23:59:59 instead of 00:00:00)
    LocalDateTime d2Local =
        dateRange[1].toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atTime(23, 59, 59, 0);

    Duration daysDiff = Duration.between(d1Local, d2Local);
    long days = daysDiff.toDays();

    return days;
  }

  /**
   * 
   * @return
   */
  private Date[] getFilterDates() {
    HorizontalLayout hl = (HorizontalLayout) gridCellFilter.getFilterRow()
        .getCell(Transaction.DATA_STR).getComponent();
    Iterator i = hl.getComponentIterator();
    Date d1 = null;
    Date d2 = null;
    while (i.hasNext()) {
      Object o = i.next();
      if (o instanceof DateField) {
        if (d1 == null) {
          d1 = ((DateField) o).getValue();
        } else {
          d2 = (((DateField) o).getValue());
        }
      }
    }
    return new Date[] { d1, d2 };
  }

  private boolean isDateFilterSet() {
    return gridCellFilter.filteredColumnIds().contains(Transaction.DATA_STR);
  }



}
