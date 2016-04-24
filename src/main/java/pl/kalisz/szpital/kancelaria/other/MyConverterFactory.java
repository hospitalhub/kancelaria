package pl.kalisz.szpital.kancelaria.other;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.util.converter.DefaultConverterFactory;

/**
 * A factory for creating MyConverter objects.
 */
@SuppressWarnings("serial")
public class MyConverterFactory extends DefaultConverterFactory {

  /**
   * findConverter.
   */
  @SuppressWarnings("unchecked")
  @Override
  protected <PRESENTATION, MODEL> Converter<PRESENTATION, MODEL> findConverter(
      Class<PRESENTATION> presentationType, Class<MODEL> modelType) {

    if (presentationType == String.class && modelType == Calendar.class) {
      return ((Converter<PRESENTATION, MODEL>) new Converter<String, Calendar>() {

        @Override
        public Class<Calendar> getModelType() {
          return Calendar.class;
        }

        @Override
        public Class<String> getPresentationType() {
          return String.class;
        }

        @Override
        public Calendar convertToModel(String value, Class<? extends Calendar> targetType,
            Locale locale) throws Converter.ConversionException {
          return new GregorianCalendar();
        }

        @Override
        public String convertToPresentation(Calendar value, Class<? extends String> targetType,
            Locale locale) throws Converter.ConversionException {
          SimpleDateFormat df = new SimpleDateFormat();
          df.applyPattern("mm/dd/yyyy hh:mm aa");
          return df.format(value.getTime());
        }

      });
    }

    return super.findConverter(presentationType, modelType);
  }
}
