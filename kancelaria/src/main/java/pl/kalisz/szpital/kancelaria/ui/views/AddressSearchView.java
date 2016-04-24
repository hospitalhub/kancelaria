package pl.kalisz.szpital.kancelaria.ui.views;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.tepi.filtertable.FilterGenerator;
import org.tepi.filtertable.paged.PagedFilterControlConfig;
import org.tepi.filtertable.paged.PagedFilterTable;
import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

import pl.kalisz.szpital.db.DbHelper;
import pl.kalisz.szpital.db.HospitalEntity;
import pl.kalisz.szpital.db.kancelaria.Adres;
import pl.kalisz.szpital.db.kancelaria.AdresBuilder;
import pl.kalisz.szpital.kancelaria.KancelariaUI;
import pl.kalisz.szpital.kancelaria.ui.tables.PagedFilterTable30;
import pl.kalisz.szpital.kancelaria.ui.windows.AdresEditorWindow;
import pl.kalisz.szpital.utils.Strings;

/**
 * The Class AdresSearchWindow.
 */
@SuppressWarnings("serial")
public class AddressSearchView extends VerticalLayout implements View {

	public class AdresTableFilterGenerator implements FilterGenerator {
		@Override
		public AbstractField<?> getCustomFilterComponent(
				final Object propertyId) {
			return null;
		}

		@Override
		public Filter generateFilter(final Object propertyId,
				final Field<?> originatingField) {
			return null;
		}

		@Override
		public Filter generateFilter(final Object propertyId,
				final Object value) {
			return null;
		}

		@Override
		public void filterRemoved(final Object propertyId) {
			//
		}

		@Override
		public Filter filterGeneratorFailed(final Exception reason,
				final Object propertyId, final Object value) {
			return null;
		}

		@Override
		public void filterAdded(final Object propertyId,
				final Class<? extends Filter> filterType, final Object value) {
			//
		}
	}

	public class AdresTableShortcutListener extends ShortcutListener {
		public AdresTableShortcutListener(String caption, int keyCode,
				int[] modifierKeys) {
			super(caption, keyCode, modifierKeys);
		}

		@Override
		public void handleAction(final Object sender, final Object target) {
			if (target instanceof PagedFilterTable
					&& contactList.getValue() != null) {
				Long id = (Long) contactList.getValue();
				Item item = contactList.getItem(id);
				// newTransaction(item);
				adres = new Adres(item);
				Notification.show("CLOSE :D");
			}
		}
	}

	public class AdresTableActionHandler implements Handler {
		private Action delAdres = new Action("Usuń adres (DEL)");
		private Action setAdres = new Action("Wybierz adres (ENTER)");

		@Override
		public Action[] getActions(final Object target, final Object sender) {
			return new Action[] { setAdres, delAdres };
		}

		@Override
		public void handleAction(final Action action, final Object sender,
				final Object target) {
			@SuppressWarnings("unchecked")
			Item item = ((PagedFilterTable<JPAContainer<Adres>>) sender)
					.getItem(target);
			if (action == setAdres) {
				if (item != null) {
					adres = new Adres(item);
					Notification.show("CLOSE :D");
				}
			} else if (action == delAdres) {
				Object id = contactList.getValue();
				if (id != null) {
					deleteAdresWindowOpen(id);
				}
			}
		}
	}

	/** The Constant logger. */
	private static final Logger logger = Logger
			.getLogger(AddressSearchView.class);

	/** The contact list. */
	private PagedFilterTable30<JPAContainer<Adres>> contactList = new PagedFilterTable30<JPAContainer<Adres>>() {

	};

	/** The add new contact button. */
	private Button addNewContactButton = new Button("Nowy kontakt");

	/** The edit contact button. */
	private Button editContactButton = new Button("Edytuj kontakt");

	/** The clear button. */
	private Button clearButton = new Button("Wyczyść formularz");

	/** The merge contact button. */
	// private Button mergeContactButton = new Button("Połącz identyczne");

	/** The content. */
//	private VerticalLayout content = new VerticalLayout();

	/** The adres. */
	private Adres adres;

	/** The adres edited. */
	private boolean adresEdited = false;

	/**
	 * Checks if is adres edited.
	 * 
	 * @return true, if is adres edited
	 */
	public final boolean isAdresEdited() {
		return adresEdited;
	}

	/**
	 * Gets the adres.
	 * 
	 * @return the adres
	 */
	public final Adres getAdres() {
		return adres;
	}

	/** The adres container. */
	private JPAContainer<Adres> adresContainer;

	/**
	 * Inits the container.
	 */
	@SuppressWarnings("unchecked")
	private void initContainer() {
		if (adresContainer == null) {
			KancelariaUI dashboardUI = (KancelariaUI) UI.getCurrent();
			DbHelper dbHelper = dashboardUI.getDbHelper();
			adresContainer = (JPAContainer<Adres>) dbHelper
					.getContainer(DbHelper.ADRES_CONTAINER);
			contactList.resetFilters();
			adresContainer.removeAllContainerFilters();
		}
	}

	/**
	 * Inits the layout.
	 */
	private void initLayout() {
//		setDraggable(false);
//		setResizable(false);
//		setModal(true);
		setSizeFull();
//		setContent(content);
		VerticalLayout mainLayout = new VerticalLayout();

		PagedFilterControlConfig config = new PagedFilterControlConfig();
		config.setPageLengthsAndCaptions(Arrays.asList(new Integer[] { 30 }));
		config.setPage("");
		config.setItemsPerPage(Strings.ADRESOW_NA_STRONIE);
		config.setNext(Strings.NASTEPNA_STRONA);
		config.setFirst(Strings.PIERWSZA);
		config.setLast(Strings.OSTATNIA);
		config.setPrevious(Strings.POPRZEDNIA);
		/*content.*/addComponent(contactList.createControls(config));
		/*content.*/addComponent(mainLayout);
		setExpandRatio(mainLayout, 10);

		HorizontalLayout topLeftLayout = new HorizontalLayout();
		HorizontalLayout topLeft2Layout = new HorizontalLayout();
		mainLayout.addComponent(topLeftLayout);
		mainLayout.addComponent(topLeft2Layout);
		mainLayout.addComponent(contactList);
		contactList.setSizeFull();

		Label fill = new Label();
		topLeftLayout.addComponent(fill);

		topLeft2Layout.addComponent(clearButton);
		topLeft2Layout.addComponent(addNewContactButton);
		topLeft2Layout.addComponent(editContactButton);
		Label fill1 = new Label();
		topLeft2Layout.addComponent(fill1);

		mainLayout.setSizeFull();

		mainLayout.setExpandRatio(contactList, 1);
		contactList.setHeight(100, Unit.PERCENTAGE);
		contactList.setSizeFull();

		topLeftLayout.setWidth(Strings.PERCENT100);
		topLeft2Layout.setWidth(Strings.PERCENT100);

		setWidth(Strings.PERCENT100);
		setHeight(Strings.PERCENT100);

		fill.setWidth(Strings.PERCENT100);
		fill.setSizeFull();
		topLeftLayout.setExpandRatio(fill, 5);
		fill1.setWidth(Strings.PERCENT100);
		fill1.setSizeFull();
		topLeft2Layout.setExpandRatio(fill1, 5);

	}

	/**
	 * Inits the add remove buttons.
	 */
	private void initAddRemoveButtons() {

		editContactButton.addClickListener(event-> {
				Object contactId = contactList.getValue();
				adresEdited = true;
				if (contactId != null) {
					adresEditorWindowOpen(contactId);
				}
		});
		clearButton.addClickListener(event-> {
				contactList.resetFilters();
				adresContainer.removeAllContainerFilters();
		});
		addNewContactButton.addClickListener(event-> {

				Adres adres = new AdresBuilder().pusty().build();
				Object contactId = adresContainer.addEntity(adres);
				for (Object id : contactList.getContainerPropertyIds()) {
					if (HospitalEntity.ID.equals(id.toString())) {
						continue;
					}
				}
				/* Lets choose the newly created contact to edit it. */
				contactList.select(contactId);

				if (contactId != null) {
					adresEditorWindowOpen(contactId);
				}
		});
	}

	/**
	 * Adres editor window open.
	 * 
	 * @param contactId
	 *            the contact id
	 */
	protected final void adresEditorWindowOpen(final Object contactId) {
		final KancelariaUI dashboardUI = (KancelariaUI) UI.getCurrent();
		final AdresEditorWindow adresEditorWindow = new AdresEditorWindow(
				contactList.getItem(contactId));
		dashboardUI.addWindow(adresEditorWindow);
		adresEditorWindow.addCloseListener(new CloseListener() {

			@Override
			public void windowClose(final CloseEvent e) {
				if (adresEditorWindow.isCommit()) {
					logger.debug("commit adres");
					adresContainer.commit();
					adres = new Adres(adresEditorWindow.getAdresItem());
				} else {
					logger.debug("rollback adres");
					adresContainer.refresh();
				}
				logger.debug("close search window");
//				close();
				Notification.show("CLOSE ;)");
			}
		});
	}

	/**
	 * Delete adres window open.
	 * 
	 * @param contactId
	 *            the contact id
	 */
	protected final void deleteAdresWindowOpen(final Object contactId) {
		Item item = contactList.getItem(contactId);
		String adres = new Adres(item).toString();
		ConfirmDialog.show(UI.getCurrent(), Strings.USUNAC_TEN_ADRES, adres,
				Strings.USUN, Strings.ANULUJ, new ConfirmDialog.Listener() {

					@Override
					public void onClose(final ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							try {
								contactList.removeItem(contactId);
								adresContainer.commit();
								// updateFilters(false);
							} catch (Exception e) {
								Notification.show(Strings.NIE_MOZNA_USUNAC,
										Strings.ZAREJESTROWANO,
										Notification.Type.ERROR_MESSAGE);
							}
						}
					}
				});
	}

	/**
	 * Inits the contact list.
	 */
	private void initContactList() {

		contactList.setContainerDataSource(adresContainer);
		contactList.setFilterGenerator(new AdresTableFilterGenerator());

		contactList.addShortcutListener(new AdresTableShortcutListener(
				Strings.EMPTY_STRING, KeyCode.ENTER, new int[] {}));

		contactList.addActionHandler(new AdresTableActionHandler());
		setColumns();
	}

	private void setColumns() {
		Object[] visible = new String[] { Adres.COMPANY, Adres.NAZWISKO,
				Adres.IMIE, Adres.ULICA, //
				Adres.MIASTO, Adres.KOD };
		contactList.setColumnExpandRatio(Adres.COMPANY, 250);
		contactList.setColumnExpandRatio(Adres.NAZWISKO, 100);
		contactList.setColumnExpandRatio(Adres.IMIE, 50);
		contactList.setColumnExpandRatio(Adres.ULICA, 150);
		contactList.setColumnExpandRatio(Adres.MIASTO, 100);
		contactList.setColumnExpandRatio(Adres.KOD, 50);
		contactList.setVisibleColumns(visible);
		contactList.setSelectable(true);
		contactList.setImmediate(true);
		contactList.setFilterBarVisible(true);
		contactList.sort(new Object[] { Adres.ILOSC_PISM },
				new boolean[] { false });
	}

	@Override
	public void enter(ViewChangeEvent event) {
		initContainer();
		initLayout();
		initContactList();
		initAddRemoveButtons();
	}

}
