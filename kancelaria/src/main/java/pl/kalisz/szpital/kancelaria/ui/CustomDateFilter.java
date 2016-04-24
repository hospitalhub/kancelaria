package pl.kalisz.szpital.kancelaria.ui;

import java.util.Date;

import org.vaadin.gridutil.cell.GridCellFilter;
import org.vaadin.gridutil.cell.RangeCellFilterComponent;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup.CommitEvent;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitHandler;
import com.vaadin.data.util.filter.Between;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;

final class CustomDateFilter extends RangeCellFilterComponent<HorizontalLayout> {


  private final static Date MIN_DATE_VALUE = new Date(0); // 1970-01-01 00:00:00
  private final static Date MAX_DATE_VALUE = new Date(32503676399000L); // 2999-12-31 23:59:59
  private final static long END_OF_THE_DAY = 24L * 60 * 60 * 1000 - 1;


  private final Object columnId;

  private final GridCellFilter cellFilter;

  public CustomDateFilter(Object columnId, GridCellFilter cellFilter) {
    this.columnId = columnId;
    this.cellFilter = cellFilter;
  }

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private DateField genDateField(final String propertyId) {
    final DateField dateField = new DateField();
    getFieldGroup().bind(dateField, propertyId);
    dateField.setWidth("100%");
    dateField.setImmediate(true);
    dateField.setInvalidAllowed(false);
    dateField.setInvalidCommitted(false);
    dateField.setResolution(Resolution.DAY);
    dateField.addStyleName(ValoTheme.DATEFIELD_TINY);
    dateField.addValueChangeListener(new ValueChangeListener() {

      private static final long serialVersionUID = 9147606627660840906L;

      @Override
      public void valueChange(final ValueChangeEvent event) {
        try {
          if (dateField.isValid()) {
            dateField.setComponentError(null);
            getFieldGroup().commit();
          }
        } catch (CommitException e) {
        }
      }
    });
    return dateField;
  }

  @Override
  public HorizontalLayout layoutComponent() {
    getFieldGroup().setItemDataSource(genPropertysetItem(Date.class));

    DateField smallest = genDateField("smallest");
    DateField biggest = genDateField("biggest");
    getHLayout().addComponent(smallest);
    getHLayout().addComponent(biggest);
    getHLayout().setExpandRatio(smallest, 1);
    getHLayout().setExpandRatio(biggest, 1);

    initCommitHandler();

    return getHLayout();
  }

  private void initCommitHandler() {
    getFieldGroup().addCommitHandler(new CommitHandler() {

      private static final long serialVersionUID = 2617591142986829655L;

      @Override
      public void preCommit(final CommitEvent commitEvent) throws CommitException {}

      @Override
      public void postCommit(final CommitEvent commitEvent) throws CommitException {
        Date smallestValue =
            (Date) getFieldGroup().getItemDataSource().getItemProperty("smallest").getValue();
        Date biggestValue =
            (Date) getFieldGroup().getItemDataSource().getItemProperty("biggest").getValue();
        if (smallestValue != null || biggestValue != null) {
          biggestValue = new Date(biggestValue.getTime() + END_OF_THE_DAY);
          cellFilter.replaceFilter(
              new Between(columnId, smallestValue != null ? smallestValue : MIN_DATE_VALUE,
                  biggestValue != null ? biggestValue : MAX_DATE_VALUE),
              columnId);
        } else {
          cellFilter.removeFilter(columnId);
        }
      }
    });
  }

  @Override
  public void clearFilter() {
    getFieldGroup().clear();
  }


}
