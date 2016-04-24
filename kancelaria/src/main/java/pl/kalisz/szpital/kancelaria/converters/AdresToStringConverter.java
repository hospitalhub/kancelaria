package pl.kalisz.szpital.kancelaria.converters;

import java.util.Locale;

import com.vaadin.data.util.converter.Converter;

import pl.kalisz.szpital.db.kancelaria.Adres;

@SuppressWarnings("serial")
public class AdresToStringConverter implements Converter<String, Adres> {
	@Override
	public Adres convertToModel(String value,
			Class<? extends Adres> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		Adres a = new Adres();
		a.setId(Long.parseLong(value));
		return a;
	}

	@Override
	public String convertToPresentation(Adres value,
			Class<? extends String> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		if (value == null) {
			return "";
		}
		return value.getId().toString();
	}

	@Override
	public Class<Adres> getModelType() {
		return Adres.class;
	}

	@Override
	public Class<String> getPresentationType() {
		return String.class;
	}
}