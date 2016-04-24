package pl.kalisz.szpital.kancelaria.converters;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import com.vaadin.data.util.converter.Converter;

import pl.kalisz.szpital.db.kancelaria.enums.KomorkaOrganizacyjna;

@SuppressWarnings("serial")
public class StringToSetConverter implements Converter<Set, String> {
	@Override
	public String convertToModel(Set value, Class targetType, Locale locale)
			throws ConversionException {
		if (value == null || value.size() == 0) {
			return "";
		}
		HashSet<KomorkaOrganizacyjna> koSet = new HashSet<KomorkaOrganizacyjna>();
		StringBuilder builder = new StringBuilder();
		for (Object ko : value) {
			builder.append(((KomorkaOrganizacyjna)ko).getKod());
			builder.append(" ");
		}
		return builder.toString();
	}

	@Override
	public Set convertToPresentation(String value, Class targetType,
			Locale locale) throws ConversionException {
		HashSet<KomorkaOrganizacyjna> koSet = new HashSet<KomorkaOrganizacyjna>();
		if (value != null && !"".equals(value)) {
			String codes[] = value.split(" ");
			for (String code : codes) { 
				// TODO test na kody z małą literą (uppercase) np LPSNFd
				KomorkaOrganizacyjna ko = KomorkaOrganizacyjna.valueOf(code.toUpperCase());
				koSet.add(ko);
			}
		}
		return koSet;
	}

	@Override
	public Class getModelType() {
		return String.class;
	}

	@Override
	public Class getPresentationType() {
		return Set.class;
	}
}