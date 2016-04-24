package pl.kalisz.szpital.kancelaria.ui.tables;

import java.util.Locale;

import org.tepi.filtertable.FilterDecorator;
import org.tepi.filtertable.numberfilter.NumberFilterPopupConfig;

import com.vaadin.server.Resource;
import com.vaadin.shared.ui.datefield.Resolution;

import pl.kalisz.szpital.utils.Strings;

/**
 * The Class TransactionFilterDecorator.
 */
@SuppressWarnings("serial")
public class TransactionFilterDecorator implements FilterDecorator {

  /*
   * (non-Javadoc)
   * 
   * @see org.tepi.filtertable.FilterDecorator#getEnumFilterDisplayName(java.lang.Object,
   * java.lang.Object)
   */
  @Override
  public String getEnumFilterDisplayName(Object propertyId, Object value) {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tepi.filtertable.FilterDecorator#getEnumFilterIcon(java.lang.Object, java.lang.Object)
   */
  @Override
  public Resource getEnumFilterIcon(Object propertyId, Object value) {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tepi.filtertable.FilterDecorator#getBooleanFilterDisplayName(java.lang.Object,
   * boolean)
   */
  @Override
  public String getBooleanFilterDisplayName(Object propertyId, boolean value) {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tepi.filtertable.FilterDecorator#getBooleanFilterIcon(java.lang.Object, boolean)
   */
  @Override
  public Resource getBooleanFilterIcon(Object propertyId, boolean value) {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tepi.filtertable.FilterDecorator#isTextFilterImmediate(java.lang.Object)
   */
  @Override
  public boolean isTextFilterImmediate(Object propertyId) {
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tepi.filtertable.FilterDecorator#getTextChangeTimeout(java.lang.Object)
   */
  @Override
  public int getTextChangeTimeout(Object propertyId) {
    return 0;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tepi.filtertable.FilterDecorator#getFromCaption()
   */
  @Override
  public String getFromCaption() {
    return Strings.OD;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tepi.filtertable.FilterDecorator#getToCaption()
   */
  @Override
  public String getToCaption() {
    return Strings.DO;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tepi.filtertable.FilterDecorator#getSetCaption()
   */
  @Override
  public String getSetCaption() {
    return Strings.USTAW;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tepi.filtertable.FilterDecorator#getClearCaption()
   */
  @Override
  public String getClearCaption() {
    return Strings.WYCZYSC;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tepi.filtertable.FilterDecorator#getDateFieldResolution(java.lang.Object)
   */
  @Override
  public Resolution getDateFieldResolution(Object propertyId) {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tepi.filtertable.FilterDecorator#getDateFormatPattern(java.lang.Object)
   */
  @Override
  public String getDateFormatPattern(Object propertyId) {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tepi.filtertable.FilterDecorator#getLocale()
   */
  @Override
  public Locale getLocale() {
    return new Locale("PL", "pl");
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tepi.filtertable.FilterDecorator#getAllItemsVisibleString()
   */
  @Override
  public String getAllItemsVisibleString() {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tepi.filtertable.FilterDecorator#getNumberFilterPopupConfig()
   */
  @Override
  public NumberFilterPopupConfig getNumberFilterPopupConfig() {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tepi.filtertable.FilterDecorator#usePopupForNumericProperty(java.lang.Object)
   */
  @Override
  public boolean usePopupForNumericProperty(Object propertyId) {
    return false;
  }

}
