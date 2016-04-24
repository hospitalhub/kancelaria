package pl.kalisz.szpital.kancelaria.ui.menu;

import org.vaadin.appfoundation.authentication.SessionHandler;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.server.Page;
import com.vaadin.server.Page.Styles;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import pl.kalisz.szpital.db.kancelaria.Transaction;
import pl.kalisz.szpital.hospitalwidgetset.Theme;
import pl.kalisz.szpital.kancelaria.ui.tables.TransactionTable;
import pl.kalisz.szpital.utils.Strings;

@SuppressWarnings("serial")
public class Toolbar extends HorizontalLayout {

	// inject
	private JPAContainer<Transaction> container;

	// inject
	private TransactionTable table;

	private static final String PRE_STYLE_NON = ".v-table-cell-content.v-table-cell-content-pre>.v-table-cell-wrapper { white-space: nowrap !important; }";

	private static final String PRE_STYLE = ".v-label-pre, .v-table-cell-content.v-table-cell-content-pre>.v-table-cell-wrapper { white-space: pre !important; }";

	/** The title. */
	private Label title = new Label();

	/** The menu bar. */
	private final MenuBar menuBar = new MenuBar();

	public Toolbar(TransactionTable table, JPAContainer<Transaction> container) {
		this.table = table; 
		this.container = container;

		setWidth(Strings.PERCENT100);
		setSpacing(true);
		setMargin(true);
		addStyleName(Strings.TOOLBAR2);
		// title
		title.setSizeFull();
		title.setWidth(Strings.PERCENT100);
		addComponent(title);
		setExpandRatio(title, 6);
		//
		setPreStyle();
		createNewTransactionMenu();
		createThemeMenu();
		createClearMenu();
		createRaportyMenu();
		createUserMenu();

	}

	private void setPreStyle() {
		// break line stylees (adres \n)
		Styles styles = Page.getCurrent().getStyles();
		styles.add(PRE_STYLE);
	}

	/**
	 * Creates the nowe zdarzenie menu.
	 */
	private void createNewTransactionMenu() {
		// nowe zdarzenie
		addComponent(new HorizontalLayout() {
			{
				setSizeUndefined();
				addStyleName(Strings.USER);

				Command cmd = new NewTransactionCommand();
				MenuItem menu = menuBar.addItem(Strings.NEW_TRANSACTION, null);
				menu.setStyleName(Strings.ICON_SEARCH_1);
				menu.addItem(Strings.NEW_TRANSACTION, cmd);
				menu.addSeparator();
				menu.addItem(Strings.COPY_LAST, cmd);
				menu.addItem(Strings.COPY_LAST_WITH_SCAN, cmd);
				menu.addItem(Strings.COPY_ADDRESS, cmd);
				menu.addItem(Strings.COPY_TYPE, cmd);
			}
		});
	}

	/**
	 * Creates the nowe zdarzenie menu.
	 */
	private void createThemeMenu() {

		// nowe zdarzenie
		addComponent(new HorizontalLayout() {
			{
				setSizeUndefined();
				addStyleName(Strings.USER);

				Command cmdFont = new Command() {

					@Override
					public void menuSelected(MenuItem selectedItem) {
						String fontFamily = selectedItem.getText();
						Styles styles = Page.getCurrent().getStyles();
						styles.add(".v-app { font:" + fontFamily
								+ " !important; }");
					}
				};

				Command cmdInline = new Command() {

					@Override
					public void menuSelected(MenuItem selectedItem) {
						if (selectedItem.isChecked()) {
							Styles styles = Page.getCurrent().getStyles();
							styles.add(PRE_STYLE_NON);
						} else {
							Styles styles = Page.getCurrent().getStyles();
							styles.add(PRE_STYLE);
						}
					}
				};

				Command cmdBigger = new Command() {

					@Override
					public void menuSelected(MenuItem selectedItem) {
						if (selectedItem.isChecked()) {
							Styles styles = Page.getCurrent().getStyles();
							styles.add(".v-app .v-table-cell-wrapper, .v-checkbox { font-size: 130% !important; }");
						} else {
							Styles styles = Page.getCurrent().getStyles();
							styles.add(".v-app .v-table-cell-wrapper, .v-checkbox { font-size: 100% !important; }");
						}
					}
				};

				Command cmdTheme = new Command() {

					@Override
					public void menuSelected(MenuItem selectedItem) {
						String themeName = (String) selectedItem.getText();
						String themeDir = Theme.getByName(themeName);
						UI.getCurrent().setTheme(themeDir);
					}
				};

				MenuItem settingsMenu = menuBar.addItem("Wygląd", null);
				MenuItem styleMenu = settingsMenu.addItem("Styl", null);
				for (Theme t : Theme.values()) {
					styleMenu.addItem(t.getName(), cmdTheme);
				}
				MenuItem fontMenu = settingsMenu.addItem("Czcionka", null);
				String[] fonts = { "12px arial, sans-serif",
						"italic bold 12px Courier, serif",
						"bold 12px Georgia, sans-serif" };
				for (String font : fonts) {
					fontMenu.addItem(font, cmdFont);
				}
				// IN LINE
				MenuItem inlineMenu = settingsMenu
						.addItem("W linii", cmdInline);
				inlineMenu.setCheckable(true);
				// BIGGER
				MenuItem biggerMenu = settingsMenu.addItem("Powiększ",
						cmdBigger);
				biggerMenu.setCheckable(true);
			}
		});
	}

	/**
	 * Creates the clear menu.
	 */
	private void createClearMenu() {
		addComponent(new HorizontalLayout() {
			{
				setSizeUndefined();
				Command cmd = new Command() {

					@Override
					public void menuSelected(final MenuItem selectedItem) {
						table.setToDefaultFilters();
					}
				};
				menuBar.addItem(Strings.WYCZYŚĆ_WYSZUKIWANIE, cmd);
				cmd = new Command() {

					@Override
					public void menuSelected(final MenuItem selectedItem) {
						updateContainer();
					}
				};
				menuBar.addItem("Odśwież dane", cmd);
			}
		});
	}

	/**
	 * Creates the raporty menu.
	 */
	private void createRaportyMenu() {
		// RAPORTY menu
		addComponent(new HorizontalLayout() {
			{
				setSizeUndefined();
				Command cmd = new ReportCommand(table,container);
				MenuItem settingsMenu = menuBar.addItem(
						Strings.RAPORTY_KORESPONDENCJI, null);
				settingsMenu.setStyleName(Strings.ICON_DOC);
				settingsMenu.addItem(Strings.BIEZACE_ZDARZENIA, cmd);
				settingsMenu.addSeparator();
				settingsMenu.addItem(Strings.RAPORT_POCZTOWY_POLECONE, cmd);
				settingsMenu.addSeparator();
				settingsMenu.addItem(Strings.RAPORT_ETYKIETY_ADRESÓW, cmd);
			}
		});
	}

	/**
	 * Creates the user menu.
	 */
	private void createUserMenu() {
		// User menu
		addComponent(new HorizontalLayout() {
			{
				setSizeUndefined();
				addStyleName(Strings.USER);

				Command cmd = new Command() {
					@Override
					public void menuSelected(final MenuItem selectedItem) {
						if (selectedItem.getText().equals(Strings.WYLOGUJ)) {
							UI.getCurrent().getNavigator().navigateTo("logout");
						} else if (selectedItem.getText().equals(
								Strings.USTAWIENIA)) {
							pokazUstawienia();
						}
					}
				};
				MenuItem settingsMenu = menuBar.addItem(SessionHandler.get()
						.getUsername(), null);
				settingsMenu.setStyleName(Strings.ICON_COG);
				settingsMenu.addItem(Strings.USTAWIENIA, cmd);
				settingsMenu.addSeparator();
				settingsMenu.addItem(Strings.WYLOGUJ, cmd);
				addComponent(menuBar);
			}
		});
	}

	/**
	 * Pokaz ustawienia.
	 */
	void pokazUstawienia() {
		Window w = new Window();
		w.setResizable(false);
		UI.getCurrent().addWindow(w);
		VerticalLayout h = new VerticalLayout();
		w.setContent(h);
	}

	void updateContainer() {
		container.refresh();
		table.refreshRowCache();
	}
}