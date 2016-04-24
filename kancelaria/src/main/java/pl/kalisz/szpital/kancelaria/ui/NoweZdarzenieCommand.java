package pl.kalisz.szpital.kancelaria.ui;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.cdi.UIScoped;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;

import pl.kalisz.szpital.kancelaria.data.db.ContainerFactory;
import pl.kalisz.szpital.kancelaria.data.pojo.Address;
import pl.kalisz.szpital.kancelaria.data.pojo.Transaction;
import pl.kalisz.szpital.kancelaria.data.pojo.TransactionBuilder;
import pl.kalisz.szpital.kancelaria.ui.editor.transaction.TransactionWindow;

/**
 * The Class NoweZdarzenieCommand.
 */
@SuppressWarnings("serial")
@UIScoped
public class NoweZdarzenieCommand implements MenuBar.Command {

  /** The Constant logger. */
  private static final Logger logger = Logger.getLogger(NoweZdarzenieCommand.class);

  @Inject
  ContainerFactory dbHelper;

  @Inject
  UserSettings userSettings;

  @Inject
  TransactionWindow transactionWindow;

  /*
   * (non-Javadoc)
   * 
   * @see com.vaadin.ui.MenuBar.Command#menuSelected(com.vaadin.ui.MenuBar.MenuItem)
   */
  @Override
  public void menuSelected(final MenuItem selectedItem) {
    try {
      Transaction tr = new TransactionBuilder().defaultTransaction(userSettings.getUser())
          .adres(new Address()).build();
      JPAContainer<Transaction> c = dbHelper.getContainer(dbHelper.TRANSACTION_CONTAINER);
      EntityItem<Transaction> ei = c.createEntityItem(tr);
      transactionWindow.setTransaction(ei);
      UI.getCurrent().addWindow(transactionWindow);
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
  }
}
