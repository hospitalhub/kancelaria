package pl.kalisz.szpital.kancelaria.ui;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.vaadin.gridutil.cell.GridCellFilter;

import com.vaadin.server.Resource;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

import pl.kalisz.szpital.kancelaria.data.enums.TypWiadomosci;
import pl.kalisz.szpital.kancelaria.data.pojo.Transaction;

@SuppressWarnings("serial")
final class TypWiadomosciFilterCommand implements Command {

  final GridCellFilter filter;
  
  public TypWiadomosciFilterCommand(GridCellFilter filter) {
    this.filter = filter;
  }

  @Override
  public void menuSelected(MenuItem selectedItem) {
    TypWiadomosci tp = TypWiadomosci.getByTytul(selectedItem.getText());
    filter.replaceFilter(tp.getFilter(), Transaction.TYP_WIADOMOSCI);
  }

  public HashMap<String, Resource> getItems() {
    HashMap<String, Resource> typWiadomosciItems = new LinkedHashMap<>();
    for (TypWiadomosci tw : TypWiadomosci.values()) {
      typWiadomosciItems.put(tw.getNazwa(), tw.getIcon());
    }
    return typWiadomosciItems;
  }

}
