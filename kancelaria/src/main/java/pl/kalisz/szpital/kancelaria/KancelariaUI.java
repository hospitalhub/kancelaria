package pl.kalisz.szpital.kancelaria;

import java.util.HashMap;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import org.vaadin.appfoundation.authentication.SessionHandler;
import org.vaadin.appfoundation.authorization.Permissions;
import org.vaadin.appfoundation.authorization.jpa.JPAPermissionManager;

import pl.kalisz.szpital.db.DbHelper;
import pl.kalisz.szpital.kancelaria.converters.MyConverterFactory;
import pl.kalisz.szpital.kancelaria.ui.editor.TransactionEditorView;
import pl.kalisz.szpital.kancelaria.ui.views.AddressSearchView;
import pl.kalisz.szpital.kancelaria.ui.views.LoginView;
import pl.kalisz.szpital.kancelaria.ui.views.LogoutView;
import pl.kalisz.szpital.kancelaria.ui.views.TransactionView;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.server.VaadinCDIServlet;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

/**
 * The Class KancelariaUI.
 */
@SuppressWarnings("serial")
@Theme("reindeer")
@Title("WSZ Sekretariat")
@CDIUI("")
public class KancelariaUI extends UI {

	public static final String TRANSACTION_EDITOR = "transactionEditor";

	public static final String ADDRESS_SEARCH = "addressSearch";

	public static final String LOGOUT = "logout";

	public static final String LOGIN = "login";

	public static final String ZDARZENIA = "zdarzenia";

	@WebServlet(value = "/*", asyncSupported = false, initParams = {
			@WebInitParam(name = "session-timeout", value = "60"),
			@WebInitParam(name = "widgetset", value = "pl.kalisz.szpital.hospitalwidgetset.HospitalWidgetSet"),
			@WebInitParam(name = "productionMode", value = "false") })
	public static class Servlet extends VaadinCDIServlet {
	}

	@Inject
	private DbHelper dbHelper;

	private Navigator navigator;

	private HashMap<String, Class<? extends View>> routes = new HashMap<String, Class<? extends View>>() {
		{
			put(ZDARZENIA, TransactionView.class);
			put(LOGIN, LoginView.class);
			put(LOGOUT, LogoutView.class);
			put(ADDRESS_SEARCH, AddressSearchView.class);
			put(TRANSACTION_EDITOR, TransactionEditorView.class);
		}
	};

	/**
	 * Gets the db helper.
	 * 
	 * @return the db helper
	 */
	public final DbHelper getDbHelper() {
		return dbHelper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.ui.UI#init(com.vaadin.server.VaadinRequest)
	 */
	@Override
	protected final void init(final VaadinRequest request) {
		SessionHandler.initialize(this);
		Permissions.initialize(this, new JPAPermissionManager());
		getSession().setConverterFactory(new MyConverterFactory());
		VaadinService.getCurrent().setSystemMessagesProvider(
				new MySystemMessagesProvider());
		setLocale(new Locale("pl", "PL"));

		navigator = new Navigator(this, this);
		for (String route : routes.keySet()) {
			navigator.addView(route, routes.get(route));
		}
		navigator.navigateTo(ZDARZENIA);
	}

}
