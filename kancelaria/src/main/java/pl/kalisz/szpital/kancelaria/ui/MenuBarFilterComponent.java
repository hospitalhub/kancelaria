package pl.kalisz.szpital.kancelaria.ui;

import java.util.HashMap;

import org.vaadin.gridutil.cell.CellFilterComponent;

import com.vaadin.server.Resource;
import com.vaadin.ui.Component;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;

@SuppressWarnings("serial")
public class MenuBarFilterComponent extends CellFilterComponent<Component> {

  MenuBar bar = new MenuBar();

  public MenuBarFilterComponent(Command c, HashMap<String, Resource> items) {
    for (String s : items.keySet()) {
      bar.addItem(s, items.get(s), c);
    }
  }

  @Override
  public Component layoutComponent() {
    return bar;
  }

  @Override
  public void clearFilter() {}

}

