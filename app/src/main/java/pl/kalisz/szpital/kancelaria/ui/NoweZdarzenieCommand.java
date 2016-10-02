package pl.kalisz.szpital.kancelaria.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.kalisz.szpital.kancelaria.DashboardUI;
import pl.kalisz.szpital.kancelaria.data.pojo.Transaction;
import pl.kalisz.szpital.kancelaria.data.pojo.TransactionFactory;
import pl.kalisz.szpital.kancelaria.data.pojo.User;
import pl.kalisz.szpital.kancelaria.ui.editor.TransactionEditorWindow;

import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.MenuBar.MenuItem;

/**
 * The Class NoweZdarzenieCommand.
 */
@SuppressWarnings("serial")
public class NoweZdarzenieCommand implements MenuBar.Command {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(NoweZdarzenieCommand.class);

  /*
   * (non-Javadoc)
   * 
   * @see com.vaadin.ui.MenuBar.Command#menuSelected(com.vaadin.ui.MenuBar.MenuItem)
   */
  @Override
  public void menuSelected(final MenuItem selectedItem) {
    String type = selectedItem.getText();
    try {
      DashboardUI dUI = (DashboardUI) UI.getCurrent();
      User u = dUI.getUser();
      Transaction lastTransaction = dUI.getLastTransaction();
      Transaction t = new TransactionFactory(u, lastTransaction).getTransaction(type);
      new TransactionEditorWindow(t, true);
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
  }
}
