package pl.kalisz.szpital.kancelaria.ui.menu;

import java.util.Date;

import org.apache.log4j.Logger;
import org.vaadin.appfoundation.authentication.SessionHandler;
import org.vaadin.appfoundation.authentication.data.User;

import pl.kalisz.szpital.db.kancelaria.Transaction;
import pl.kalisz.szpital.db.kancelaria.TransactionFactory;
import pl.kalisz.szpital.kancelaria.KancelariaUI;

import com.vaadin.data.util.BeanItem;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;

/**
 * The Class NoweZdarzenieCommand.
 */
@SuppressWarnings("serial")
public class NewTransactionCommand implements MenuBar.Command {

	/** The Constant logger. */
	private static final Logger logger = Logger
			.getLogger(NewTransactionCommand.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vaadin.ui.MenuBar.Command#menuSelected(com.vaadin.ui.MenuBar.MenuItem
	 * )
	 */
	@Override
	public void menuSelected(final MenuItem selectedItem) {
		String type = selectedItem.getText();
		try {
			User u = SessionHandler.get();
			Transaction lastTransaction = (Transaction) VaadinSession
					.getCurrent().getAttribute("lastTransaction");
			Transaction t = new TransactionFactory(u, lastTransaction)
					.getTransaction(type);
			t.setData(new Date());
			BeanItem<Transaction> item = new BeanItem<Transaction>(t);
			VaadinSession.getCurrent().setAttribute(KancelariaUI.TRANSACTION_EDITOR,item);
//			new TransactionEditorWindow(item);
			UI.getCurrent().getNavigator().navigateTo(KancelariaUI.TRANSACTION_EDITOR);
			System.out.println("NAV TO TRAN ED");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}
