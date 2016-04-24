package pl.kalisz.szpital.kancelaria.ui;

import javax.inject.Inject;

import com.vaadin.cdi.UIScoped;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;

import pl.kalisz.szpital.kancelaria.data.pojo.Transaction;
import pl.kalisz.szpital.kancelaria.utils.DateRangeEnum;
import pl.kalisz.szpital.kancelaria.utils.TransactionGridFilterBuilder;

@SuppressWarnings("serial")
@UIScoped
public class FilterCommand implements Command {

  @Inject
  TransactionGrid grid;

  @Inject
  UserSettings userSettings;

  @Inject
  TransactionGridFilterBuilder filters;

  @Inject
  BetweenDatesFilter betweenDatesFilter;

  private void clearSelection() {
    for (Object o : grid.getSelectedRows()) {
      grid.deselect(o);
    }
  }

  public void applyFilters() {
    grid.getFilter().clearAllFilters();
    filters.applyFilters();
    grid.sort(Transaction.DATA_STR, SortDirection.DESCENDING);
    clearSelection();
  }

  @Override
  public void menuSelected(final MenuItem selectedItem) {
    // set caption
    Enum filter = FilterEnumFactory.getFilter(selectedItem.getText());
    if (selectedItem.getParent() != null) {
      selectedItem.getParent().setText(filter.name());
    }
    if (selectedItem.getText().equals(DateRangeEnum.BETWEEN_DATES.getName())) {
      UI.getCurrent().addWindow(betweenDatesFilter);
    } 
    filters.set(filter);
    applyFilters();
  }
}
