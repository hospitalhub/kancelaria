package pl.kalisz.szpital.kancelaria.ui.editor.transaction;

import java.util.Collection;
import java.util.Locale;

import com.vaadin.data.Property;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Field;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class CustomField<T> extends VerticalLayout implements Field<T> {

  final class ObjectConvereter implements Converter<String, T> {

    @Override
    public T convertToModel(String value1, Class<? extends T> targetType, Locale locale)
        throws com.vaadin.data.util.converter.Converter.ConversionException {
      return value;
    }

    @Override
    public String convertToPresentation(T value1, Class<? extends String> targetType, Locale locale)
        throws com.vaadin.data.util.converter.Converter.ConversionException {
      value = value1;
      return value.toString();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<T> getModelType() {
      if (value == null) {
        return (Class<T>) new Object().getClass();
      } else {
        return (Class<T>) value.getClass();
      }
    }

    @Override
    public Class<String> getPresentationType() {
      return String.class;
    }
  }

  T value;
  T oldValue;
  @SuppressWarnings("rawtypes")
  AbstractField field;
  Button button = new Button();
  ClickListener listener;

  public void addClickListener(ClickListener listener) {
    button.addClickListener(listener);
  }


  @Override
  public void focus() {
    super.focus();
  }


  @Override
  public void setCaption(String caption) {
    super.setCaption(caption);
    button.setCaption(caption);
  }

  @Override
  public boolean isInvalidCommitted() {
    return false;
  }



  @Override
  public void setInvalidCommitted(boolean isCommitted) {}



  @Override
  public void commit() throws SourceException, InvalidValueException {
    field.commit();
  }


  @SuppressWarnings("unchecked")
  @Override
  public void discard() throws SourceException {
    value = oldValue;
    field.setValue(value.toString());
    field.discard();
  }



  @Override
  public void setBuffered(boolean buffered) {
    field.setBuffered(buffered);
  }



  @Override
  public boolean isBuffered() {
    return field.isBuffered();
  }



  @Override
  public boolean isModified() {
    return field.isModified();
  }



  @Override
  public void addValidator(Validator validator) {
    field.addValidator(validator);
  }



  @Override
  public void removeValidator(Validator validator) {
    field.removeValidator(validator);
  }



  @Override
  public void removeAllValidators() {
    field.removeAllValidators();
  }



  @SuppressWarnings("unchecked")
  @Override
  public Collection<Validator> getValidators() {
    return field.getValidators();
  }



  @Override
  public boolean isValid() {
    return field.isValid();
  }



  @Override
  public void validate() throws InvalidValueException {
    field.validate();
  }



  @Override
  public boolean isInvalidAllowed() {
    return false;
  }



  @Override
  public void setInvalidAllowed(boolean invalidValueAllowed) throws UnsupportedOperationException {}



  @Override
  public T getValue() {
    return value;
  }



  @SuppressWarnings("unchecked")
  @Override
  public void setValue(T newValue) throws com.vaadin.data.Property.ReadOnlyException {
    if (newValue != null) {
      this.oldValue = value;
      this.value = newValue;
      if (this.field != null && newValue.toString() != null) {
        this.field.setValue(newValue.toString());
      }
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public Class<? extends T> getType() {
    if (this.value != null) {
      return (Class<? extends T>) this.value.getClass();
    } else {
      return (Class<T>) Object.class;
    }
  }


  @Override
  public void addValueChangeListener(com.vaadin.data.Property.ValueChangeListener listener) {
    field.addValueChangeListener(listener);
  }



  @Override
  public void addListener(com.vaadin.data.Property.ValueChangeListener listener) {}



  @Override
  public void removeValueChangeListener(com.vaadin.data.Property.ValueChangeListener listener) {
    field.removeValueChangeListener(listener);
  }



  @Override
  public void removeListener(com.vaadin.data.Property.ValueChangeListener listener) {}



  @Override
  public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
    field.valueChange(event);
  }



  @SuppressWarnings("rawtypes")
  @Override
  public void setPropertyDataSource(Property newDataSource) {
    field.setPropertyDataSource(newDataSource);
  }



  @SuppressWarnings("rawtypes")
  @Override
  public Property getPropertyDataSource() {
    return field.getPropertyDataSource();
  }



  @Override
  public int getTabIndex() {
    return 0;
  }



  @Override
  public void setTabIndex(int tabIndex) {}



  @Override
  public boolean isRequired() {
    return true;
  }



  @Override
  public void setRequired(boolean required) {}



  @Override
  public void setRequiredError(String requiredMessage) {
    field.setRequiredError(requiredMessage);
  }



  @Override
  public String getRequiredError() {
    return field.getRequiredError();
  }



  @Override
  public boolean isEmpty() {
    return field.isEmpty();
  }


  @Override
  public void clear() {
    this.value = null;
    field.clear();
  }

}
