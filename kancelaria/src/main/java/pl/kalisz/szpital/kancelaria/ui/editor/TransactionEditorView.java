package pl.kalisz.szpital.kancelaria.ui.editor;

import org.apache.log4j.Logger;

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.CssLayout;

import pl.kalisz.szpital.kancelaria.KancelariaUI;

/**
 * The Class TransactionEditorWindow.
 */
@SuppressWarnings("serial")
public class TransactionEditorView extends CssLayout implements View {

	/** The Constant logger. */
	private static final Logger LOGGER = Logger
			.getLogger(TransactionEditorView.class);

	/** The bottom layout. */
	// private final VerticalLayout bottomLayout = new VerticalLayout();

	// /** The bottom buttons layout. */
	// private final HorizontalLayout bottomButtonsLayout = new
	// HorizontalLayout();

	// /** The details layout. */
	// private final HorizontalLayout detailsLayout = new HorizontalLayout();

	/** The tab layout. */
	// private final HorizontalLayout tabLayout = new HorizontalLayout();

	/** The content layout. */
//	private final VerticalLayout contentLayout = new VerticalLayout();

	// private final HorizontalLayout topLayout = new HorizontalLayout();

	/** The notatki layout. */
	// private final HorizontalLayout notatkiLayout = new HorizontalLayout();

	/** The tabsheet. */
	// private final TabSheet tabsheet = new TabSheet();


	/** The transaction. */
	// private Transaction transaction;

	/** The adresses edited. */
	// private boolean adressesEdited = false;

	/** The container. */
	// private JPAContainer<Transaction> container;

	/** The table. */
	// private Table table;

	public FieldGroup binder = new FieldGroup();

	@Override
	public void enter(ViewChangeEvent event) {
		setCaption("Zdarzenie");
		setSizeFull();
//		setResizable(false);
//		setDraggable(false);
//		setModal(true);
//		center();

		LOGGER.info("binder setup");
		
		Item i = (Item) VaadinSession.getCurrent().getAttribute(KancelariaUI.TRANSACTION_EDITOR);
		
		binder.setItemDataSource(i);
		binder.setBuffered(true);
		TransactionForm form = new TransactionForm(binder);
		LOGGER.info("form created");
		
		addComponent(form);
		form.setSizeFull();

		LOGGER.info("window added");

		// initTransactionContainer();
		// buildWindow();
		// addTyp(t.getTypWiadomosci());
		// addKomorkaLayout(t.getOdbiorca());
		// addTypPisma(t.getTypPisma());
		// addAdres(t.getAdres());
		// addTresc(t.getOpis());
		// addScan(isTemp);
		// addSaveButton();
		// addTabs();
		// setSizesAndStyles();
		// topLayout.setSpacing(true);
		
	}

	// /**
	// * Inits the transaction container.
	// */
	// @Deprecated
	// @SuppressWarnings("unchecked")
	// private void initTransactionContainer() {
	// try {
	// KancelariaUI dashboardUI = (KancelariaUI) UI.getCurrent();
	// DbHelper dbHelper = dashboardUI.getDbHelper();
	// int type = DbHelper.ADDRESS_BOUND_TRANSACTION_CONTAINER;
	// container = (JPAContainer<Transaction>) dbHelper.getContainer(type);
	// table = new Table();
	// table.setContainerDataSource(container);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	// /**
	// * Adds the tabs.
	// */
	// private void addTabs() {
	// tabLayout.addComponent(tabsheet);
	// notatkiLayout.addComponent(notatkiTextArea);
	// tabsheet.addTab(detailsLayout, Strings.KORESPONDENCJA);
	// table.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
	// table.setFooterVisible(false);
	// table.setVisibleColumns(new Object[] { Transaction.TYP_WIADOMOSCI,
	// Transaction.ID, Transaction.TYP_PISMA_STR,
	// Transaction.ODBIORCA_STR, Transaction.DATA_STR });
	// table.setHeight(90, Unit.PIXELS);
	// table.setWidth(100, Unit.PERCENTAGE);
	// detailsLayout.addComponent(table);
	// tabsheet.addTab(notatkiLayout, Strings.NOTATKI);
	// }

	/**
	 * Adds the adres.
	 * 
	 * @param a
	 *            the a
	 */
	// private void addAdres(final Adres a) {
	// VerticalLayout adresLayout = new VerticalLayout();
	// adresLayout.setSizeFull();
	// adresTA.setCaption(Strings.ADRES);
	// setAdres(a);
	// setLinkedTransactions(a);
	// adresTA.setImmediate(true);
	// adresTA.setReadOnly(true);
	// adresTA.setSizeFull();
	// adresTA.setHeight(100, Unit.PERCENTAGE);
	// adresLayout.addComponent(adresTA);
	// String adresButtonCaption = a == null ? Strings.WYBIERZ_ADRES
	// : Strings.ZMIEN_ADRES;
	// final Button adresyButton = new Button(adresButtonCaption);
	// adresyButton.addClickListener(new ClickListener() {
	// @Override
	// public void buttonClick(final ClickEvent event) {
	// showAdresSearchWindow();
	// }
	// });
	// adresyButton.setStyleName(Strings.SMALL);
	// adresLayout.addComponent(adresyButton);
	// adresLayout.setExpandRatio(adresTA, 3);
	// adresLayout.setExpandRatio(adresyButton, 1);
	// adresLayout
	// .setComponentAlignment(adresyButton, Alignment.MIDDLE_CENTER);
	// topLayout.addComponent(adresLayout);
	// topLayout.setExpandRatio(adresLayout, 1.2f);
	// }

	/**
	 * Sets the linked transactions.
	 * 
	 * @param adres
	 *            the new linked transactions
	 */
	// private void setLinkedTransactions(final Adres adres) {
	// container.removeAllContainerFilters();
	// container.addContainerFilter(new Equal(Transaction.ADRES, adres));
	// container.applyFilters();
	// container
	// .sort(new Object[] { Transaction.ID }, new boolean[] { false });
	// }

	/**
	 * Sets the adres.
	 * 
	 * @param adres
	 *            the new adres
	 */
	// private void setAdres(final Adres adres) {
	// if (adres != null) {
	// adresTA.setReadOnly(false);
	// adresTA.setValue(adres.toString());
	// adresTA.setReadOnly(true);
	// if (transaction != null) {
	// transaction.setAdres(adres);
	// }
	// }
	// }

	/**
	 * Adds the komorka layout.
	 * 
	 * @param value
	 *            the value
	 */
	// private void addKomorkaLayout(final String value) {
	// final VerticalLayout komorkaLayout = new VerticalLayout();
	// IndexedContainer container = new IndexedContainer();
	// for (KomorkaOrganizacyjna ko : KomorkaOrganizacyjna.values()) {
	// container.addItem(ko);
	// }
	// if (value != null && !value.equals("")) {
	// for (String s : value.split(" ")) {
	// komorkaTokenField.addToken(KomorkaOrganizacyjna.getByKod(s));
	// }
	// }
	// komorkaTokenField.setContainerDataSource(container);
	// komorkaTokenField.setFilteringMode(FilteringMode.CONTAINS);
	//
	// komorkaTokenField.addValidator(new Validator() {
	//
	// @Override
	// public void validate(Object value) throws InvalidValueException {
	// if (value != null) {
	// LinkedHashSet set = (LinkedHashSet) value;
	// for (Object o : set) {
	// if (o instanceof String) {
	// for (String s : ((String) o).split(" ")) {
	// try {
	// KomorkaOrganizacyjna ko = KomorkaOrganizacyjna
	// .getByKod(s);
	// komorkaTokenField.addToken(ko);
	// } catch (Exception e2) {
	// }
	// }
	// komorkaTokenField.removeToken(o);
	// }
	// }
	// }
	// }
	// });
	// komorkaLayout.addComponent(komorkaTokenField);
	// komorkaTokenField.setWidth(185, Unit.PIXELS);
	// // komorkaLayout.setSizeFull();
	// // komorkaLayout.setSpacing(true);
	// topLayout.addComponent(komorkaLayout);
	// topLayout.setExpandRatio(komorkaLayout, 2);
	// }

	/**
	 * Adds the save button.
	 */
	// private void addSaveButton() {
	// final Button zapiszButton = new Button(Strings.ZAPISZ);
	// TransactionValidator transactionValidator = new TransactionValidator();
	// zapiszButton.addClickListener(e -> {
	// try {
	// transactionValidator.validate(transaction);
	// } catch (Exception ex) {
	// Notification.show(Strings.ERR, ex.getMessage(),
	// Notification.Type.WARNING_MESSAGE);
	// return;
	// }
	// // context.setFileLocationStrategy(new FilepathLocationStrategy());
	// // if (pdfUploadFile == null && !context.exists(transaction)) {
	// // System.out.println("BRAK??? " + transaction.getPlikSciezka());
	// // confirmation();
	// // } else {
	// // LOGGER.debug("zapisz start");
	// // zapisz(transaction);
	// // }
	// });
	// Label fill = new Label();
	// bottomButtonsLayout.addComponent(fill);
	// bottomButtonsLayout.setExpandRatio(fill, 10);
	// bottomButtonsLayout.addComponent(zapiszButton);
	// bottomButtonsLayout.setSizeFull();
	// bottomLayout.addComponent(bottomButtonsLayout);
	// }

	// private void confirmation() {
	// ConfirmDialog.show(UI.getCurrent(), Strings.BRAK_SKANU,
	// Strings.CZY_ZAPISAC_BEZ_SKANU, "Tak", "Nie",
	// new ConfirmDialog.Listener() {
	// @Override
	// public void onClose(final ConfirmDialog dialog) {
	// if (dialog.isConfirmed()) {
	// zapisz(transaction);
	// }
	// }
	// });
	// }


	/**
	 * Adds the tresc.
	 * 
	 * @param value
	 *            the value
	 */
	// private void addTresc(final String value) {
	// notatkiTextArea.setValue(value);
	// }

	// private void addTyp(final TypWiadomosci typWiadomosci) {
	// typOptionGroup.setCaption(Strings.TYP_WIADOMOSCI);
	// typOptionGroup.setNewItemsAllowed(false);
	// typOptionGroup.setNullSelectionAllowed(false);
	// typOptionGroup.setNewItemHandler(new NewItemHandler() {
	//
	// @Override
	// public void addNewItem(final String newItemCaption) {
	// typOptionGroup.addItem(newItemCaption);
	// }
	// });
	//
	// for (TypWiadomosci t : TypWiadomosci.values()) {
	// // Archiwalny ma przychodzacy na null - nie jest wyswietlany
	// if (t.isPrzychodzacy() != null) {
	// typOptionGroup.addItem(t.getNazwa());
	// }
	// }
	// typOptionGroup.setImmediate(true);
	// if (typWiadomosci != null) {
	// typOptionGroup.setValue(typWiadomosci.getNazwa());
	// }
	// topLayout.addComponent(typOptionGroup);
	// }

	/**
	 * Adds the typ pisma.
	 * 
	 * @param value
	 *            the value
	 */
	// private void addTypPisma(final TypPisma value) {
	// typPismaSelect.setValue(value);
	// topLayout.addComponent(typPismaSelect);
	// }

	/**
	 * Builds the window.
	 */
	// private void buildWindow() {
	// setCaption("Zdarzenie");
	// setResizable(false);
	// setDraggable(false);
	// setModal(true);
	// center();
	// // contentLayout.addComponent(topLayout);
	// contentLayout.addComponent(bottomLayout);
	// bottomLayout.addComponent(tabLayout);
	//
	// setContent(contentLayout);
	// UI.getCurrent().addWindow(this);
	// this.focus();
	// }

	/**
	 * Gets the komorka.
	 * 
	 * @return the komorka
	 */
	// private String getKomorka() {
	// @SuppressWarnings("unchecked")
	// LinkedHashSet<KomorkaOrganizacyjna> set =
	// (LinkedHashSet<KomorkaOrganizacyjna>) komorkaTokenField
	// .getValue();
	// if (set == null) {
	// return "";
	// }
	// StringBuilder sb = new StringBuilder();
	// for (KomorkaOrganizacyjna ko : set) {
	// sb.append(ko.getKod());
	// sb.append(" ");
	// }
	// LOGGER.debug(sb);
	// return sb.toString();
	// }

	// /**
	// * Gets the opis.
	// *
	// * @return the opis
	// */
	// private String getOpis() {
	// return notatkiTextArea.getValue().toString();
	// }

	// /**
	// * Gets the typ.
	// *
	// * @return the typ
	// */
	// private String getTyp() {
	// return typOptionGroup.getValue().toString();
	// }

//	 /**
	// * Gets the typ pisma.
	// *
	// * @return the typ pisma
	// */
	// private TypPisma getTypPisma() {
	// return new TypPisma(typPismaSelect.getValue().toString());
	// }

//	/**
//	 * Sets the last saved transaction.
//	 * 
//	 * @param transaction
//	 *            the new last saved transaction
//	 */
//	private void setLastSavedTransaction(final Transaction transaction) {
//		((KancelariaUI) UI.getCurrent()).setLastTransaction(transaction);
//	}

	// /**
	// * Sets the sizes and styles.
	// */
	// private void setSizesAndStyles() {
	// topLayout.setSizeFull();
	// topLayout.setHeight(180, Unit.PIXELS);
	// typPismaSelect.setHeight(120, Unit.PIXELS);
	// typPismaSelect.setSizeFull();
	// topLayout.setExpandRatio(typOptionGroup, 2.1f);
	// topLayout.setExpandRatio(typPismaSelect, 1.7f);
	// topLayout.setSpacing(true);
	// tabLayout.setWidth(650, Unit.PIXELS);
	// notatkiLayout.setSizeFull();
	// notatkiTextArea.setSizeFull();
	// komorkaTokenField.focus();
	// }

	/**
	 * Show adres search window.
	 */
	// private void showAdresSearchWindow() {
	// final KancelariaUI ui = (KancelariaUI) UI.getCurrent();
	//
	// final AdresSearchWindow adresSearchWindow = new AdresSearchWindow();
	// adresSearchWindow.addCloseListener(new CloseListener() {
	// @Override
	// public void windowClose(final CloseEvent e) {
	// Adres adres = adresSearchWindow.getAdres();
	// adressesEdited = adresSearchWindow.isAdresEdited();
	// // if (adres != null) {
	// // setAdres(adres);
	// // setLinkedTransactions(adres);
	// // }
	// }
	// });
	// ui.addWindow(adresSearchWindow);
	// }

	// TODO(AM) testy do zrobienia - 1 utworzenie zdarzenia 2 skopiowanie
	// zdarzenia z wszystkim 3
	// edycja
	// TODO(AM) 4 przeniesienie do kosza 5 przeniesienie do innego typu
	// wiadomosci (zewn -> wewn)
	// TODO(AM) rzeczy do sprawdzenia: utworzenie, sygnatura, zmieniona tresc

	/**
	 * Zapisz.
	 * 
	 * @param transaction
	 *            the transaction
	 */
//	private void zapisz(final Transaction transaction) {
		// TypWiadomosci typWiadomosci = TypWiadomosci.getByTytul(getTyp());
//		boolean isNewTransaction = transaction.getId() == null;
//		if (isNewTransaction) {
//			Date data = new Date(System.currentTimeMillis());
//			transaction.setData(data);
			// transaction.setTypWiadomosci(typWiadomosci);
//		}
		// transaction.setTypPisma(getTypPisma());
		// transaction.setOdbiorca(getKomorka());
		// transaction.setOpis(getOpis());
		// transaction.setTypWiadomosci(typWiadomosci);
//		transaction.setSygnatura(null);
//		KancelariaUI ui = ((KancelariaUI) UI.getCurrent());
//		@SuppressWarnings("unchecked")
//		JPAContainer<Transaction> container = (JPAContainer<Transaction>) ui
//				.getDbHelper().getContainer(DbHelper.TRANSACTION_CONTAINER);
//		try {
//			Long id = (Long) container.addEntity(transaction);
//			transaction.setId(id);
//			zapiszPlik(transaction);
//			container.commit();
//			close();
//			LOGGER.debug("sygnatura");
//			setLastSavedTransaction(transaction);
//			LOGGER.debug("zapisywanie transakcji");
//		} catch (Exception e) {
//			Notification.show(Strings.COS_POSZLO_NIE_TAK,
//					Strings.PROSZE_ZGLOSIC_BLAD,
//					Notification.Type.ERROR_MESSAGE);
//		}
//	}

	/**
	 * Gets the transaction.
	 * 
	 * @return the transaction
	 */
	// public final Transaction getTransaction() {
	// return transaction;
	// }


}
