package pl.kalisz.szpital.kancelaria.ui;

import javax.inject.Inject;

import com.vaadin.cdi.UIScoped;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import pl.kalisz.szpital.kancelaria.utils.Strings;

@SuppressWarnings("serial")
@UIScoped
public class UserCommand implements Command {

  @Inject
  private javax.enterprise.event.Event<NavigationEvent> navigationEvent;

  private void pokazUstawienia() {
    Window w = new Window();
    w.setResizable(false);
    UI.getCurrent().addWindow(w);
    VerticalLayout h = new VerticalLayout();
    // if (((DashboardUI) UI.getCurrent()).getUser().hasRole(Role.ADMIN)) {
    // h.addComponent(new OCRProcessor().ocrujPliki(transactions));
    // }
    w.setContent(h);
  }


  @Override
  public void menuSelected(final MenuItem selectedItem) {
    if (selectedItem.getText().equals(Strings.WYLOGUJ)) {
      navigationEvent.fire(new NavigationEvent("logout"));
    } else if (selectedItem.getText().equals(Strings.USTAWIENIA)) {
      pokazUstawienia();
    }
  }
}
