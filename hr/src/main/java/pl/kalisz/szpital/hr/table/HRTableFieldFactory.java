package pl.kalisz.szpital.hr.table;

import org.vaadin.tokenfield.TokenField;

import pl.kalisz.szpital.hr.DBHelper;
import pl.kalisz.szpital.hr.HRUI;
import pl.kalisz.szpital.hr.UIComponentsFactory;
import pl.kalisz.szpital.hr.data.entity.Kontraktowiec;
import pl.kalisz.szpital.hr.data.entity.Specjalizacja;
import pl.kalisz.szpital.hr.table.converter.SpecjalizacjaToIdConverter;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Container;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TableFieldFactory;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
public class HRTableFieldFactory implements TableFieldFactory {

	@Override
	public Field<?> createField(Container container, Object itemId,
			Object propertyId, Component uiContext) {

		if (Kontraktowiec.SPECJALIZACJE.equals(propertyId)) {
			ListSelect listField = new ListSelect();
			listField.setMultiSelect(true);
			listField.setImmediate(true);
			listField.setItemCaptionPropertyId(Specjalizacja.NAZWA);
			DBHelper d = ((HRUI) UI.getCurrent()).getDBHelper();
			JPAContainer c;
			try {
				c = d.getContainer(Specjalizacja.class);
				listField.setContainerDataSource(c);
			} catch (Exception e) {
				e.printStackTrace();
			}
			listField.sanitizeSelection();
			TokenField field = new TokenField();
			field.setContainerDataSource(listField);
			field.setTokenCaptionPropertyId(Specjalizacja.NAZWA);
			field.setConverter(new SpecjalizacjaToIdConverter());
			field.setConversionError("Nieprawidłowa nazwa");
			field.setFilteringMode(FilteringMode.CONTAINS);
			field.setImmediate(false);
			return field;
		} else if (propertyId.toString().startsWith("data")) {
			PopupDateField df = new PopupDateField();
			df.setDateFormat("yyyy-MM-dd");
			df.setWidth(50, Unit.PIXELS);
			return df;
		} else if (Kontraktowiec.ARCHIWALNY.equals(propertyId)) {
			return UIComponentsFactory.twoOptionsCombo("TAK","NIE");
		} else if (propertyId.toString().startsWith("adres")) {
			return new TextArea(); 
		} else if (Kontraktowiec.PLEC.equals(propertyId)) {
			return UIComponentsFactory.twoOptionsCombo("mężczyzna","kobieta");
		}
		return new TextField();
	}
}