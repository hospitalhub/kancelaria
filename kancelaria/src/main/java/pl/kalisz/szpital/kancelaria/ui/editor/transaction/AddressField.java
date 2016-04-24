package pl.kalisz.szpital.kancelaria.ui.editor.transaction;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

import pl.kalisz.szpital.kancelaria.DashboardUI;
import pl.kalisz.szpital.kancelaria.ui.search.AdresSearchWindow;

@SuppressWarnings("serial")

public class AddressField<Adres> extends CustomField<Adres> {

  // AdresSearchWindow adresSearchWindow;
  
  public boolean edited = false;

  @SuppressWarnings("unchecked")
  public AddressField() {
    super();
    field = new TextArea();
    button.setIcon(FontAwesome.HOME);

    field.setEnabled(false);
    field.setConverter(new ObjectConvereter());

    field.setSizeFull();
    button.setSizeFull();

    addComponent(field);
    addComponent(button);

    ((TextArea) field).setRows(4);

    setComponentAlignment(button, Alignment.BOTTOM_RIGHT);

    button.addClickListener(new ClickListener() {

      @Override
      public void buttonClick(ClickEvent event) {
        AdresSearchWindow adresSearchWindow =
            ((DashboardUI) UI.getCurrent()).getAdresSearchWindow();

        UI.getCurrent().addWindow(adresSearchWindow);
        adresSearchWindow.addCloseListener(new CloseListener() {

          @Override
          public void windowClose(CloseEvent e) {
            setValue((Adres) adresSearchWindow.getAdres());
            edited = adresSearchWindow.isAdresEdited();
          }
        });
      }
    });
  }

}
