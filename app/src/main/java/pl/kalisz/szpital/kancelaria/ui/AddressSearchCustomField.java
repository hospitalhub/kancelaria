package pl.kalisz.szpital.kancelaria.ui;

import pl.kalisz.szpital.kancelaria.data.pojo.Adres;
import pl.kalisz.szpital.kancelaria.ui.search.AdresSearchWindow;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

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
    final AdresSearchWindow adresSearchWindow = new AdresSearchWindow();
    UI.getCurrent().addWindow(adresSearchWindow);
    adresSearchWindow.addCloseListener(new CloseListener() {

      @Override
      public void windowClose(final CloseEvent e) {
        Adres adres = adresSearchWindow.getAdres();
        if (adres != null) {
          setValue(adres);
        }
      }
    });
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.vaadin.ui.CustomField#initContent()
   */
  @Override
  protected final Component initContent() {
    final Button button = new Button("wyszukaj po adresie");
    button.addClickListener(new ClickListener() {

      @Override
      public void buttonClick(final ClickEvent event) {
        openAdresSearch();
      }
    });
    return button;
  }
};
