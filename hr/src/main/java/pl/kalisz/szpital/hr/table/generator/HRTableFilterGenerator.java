package pl.kalisz.szpital.hr.table.generator;

import org.tepi.filtertable.FilterGenerator;

import pl.kalisz.szpital.hr.UIComponentsFactory;
import pl.kalisz.szpital.hr.data.entity.Kontraktowiec;
import pl.kalisz.szpital.hr.data.entity.Specjalizacja;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Field;

@SuppressWarnings("serial")
public class HRTableFilterGenerator implements FilterGenerator {

	JPAContainer<Specjalizacja> container;

	public HRTableFilterGenerator(JPAContainer<Specjalizacja> container) {
		this.container = container;
	}

	@Override
	public AbstractField<?> getCustomFilterComponent(Object propertyId) {
		if (Kontraktowiec.SPECJALIZACJE.equals(propertyId)) {
			ComboBox box = new ComboBox();
			box.setContainerDataSource(container);
			box.setItemCaptionPropertyId(Specjalizacja.NAZWA);
			return box;
		} else if (Kontraktowiec.ARCHIWALNY.equals(propertyId)) {
			return UIComponentsFactory.twoOptionsCombo("TAK", "NIE");
		} else if (Kontraktowiec.PLEC.equals(propertyId)) {
			return UIComponentsFactory.twoOptionsCombo("mężczyzna", "kobieta");
		}
		return null;
	}

	@Override
	public void filterRemoved(Object propertyId) {
	}

	@Override
	public Filter filterGeneratorFailed(Exception reason, Object propertyId,
			Object value) {
		return null;
	}

	@Override
	public void filterAdded(Object propertyId,
			Class<? extends Filter> filterType, Object value) {
	}

	@Override
	public Filter generateFilter(Object propertyId, Object value) {
		if (Kontraktowiec.SPECJALIZACJE.equals(propertyId)) {
			Specjalizacja s = container.getItem(value).getEntity();
			return new Compare.Equal(propertyId, s);
		}
		return null;
	}

	@Override
	public Filter generateFilter(Object propertyId, Field<?> originatingField) {
		return null;
	}
}