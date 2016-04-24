package pl.kalisz.szpital.kancelaria.ui.editor;

import java.util.List;

import org.vaadin.hene.popupbutton.PopupButton;

import pl.kalisz.szpital.db.DbHelper;
import pl.kalisz.szpital.db.kancelaria.enums.TypWiadomosci;
import pl.kalisz.szpital.kancelaria.KancelariaUI;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.Converter.ConversionException;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class TypWiadomosciPopup extends CustomField<TypWiadomosci> {
	PopupButton popup = new PopupButton();

	public TypWiadomosciPopup(String caption) {
		setCaption(caption);
		setImmediate(true);
	}

	@Override
	protected void setInternalValue(TypWiadomosci newValue) {
		if (newValue != null) {
			popup.setCaption(newValue.getNazwa());
		}
		super.setInternalValue(newValue);
	}

	@Override
	public void setValue(TypWiadomosci newFieldValue)
			throws com.vaadin.data.Property.ReadOnlyException,
			ConversionException {
		if (newFieldValue != null) {
			popup.setCaption(newFieldValue.getNazwa());
		}
		super.setValue(newFieldValue);
	}

	@Override
	protected Component initContent() {
		popup.setImmediate(true);
		KancelariaUI ui = ((KancelariaUI) UI.getCurrent());
		@SuppressWarnings("unchecked")
		BeanItemContainer<TypWiadomosci> container = (BeanItemContainer<TypWiadomosci>) ui
				.getDbHelper().getContainer(DbHelper.TYP_WIADOMOSCI_CONTAINER);
		VerticalLayout layout = new VerticalLayout();
		List<TypWiadomosci> typy = container.getItemIds();
		for (TypWiadomosci t : typy) {
			Button b = new Button(t.getNazwa());
			b.addClickListener(event -> {
				setValue(t);
				popup.setPopupVisible(false);
			});
			layout.addComponent(b);
		}
		popup.setContent(layout);
		return new VerticalLayout(popup);
	}

	@Override
	public Class getType() {
		return TypWiadomosci.class;
	}
}