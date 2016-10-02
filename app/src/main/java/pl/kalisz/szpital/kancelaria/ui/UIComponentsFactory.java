package pl.kalisz.szpital.kancelaria.ui;

import java.util.Date;
import java.util.Locale;

import com.vaadin.data.validator.NullValidator;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.PopupDateField;

import pl.kalisz.szpital.kancelaria.data.enums.KomorkaOrganizacyjna;
import pl.kalisz.szpital.kancelaria.utils.Strings;

/**
 * A factory for creating UIComponents objects.
 */
public class UIComponentsFactory {

  // TODO(AM) tu przeniesc filtry !! zrobic nadklase dla obiektow
  // komponent + filtr

  /**
   * Builds the filter date.
   *
   * @param date the date
   * @return the popup date field
   */
  public static PopupDateField buildFilterDate(Date date) {
    PopupDateField filterDate = new PopupDateField();
    filterDate.setImmediate(true);
    filterDate.setDateFormat(Strings.DD_MM_YY);
    filterDate.setResolution(Resolution.DAY);
    filterDate.setLocale(new Locale("pl", "PL"));
    filterDate.setDescription(Strings.DATA);
    filterDate.setValue(date);
    return filterDate;
  }

  /**
   * Builds the komorka combo.
   *
   * @param newItemsAllowed the new items allowed
   * @return the combo box
   */
  public static ComboBox buildKomorkaCombo(boolean newItemsAllowed) {
    final ComboBox komorkaCombo = new ComboBox();
    komorkaCombo.setWidth(Strings.PERCENT92);
    for (KomorkaOrganizacyjna s : KomorkaOrganizacyjna.values()) {
      Object itemId = komorkaCombo.addItem(s);
      komorkaCombo.setItemCaption(itemId, s.toString());
    }
    komorkaCombo.setNewItemsAllowed(newItemsAllowed);
    komorkaCombo.setNullSelectionAllowed(false);
    komorkaCombo.setImmediate(true);
    komorkaCombo.setCaption(Strings.KOMORKA_ORG);
    komorkaCombo.setInputPrompt(Strings.WYSZUKIWANIE_KOMOREK_ORG);
    komorkaCombo.addValidator(new NullValidator(Strings.WYBIERZ_KOMORKE_ORGANIZACYJNA, false));
    komorkaCombo.setInputPrompt(Strings.WYSZUKIWANIE_KOMOREK_ORG);
    komorkaCombo.setFilteringMode(FilteringMode.CONTAINS);
    return komorkaCombo;
  }
}
