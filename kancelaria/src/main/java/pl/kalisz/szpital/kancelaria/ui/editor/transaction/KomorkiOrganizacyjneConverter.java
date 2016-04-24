package pl.kalisz.szpital.kancelaria.ui.editor.transaction;

import java.util.Locale;

import com.vaadin.data.util.converter.Converter;

final class KomorkiOrganizacyjneConverter<Object, T> implements Converter<Object, T> {
  @Override
  public T convertToModel(Object value, Class<? extends T> targetType, Locale locale)
      throws com.vaadin.data.util.converter.Converter.ConversionException {
    System.out.println("TO MODEL " + value + value.getClass() + targetType);
    return (T) (value.toString());
  }

  @Override
  public Object convertToPresentation(T value, Class<? extends Object> targetType, Locale locale)
      throws com.vaadin.data.util.converter.Converter.ConversionException {
    System.out.println("TO PRES " + value + value.getClass() + targetType);
    return (Object) (value).toString();
  }

  @Override
  public Class<T> getModelType() {
    T t = null;
    return (Class<T>) t.getClass();
  }

  @Override
  public Class<Object> getPresentationType() {
    Object o = (Object) "";
    return (Class<Object>) o.getClass();
  }

}