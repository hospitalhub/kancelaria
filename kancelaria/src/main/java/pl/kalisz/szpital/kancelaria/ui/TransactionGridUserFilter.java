package pl.kalisz.szpital.kancelaria.ui;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.vaadin.gridutil.cell.CellFilterComponent;

import com.vaadin.cdi.UIScoped;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

import pl.kalisz.szpital.kancelaria.utils.OrganisationEnum;

@SuppressWarnings("serial")
@UIScoped
public class TransactionGridUserFilter extends CellFilterComponent<MenuBar> {

  @Inject
  FilterCommand command;

  @Inject
  TransactionGrid grid;

  @PostConstruct
  public void init() {
//    grid.getFilter().setCustomFilter(TransactionGrid.BUTTONS, this);
  }

  @Override
  public MenuBar layoutComponent() {
    MenuBar menu = new MenuBar();
    Set<String> names = OrganisationEnum.names();
    String firstCaption = names.iterator().next();
    MenuItem filtersCommand = menu.addItem(firstCaption, FontAwesome.MINUS_SQUARE_O, null);
    for (String key : names) {
      filtersCommand.addItem(key, FontAwesome.CALENDAR_O, command);
    }
    return menu;
  }

  @Override
  public void clearFilter() {
    
  }
}
