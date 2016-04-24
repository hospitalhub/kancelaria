package pl.kalisz.szpital.kancelaria.ui.editor;

import pl.kalisz.szpital.db.DbHelper;
import pl.kalisz.szpital.db.kancelaria.Adres;
import pl.kalisz.szpital.db.kancelaria.Transaction;
import pl.kalisz.szpital.kancelaria.KancelariaUI;
import pl.kalisz.szpital.kancelaria.ui.views.AddressSearchView;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
public class AdresField extends CustomField<Adres> {
	/**
	 * 
	 */
	private Button b = new Button();
	private Label l;

	public AdresField(String caption) {
		setCaption(caption);
	}

	private boolean edited = false;

	public boolean isEdited() {
		return edited;
	}

	private void refresh() {
		System.out.println("address edited => REFRESH");
		edited = true;
		KancelariaUI ui = (KancelariaUI) UI.getCurrent();
		JPAContainer<Transaction> container = (JPAContainer<Transaction>) ui
				.getDbHelper().getContainer(DbHelper.TRANSACTION_CONTAINER);
//		container.refresh();
	}

	@Override
	protected Component initContent() {
		if (getValue() != null) {
			Adres a = (Adres) getValue();
			l = new Label(a.toString());
		} else {
			l = new Label();
		}
		b.setCaption("Książka adresowa");
		b.addClickListener(e -> {
			AddressSearchView asw = new AddressSearchView();
//			UI.getCurrent().addWindow(asw);
			UI.getCurrent().getNavigator().navigateTo("addressSearch");
//			asw.addCloseListener(e1 -> {
//				if (asw.getAdres() != null) {
//					setValue(asw.getAdres());
//					if (asw.isAdresEdited()) {
//						refresh();
//					}
//				}
//			});
		});
		l.setStyleName("pre");
		HorizontalLayout h = new HorizontalLayout(l, b);
		h.setSpacing(true);
		return h;
	}

	public void setValue(Adres newFieldValue)
			throws com.vaadin.data.Property.ReadOnlyException,
			com.vaadin.data.util.converter.Converter.ConversionException {
		if (newFieldValue != null) {
			l.setValue(newFieldValue.toString());
		}
		super.setValue(newFieldValue);
	}

	@Override
	public Class<Adres> getType() {
		return Adres.class;
	}
}