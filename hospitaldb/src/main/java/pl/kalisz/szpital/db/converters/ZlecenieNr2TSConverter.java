package pl.kalisz.szpital.db.converters;

import java.util.Locale;

import com.vaadin.data.util.converter.Converter;

@SuppressWarnings("serial")
public final class ZlecenieNr2TSConverter implements Converter<String, Long> {
	@Override
	public Long convertToModel(String value, Class<? extends Long> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		return null;
	}

	@Override
	public String convertToPresentation(Long value, Class<? extends String> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		return "<small>TS/</small><b style='color:green;background-color:yellow;'>" + value
				+ "</b><small>/2015</small>";
	}

	@Override
	public Class<Long> getModelType() {
		return Long.class;
	}

	@Override
	public Class<String> getPresentationType() {
		return String.class;
	}
}