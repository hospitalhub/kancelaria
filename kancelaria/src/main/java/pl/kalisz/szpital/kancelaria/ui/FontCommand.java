package pl.kalisz.szpital.kancelaria.ui;

import com.vaadin.cdi.UIScoped;
import com.vaadin.server.Page;
import com.vaadin.server.Page.Styles;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

@UIScoped
public class FontCommand implements Command {
  @Override
  public void menuSelected(MenuItem selectedItem) {
    String fontFamily = selectedItem.getText();
    Styles styles = Page.getCurrent().getStyles();
    styles.add(".v-app { font:" + fontFamily + " !important; }");
  }
}