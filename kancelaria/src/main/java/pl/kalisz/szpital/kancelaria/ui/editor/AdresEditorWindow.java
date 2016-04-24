package pl.kalisz.szpital.kancelaria.ui.editor;

import org.apache.log4j.Logger;

import pl.kalisz.szpital.kancelaria.data.pojo.Address;
import pl.kalisz.szpital.kancelaria.utils.Strings;

import com.vaadin.data.Item;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.DefaultFieldGroupFieldFactory;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

/**
 * The Class AdresEditorWindow.
 */
@SuppressWarnings("serial")
public class AdresEditorWindow extends Window {

  /** The Constant logger. */
  private static final Logger logger = Logger.getLogger(AdresEditorWindow.class);

  /** The form. */
  private FormLayout form = new FormLayout();

  /** The editor fields. */
  private FieldGroup editorFields = new FieldGroup();

  /** The save contact button. */
  private Button saveContactButton = new Button("OK");

  /** The cancel button. */
  private Button cancelButton = new Button("Anuluj");

  /** The adres item. */
  private Item adresItem;

  /** The commit. */
  private boolean commit = false;

  /**
   * Gets the adres item.
   * 
   * @return the adres item
   */
  public Item getAdresItem() {
    return adresItem;
  }

  /**
   * Checks if is commit.
   * 
   * @return true, if is commit
   */
  public boolean isCommit() {
    return commit;
  }

  /**
   * Instantiates a new adres editor window.
   * 
   * @param item the item
   */
  public AdresEditorWindow(Item item) {
    this.adresItem = item;
    initEditor();
    initValidation();
  }

  // TODO(AM) zrobic min - maks wielkosc pola (zgodnie z baza danych)
  /**
   * Inits the validation.
   */
  @SuppressWarnings("unchecked")
  private void initValidation() {
    logger.debug("init validation");
    // TODO(AM) usuwać gdy kraj inny niż PL
    Field<String> f = (Field<String>) editorFields.getField(Address.POSTCODE);
    Validator postalCodeValidator =
        new RegexpValidator("[0-9]{2}-[0-9]{3}", "Kod pocztowy w postaci: 00-000 (zamiast {0})");
    f.addValidator(postalCodeValidator);
    Field<String> ulicaField = (Field<String>) editorFields.getField(Address.STREET);
    ulicaField.addValidator(new StringLengthValidator(
        "Wpisz ulicę, maks. liczba znaków to 80 (popraw: {0})", 0, 80, true));
    Field<String> imieField = (Field<String>) editorFields.getField(Address.NAME);
    imieField.addValidator(new StringLengthValidator(
        "Popraw imię, maks. liczba znaków to 30 (popraw: {0})", 0, 29, true));
  }

  /**
   * Validate fields.
   * 
   * @throws InvalidValueException the invalid value exception
   */
  @SuppressWarnings("unchecked")
  private void validateFields() throws InvalidValueException {
    for (Field<String> f : editorFields.getFields().toArray(new Field[] {})) {
      f.validate();
    }
  }

  /**
   * Inits the editor.
   */
  private void initEditor() {
    this.setContent(form);
    setWidth(Strings.PERCENT80);
    setModal(true);
    setClosable(false);
    setResizable(false);
    editorFields.setItemDataSource(adresItem);
    editorFields.setFieldFactory(new FieldGroupFieldFactory() {

      @SuppressWarnings({ "unchecked", "rawtypes" })
      @Override
      public <T extends Field> T createField(Class<?> dataType, Class<T> fieldType) {
//        Field f = new DefaultFieldGroupFieldFactory().createField(dataType, fieldType);
        Field f = DefaultFieldGroupFieldFactory.get().createField(dataType, fieldType);
        if (f instanceof TextField) {
          ((TextField) f).setNullRepresentation("");
          ((TextField) f).setNullSettingAllowed(false);
        }
        f.setWidth(Strings.PERCENT100);
        f.setId(f.getCaption());
        return (T) f;
      }
    });
    for (String fieldName : Address.COLUMN_NAMES) {
      // TextField field = new TextField(fieldName);
      if (fieldName.equals(Address.ID) || fieldName.equals(Address.TEL1) || fieldName.equals(Address.TEL2)
          || fieldName.equals(Address.FAX) || fieldName.equals(Address.MAIL)) {
        continue;
      }
      form.addComponent(editorFields.buildAndBind(fieldName));
    }

    editorFields.setBuffered(false);
    form.addComponent(saveContactButton);
    form.addComponent(cancelButton);
    form.setMargin(true);

    saveContactButton.addClickListener(new ClickListener() {

      // TODO(AM) walidacja
      public void buttonClick(ClickEvent event) {
        try {
          validateFields();
          commit = true;
          close();
        } catch (InvalidValueException e) {
          Notification.show(e.getMessage());
        }
      }
    });

    cancelButton.addClickListener(new ClickListener() {

      @Override
      public void buttonClick(ClickEvent event) {
        close();
      }
    });
  }

  /**
   * Gets the field.
   * 
   * @param fieldName the field name
   * @return the field
   */
  public String getField(String fieldName) {
    if (editorFields.getField(fieldName) != null
        && !editorFields.getField(fieldName).getValue().toString().equals(Strings.EMPTY_STRING)) {
      return editorFields.getField(fieldName).getValue().toString();
    } else {
      return Strings.EMPTY_STRING;
    }
  }

}
