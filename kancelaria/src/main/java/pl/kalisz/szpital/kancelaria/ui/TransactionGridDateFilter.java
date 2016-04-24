package pl.kalisz.szpital.kancelaria.ui;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.vaadin.gridutil.cell.CellFilterComponent;

import com.vaadin.cdi.UIScoped;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

import pl.kalisz.szpital.kancelaria.data.pojo.Transaction;
import pl.kalisz.szpital.kancelaria.utils.DateRangeEnum;

@SuppressWarnings("serial")
@UIScoped
public class TransactionGridDateFilter extends CellFilterComponent<MenuBar> {

  @Inject
  FilterCommand command;

  @Inject
  TransactionGrid grid;

  @PostConstruct
  public void init() {
//    grid.getFilter().setCustomFilter(Transaction.DATA_STR, this);
  }

  @Override
  public MenuBar layoutComponent() {
    MenuBar menu = new MenuBar();
    MenuItem main = menu.addItem("Wybierz", null);
    for (DateRangeEnum d : DateRangeEnum.values()) {
      main.addItem(d.getName(), d.getIcon(), command);
    }
    return menu;
  }

  @Override
  public void clearFilter() {}
}
