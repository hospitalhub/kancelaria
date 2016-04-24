package pl.kalisz.szpital.kancelaria.ui.editor.transaction;

import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

import pl.kalisz.szpital.kancelaria.DashboardUI;
import pl.kalisz.szpital.kancelaria.data.pojo.Address;
import pl.kalisz.szpital.kancelaria.ui.search.AdresSearchWindow;

@Deprecated
@SuppressWarnings("serial")
final class DEPAddressClickListener extends TransactionFormButtonListener {

  public DEPAddressClickListener(TransactionWindow window) {
    super(window);
  }

  @Override
  public void buttonClick(ClickEvent event) {
   showAdresSearchWindow(); 
  }
  
  /**
   * Show adres search window.
   */
  private void showAdresSearchWindow() {
    final DashboardUI dashboardUI = (DashboardUI) UI.getCurrent();

    final AdresSearchWindow adresSearchWindow = new AdresSearchWindow();
    /*adresSearchWindow.addCloseListener(new CloseListener() {
      @Override
      public void windowClose(final CloseEvent e) {
        boolean adressesEdited;
        Adres adres = adresSearchWindow.getAdres();
        adressesEdited = adresSearchWindow.isAdresEdited();
        if (adres != null) {
//          setAdres(adres);
        }
      }
    });*/
//    dashboardUI.addWindow(adresSearchWindow);
  }
}