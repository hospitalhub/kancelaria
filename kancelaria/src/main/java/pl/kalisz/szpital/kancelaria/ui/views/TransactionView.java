package pl.kalisz.szpital.kancelaria.ui.views;

import org.vaadin.appfoundation.authentication.SessionHandler;

import pl.kalisz.szpital.db.DbHelper;
import pl.kalisz.szpital.db.kancelaria.Transaction;
import pl.kalisz.szpital.kancelaria.KancelariaUI;
import pl.kalisz.szpital.kancelaria.data.filerepo.FileLocationContext;
import pl.kalisz.szpital.kancelaria.ui.menu.Toolbar;
import pl.kalisz.szpital.kancelaria.ui.tables.TransactionTable;
import pl.kalisz.szpital.kancelaria.ui.tables.TransactionTableControls;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * The Class TransactionView.
 */
@SuppressWarnings("serial")
public class TransactionView extends VerticalLayout implements View {

	/** The file ctx. */
	final FileLocationContext fileCtx = new FileLocationContext();

	/** The filter generator. */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.navigator.View#enter
	 */
	@SuppressWarnings("unchecked")
	@Override
	public final void enter(final ViewChangeEvent event) {
		if (!isLoggedIn()) {
			UI.getCurrent().getNavigator().navigateTo(KancelariaUI.LOGIN);
			return;
		}

		KancelariaUI ui = (KancelariaUI) getUI();
		DbHelper dbHelper = ui.getDbHelper();
		JPAContainer<Transaction> container = (JPAContainer<Transaction>) dbHelper
				.getContainer(DbHelper.TRANSACTION_CONTAINER);
		TransactionTable table = new TransactionTable(container);
		setSizeFull();

		addComponent(new Toolbar(table, container));
		addComponent(table.createControls(new TransactionTableControls()));
		addComponent(table);
		setExpandRatio(table, 1);
	}

	private boolean isLoggedIn() {
		return (SessionHandler.get() != null);
	}

}
