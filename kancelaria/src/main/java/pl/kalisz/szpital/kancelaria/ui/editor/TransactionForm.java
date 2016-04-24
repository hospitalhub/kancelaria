package pl.kalisz.szpital.kancelaria.ui.editor;

import org.apache.log4j.Logger;
import org.vaadin.tokenfield.TokenField;

import pl.kalisz.szpital.db.DbHelper;
import pl.kalisz.szpital.db.kancelaria.Adres;
import pl.kalisz.szpital.db.kancelaria.Transaction;
import pl.kalisz.szpital.db.kancelaria.TypPisma;
import pl.kalisz.szpital.db.kancelaria.enums.TypWiadomosci;
import pl.kalisz.szpital.kancelaria.KancelariaUI;
import pl.kalisz.szpital.utils.Strings;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.JPAContainerItem;
import com.vaadin.addon.jpacontainer.fieldfactory.SingleSelectConverter;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
public class TransactionForm extends HorizontalSplitPanel {

	/** The Constant logger. */
	static final Logger LOGGER = Logger
			.getLogger(TransactionForm.class);

	/** The notatki text area. */
	@PropertyId(Transaction.OPIS_STR)
	private final TextArea notatkiTextArea = new TextArea(Transaction.OPIS_STR);

	/** The typ option group. */
	@PropertyId(Transaction.TYP_WIADOMOSCI)
	private final CustomField<TypWiadomosci> typWiadomosciCombo = new TypWiadomosciPopup(
			Transaction.TYP_WIADOMOSCI);

	/** The typ pisma select. */
	@PropertyId(Transaction.TYP_PISMA_STR)
	private final ComboBox typPismaCombo = new ComboBox(
			Transaction.TYP_PISMA_STR);

	@PropertyId(Transaction.ODBIORCA_STR)
	private TokenField komorkaTokenField = new KOrgTokenField(
			Transaction.ODBIORCA_STR);

	@PropertyId(Transaction.PLIK_SCIEZKA_STR)
	private FilepathCustomField file = new FilepathCustomField();

	/** The adres ta. */
	@PropertyId(Transaction.ADRES)
	private final AdresField adresTA = new AdresField(Transaction.ADRES);

	private final Button saveButton = new Button(Strings.ZAPISZ);

	final FieldGroup binder;

	public TransactionForm(FieldGroup binder) {
		this.binder = binder;

//		setSpacing(true);

		createTypPismaListSelect();
		createSaveButton();

		// AM: at the end bind fields (using converters etc.)
		binder.bindMemberFields(this);

		typWiadomosciCombo.addValidator(new BeanValidator(Transaction.class,
				Transaction.TYP_WIADOMOSCI));
		adresTA.addValidator(new BeanValidator(Transaction.class,
				Transaction.ADRES));
		komorkaTokenField.addValidator(new BeanValidator(Transaction.class,
				Transaction.ODBIORCA_STR));
		file.addValidator(new StringLengthValidator("Brak pliku", 0, 1024,
				false));

		FormLayout formLayout = new FormLayout();
		formLayout.addComponent(typWiadomosciCombo);
		formLayout.addComponent(typPismaCombo);
		formLayout.addComponent(komorkaTokenField);
		formLayout.addComponent(adresTA);
		formLayout.addComponent(notatkiTextArea);
		
		Label filler = new Label();
		formLayout.addComponent(filler);

		formLayout.addComponent(saveButton);
		addComponent(formLayout);
		addComponent(file);
		file.setSizeFull();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void createTypPismaListSelect() {
		typPismaCombo.sanitizeSelection();
		Container c = JPAContainerFactory.makeReadOnly(TypPisma.class,
				DbHelper.JPA);
		typPismaCombo.setContainerDataSource(c);
		typPismaCombo.setNewItemsAllowed(false);
		typPismaCombo.setNullSelectionAllowed(false);
		typPismaCombo.setItemCaptionPropertyId(TypPisma.NAZWA);
		typPismaCombo.setImmediate(false);
		typPismaCombo.setConverter(new SingleSelectConverter(typPismaCombo));
	}

	private void createSaveButton() {
		saveButton.addClickListener(e -> {
			try {

				commitBinder();

				// save if new
				Long id = save();

				// this updates transaction container if adres edited in adres
				// container
				updateAdresIfEdited(id);

				saveTransactionToSession();

//				parent.close();
				Notification.show("CLSE");
				UI.getCurrent().getNavigator().navigateTo(KancelariaUI.ZDARZENIA);

				Notification.show("Numer dokumentu: " + id.toString(),
						Notification.Type.ERROR_MESSAGE);

			} catch (Exception ex) {
				Notification.show(Strings.ERR, ex.getMessage(),
						Notification.Type.WARNING_MESSAGE);
			}
		});
	}

	private void saveTransactionToSession() {
		Item item = binder.getItemDataSource();
		if (item instanceof JPAContainerItem) {
			VaadinSession.getCurrent().setAttribute("lastTransaction",
					((JPAContainerItem) item).getEntity());
		} else if (item instanceof BeanItem<?>) {
			VaadinSession.getCurrent().setAttribute("lastTransaction",
					((BeanItem) item).getBean());
		}
	}

	@SuppressWarnings("unchecked")
	private void updateAdresIfEdited(Long id) {
		if (adresTA.isEdited()) {
			KancelariaUI ui = (KancelariaUI) UI.getCurrent();
			JPAContainer<Transaction> container = (JPAContainer<Transaction>) ui
					.getDbHelper().getContainer(DbHelper.TRANSACTION_CONTAINER);

			// UPDATE PO EDICIE ADRESU
			Adres a = (Adres) container.getItem(id)
					.getItemProperty(Transaction.ADRES).getValue();
			try {
				container.getItem(id).getItemProperty(Transaction.ADRES)
						.setValue(null);
			} catch (Exception e) {
				LOGGER.trace("Address update " + e.getMessage());
			}
			container.getItem(id).getItemProperty(Transaction.ADRES)
					.setValue(a);
			container.commit();
			container.refreshItem(id);
		}
	}

	private void commitBinder() throws Exception {
		try {
			binder.commit();
		} catch (Exception e) {
			throw new Exception("Uzupełnij brakujące dane");
		}
	}

	@SuppressWarnings("unchecked")
	private Long save() throws Exception {
		try {
			Item i = binder.getItemDataSource();
			Long id = (Long) i.getItemProperty(Transaction.ID).getValue();
			// id == null => new transaction -> add entity to db
			if (id == null) {
				KancelariaUI ui = (KancelariaUI) UI.getCurrent();
				JPAContainer<Transaction> container = (JPAContainer<Transaction>) ui
						.getDbHelper().getContainer(
								DbHelper.TRANSACTION_CONTAINER);
				BeanItem<Transaction> ti = (BeanItem<Transaction>) binder
						.getItemDataSource();
				Object newId = container.addEntity(ti.getBean());
				LOGGER.trace("Address: " + ti.getBean().getAdres());
				return (Long) newId;
			} else {
				return id;
			}
		} catch (Exception e2) {
			throw new Exception("Nie można zapisać");
		}
	}

}
