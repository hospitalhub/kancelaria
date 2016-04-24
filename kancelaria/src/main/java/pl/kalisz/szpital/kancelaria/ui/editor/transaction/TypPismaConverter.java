package pl.kalisz.szpital.kancelaria.ui.editor.transaction;

import java.util.Locale;

import com.vaadin.data.util.converter.Converter;

import pl.kalisz.szpital.kancelaria.data.pojo.TypPisma;

final class TypPismaConverter<Object, T> implements Converter<Object, T> {

  @Override
  public T convertToModel(Object value, Class<? extends T> targetType, Locale locale)
      throws com.vaadin.data.util.converter.Converter.ConversionException {
    return (T) new TypPisma(value.toString());
  }

  @Override
  public Object convertToPresentation(T value, Class<? extends Object> targetType, Locale locale)
      throws com.vaadin.data.util.converter.Converter.ConversionException {
    return (Object) ((TypPisma) value).toString();
  }

  @Override
  public Class<T> getModelType() {
    return (Class<T>) java.lang.Object.class;
  }

  @Override
  public Class<Object> getPresentationType() {
    Object o = (Object) "";
    return (Class<Object>) o.getClass();
  }


}