package pl.kalisz.szpital.kancelaria.ui.windows;

import java.util.Collection;

import pl.kalisz.szpital.db.DbHelper;
import pl.kalisz.szpital.db.HospitalEntity;
import pl.kalisz.szpital.db.kancelaria.Adres;
import pl.kalisz.szpital.db.kancelaria.AdresBuilder;
import pl.kalisz.szpital.db.kancelaria.Phone;
import pl.kalisz.szpital.db.kancelaria.enums.Kraj;
import pl.kalisz.szpital.kancelaria.utils.AdresApiFacade;
import pl.kalisz.szpital.utils.Strings;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Item;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.DefaultFieldGroupFieldFactory;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * The Class AdresEditorWindow.
 */
@SuppressWarnings("serial")
public class AdresEditorWindow extends Window {

	public class PhoneGrid extends CustomField<Collection> {
		@SuppressWarnings("unchecked")
		@Override
		public Class<? extends Collection> getType() {
			return (Class<? extends Collection>) Collection.class;
		}

		@Override
		protected Component initContent() {
			HorizontalLayout hl = new HorizontalLayout();
			JPAContainer<Phone> phoneContainer = JPAContainerFactory.make(Phone.class, DbHelper.JPA);
			Grid grid = new Grid(phoneContainer);
			grid.removeColumn("id");
			grid.removeColumn("adres");
			grid.setEditorEnabled(true);
			grid.setHeight(150, Unit.PIXELS);
			Button add = new Button();
			add.addClickListener(event -> {
				Phone p = new Phone();
				p.setNumber("123123123");
				p.setAdres(new AdresBuilder(1L).build());
				p.setId(1L);
				p.setType(Phone.PhoneType.HOME);
				phoneContainer.addEntity(p);
				phoneContainer.commit();
			} );
			hl.addComponent(grid);
			hl.addComponent(add);
			return hl;
		}
	}

	public class AdresEditorFieldFactory implements FieldGroupFieldFactory {

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public <T extends Field> T createField(Class<?> dataType, Class<T> fieldType) {
			DefaultFieldGroupFieldFactory factory = new DefaultFieldGroupFieldFactory() {
			};
			Field f = factory.createField(dataType, fieldType);
			if (f instanceof TextField) {
				((TextField) f).setNullRepresentation("");
				((TextField) f).setNullSettingAllowed(false);
			}
			f.setWidth(Strings.PERCENT100);
			f.setId(f.getCaption());
			if (dataType.equals(Kraj.class)) {
				ComboBox c = new ComboBox();
				ListSelect ls = (ListSelect) f;
				c.setContainerDataSource(ls);
				c.setNullSelectionItemId(ls.getContainerDataSource().getItem(Kraj.PL));
				ls.setNullSelectionItemId(ls.getContainerDataSource().getItem(Kraj.PL));
				if (f.getValue() != null && f.getValue().toString().trim().equals("")) {
					f.setValue(null);
				}
				c.setImmediate(true);
				c.setBuffered(true);
				c.setWidth(Strings.PERCENT100);
				return (T) c;
			} else if (dataType.equals(Collection.class)) {
				CustomField<Collection> phoneGrid = new PhoneGrid();
				return (T) phoneGrid;
			}
			return (T) f;
		}
	}

	/** The form. */
	private FormLayout form = new FormLayout();

	/** The editor fields. */
	private FieldGroup binder = new FieldGroup();

	/** The save contact button. */
	private Button saveContactButton = new Button("OK");

	/** The cancel button. */
	private Button cancelButton = new Button("Anuluj");

	/** The adres item. */
	private Item adresItem;

	/** The commit. */
	private boolean commit = false;

	HorizontalLayout mainLayout = new HorizontalLayout();

	VerticalLayout mapLayout = new VerticalLayout();

	String currentLocationString = "new";

	/**
	 * Gets the adres item.
	 * 
	 * @return the adres item
	 */
	public Item getAdresItem() {
		return adresItem;
	}

	/**
	 * Checks if is commit.
	 * 
	 * @return true, if is commit
	 */
	public boolean isCommit() {
		return commit;
	}

	private void updatePics(Adres a) {
		String locationString = a.toLocation();
		System.out.println(locationString);
		if (locationString.equals(currentLocationString)) {
			return;
		} else {
			currentLocationString = locationString;
			Image[] imgs = AdresApiFacade.getGeoPics(locationString);
			mapLayout.removeAllComponents();
			if (imgs != null) {
				for (Image img : imgs) {
					img.setWidth(300, Unit.PIXELS);
					mapLayout.addComponent(img);
				}
			}
			mainLayout.addComponent(mapLayout);
			mapLayout.setWidth(310, Unit.PIXELS);
		}
	}

	/**
	 * Instantiates a new adres editor window.
	 * 
	 * @param item
	 *            the item
	 */
	public AdresEditorWindow(Item item) {
		this.adresItem = item;
		setContent(mainLayout);
		mainLayout.setSizeFull();
		initEditor();
		updatePics(new Adres(item));
	}

	// TODO(AM) zrobic min - maks wielkosc pola (zgodnie z baza danych)
	// @SuppressWarnings("unchecked")
	// private void initValidation() {
	// logger.debug("init validation");
	// TODO(AM) usuwać gdy kraj inny niż PL
	// Field<String> f = (Field<String>) binder.getField(Address.KOD);
	// f.addValidator(new BeanValidator(Address.class,Address.KOD));
	// Field<String> ulicaField = (Field<String>) binder.getField(Adres.ULICA);
	// ulicaField.addValidator(new BeanValidator(Adres.class,Adres.ULICA));
	// Field<String> imieField = (Field<String>) binder.getField(Adres.IMIE);
	// imieField.addValidator(new BeanValidator(Adres.class,Adres.IMIE));
	// }

	// @SuppressWarnings("unchecked")
	// private void validateFields() throws InvalidValueException {
	// for (Field<String> f : binder.getFields().toArray(new Field[] {})) {
	// f.validate();
	// }
	// }

	/**
	 * Inits the editor.
	 */
	private void initEditor() {
		mainLayout.addComponent(form);
		setWidth(Strings.PERCENT80);
		setModal(true);
		setClosable(false);
		setResizable(false);
		setSizeFull();
		binder.setItemDataSource(adresItem);

		FieldGroupFieldFactory fgFieldFactory = new AdresEditorFieldFactory();
		binder.setFieldFactory(fgFieldFactory);

		for (String fieldName : Adres.COLUMN_NAMES) {
			// TextField field = new TextField(fieldName);
			if (fieldName.equals(HospitalEntity.ID)) {
				continue;
			}
			Field f = binder.buildAndBind(fieldName);
			if (fieldName.equals(Adres.ULICA) || fieldName.equals(Adres.MIASTO)) {
				f.addValueChangeListener(event -> {
					System.out.println("updating");
					String ulica = binder.getField(Adres.ULICA).getValue().toString();
					String miasto = binder.getField(Adres.MIASTO).getValue().toString();
					updatePics(new AdresBuilder().adres(ulica, "", miasto).build());
				} );
			}
			f.addValidator(new BeanValidator(Adres.class, fieldName));
			form.addComponent(f);
		}

		form.addComponent(saveContactButton);
		form.addComponent(cancelButton);
		form.setMargin(true);
		mainLayout.setExpandRatio(form, 3);

		saveContactButton.addClickListener(event -> {
			try {
				// validateFields();
				commit = true;
				binder.commit();
				close();
			} catch (InvalidValueException | CommitException e) {
				Notification.show(e.getMessage());
			}
		} );

		cancelButton.addClickListener(event -> {
			close();
		} );
	}

	/**
	 * Gets the field.
	 * 
	 * @param fieldName
	 *            the field name
	 * @return the field
	 */
	public String getField(String fieldName) {
		if (binder.getField(fieldName) != null
				&& !binder.getField(fieldName).getValue().toString().equals(Strings.EMPTY_STRING)) {
			return binder.getField(fieldName).getValue().toString();
		} else {
			return Strings.EMPTY_STRING;
		}
	}

}
