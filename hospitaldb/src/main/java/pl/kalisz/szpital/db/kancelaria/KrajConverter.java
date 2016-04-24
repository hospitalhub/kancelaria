package pl.kalisz.szpital.db.kancelaria;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import pl.kalisz.szpital.db.kancelaria.enums.Kraj;

@Converter(autoApply = true)
public class KrajConverter implements AttributeConverter<Kraj, String> {

	public String convertToDatabaseColumn(Kraj attribute) {
		return attribute.name();
	}

	public Kraj convertToEntityAttribute(String dbData) {
		for (Kraj kraj : Kraj.values()) {
			if (kraj.name().equals(dbData)) {
				return kraj;
			}
		}
		return null;
	}

}
