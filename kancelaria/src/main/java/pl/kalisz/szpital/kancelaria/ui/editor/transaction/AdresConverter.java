package pl.kalisz.szpital.kancelaria.ui.editor.transaction;

import java.util.Locale;

import com.vaadin.data.util.converter.Converter;

import pl.kalisz.szpital.kancelaria.data.pojo.Address;

final class AdresConverter implements Converter<String, Address> {
  @Override
  public Address convertToModel(String value, Class<? extends Address> targetType, Locale locale)
      throws com.vaadin.data.util.converter.Converter.ConversionException {
    return new Address();
  }

  @Override
  public String convertToPresentation(Address value, Class<? extends String> targetType,
      Locale locale) throws com.vaadin.data.util.converter.Converter.ConversionException {
    return value.toString();
  }

  @Override
  public Class<Address> getModelType() {
    return Address.class;
  }

  @Override
  public Class<String> getPresentationType() {
    return String.class;
  }
}