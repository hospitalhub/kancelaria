package pl.kalisz.szpital.hr.table.converter;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import pl.kalisz.szpital.hr.HRUI;
import pl.kalisz.szpital.hr.data.entity.Specjalizacja;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.UI;

public class SpecjalizacjaToIdConverter implements Converter<Set, Set> {
	@Override
	public Set convertToModel(Set value, Class<? extends Set> targetType,
			Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		Set<Specjalizacja> set = new HashSet<Specjalizacja>();
		for (Integer o : (Set<Integer>) value) {
			try {
				JPAContainer c = ((HRUI) UI.getCurrent()).getDBHelper()
						.getContainer(Specjalizacja.class);
				Specjalizacja s = (Specjalizacja) c.getItem(o).getEntity();
				set.add(s);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return set;
	}

	@Override
	public Set convertToPresentation(Set value,
			Class<? extends Set> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		Set<Integer> set = new HashSet<Integer>();
		if (value != null) {
			for (Specjalizacja o : (Set<Specjalizacja>) value) {
				set.add(o.getId());
			}
		}
		return set;
	}

	@Override
	public Class<Set> getModelType() {
		return Set.class;
	}

	@Override
	public Class<Set> getPresentationType() {
		return Set.class;
	}
}