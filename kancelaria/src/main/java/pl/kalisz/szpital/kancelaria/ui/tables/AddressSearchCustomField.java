package pl.kalisz.szpital.kancelaria.ui.tables;

import pl.kalisz.szpital.db.kancelaria.Adres;
import pl.kalisz.szpital.kancelaria.ui.views.AddressSearchView;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

/**
 * The Class AddressSearchCustomField.
 */
@SuppressWarnings("serial")
public class AddressSearchCustomField extends CustomField<Adres> {

  /*
   * (non-Javadoc)
   * 
   * @see com.vaadin.ui.AbstractField#getType()
   */
  @Override
  public final Class<? extends Adres> getType() {
    return Adres.class;
  }

  /**
   * Open adres search.
   */
  protected final void openAdresSearch() {
    final AddressSearchView adresSearchWindow = new AddressSearchView();
//    UI.getCurrent().addWindow(adresSearchWindow);
    UI.getCurrent().getNavigator().navigateTo("addressSearch");
    Notification.show("NO CLOSE LISTENER");
//    adresSearchWindow.addCloseListener(new CloseListener() {

//      @Override
//      public void windowClose(final CloseEvent e) {
//        Adres adres = adresSearchWindow.getAdres();
//        if (adres != null) {
//          setValue(adres);
//        }
//      }
//    });
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.vaadin.ui.CustomField#initContent()
   */
  @Override
  protected final Component initContent() {
    final Button button = new Button("wyszukaj po adresie");
    button.setSizeFull();
    button.addClickListener(new ClickListener() {

      @Override
      public void buttonClick(final ClickEvent event) {
        openAdresSearch();
      }
    });
    return button;
  }
};
