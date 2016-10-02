package pl.kalisz.szpital.kancelaria.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.tepi.filtertable.FilterGenerator;

import pl.kalisz.szpital.kancelaria.DashboardUI;
import pl.kalisz.szpital.kancelaria.data.db.DbHelper;
import pl.kalisz.szpital.kancelaria.data.enums.TypWiadomosci;
import pl.kalisz.szpital.kancelaria.data.pojo.Adres;
import pl.kalisz.szpital.kancelaria.data.pojo.Role;
import pl.kalisz.szpital.kancelaria.data.pojo.Transaction;
import pl.kalisz.szpital.kancelaria.data.pojo.TypPisma;
import pl.kalisz.szpital.kancelaria.data.pojo.User;
import pl.kalisz.szpital.kancelaria.utils.Strings;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.data.util.filter.Or;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.Field;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

/**
 * The Class TransactionFilterGenerator.
 */
@SuppressWarnings("serial")
public class TransactionFilterGenerator implements FilterGenerator {

  /** The checkboxed items. */
  private final HashSet<Integer> checkboxedItems = new HashSet<Integer>();

  /**
   * Adds the item id.
   * 
   * @param itemId the item id
   */
  public final void addItemId(final Integer itemId) {
    checkboxedItems.add(itemId);
  }

  /**
   * Removes the item id.
   * 
   * @param itemId the item id
   */
  public final void removeItemId(final Integer itemId) {
    checkboxedItems.remove(itemId);
  }

  /**
   * Checks if is checked.
   * 
   * @param itemId the item id
   * @return true, if is checked
   */
  public final boolean isChecked(final Integer itemId) {
    return checkboxedItems.contains(itemId);
  }

  /**
   * Reset.
   */
  public final void reset() {
    checkboxedItems.clear();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tepi.filtertable.FilterGenerator#generateFilter(java.lang.Object, java.lang.Object)
   */
  @Override
  public final Filter generateFilter(final Object propertyId, final Object value) {
    if (Transaction.ID.equals(propertyId)) {
      System.out.println("####");
      if (value != null && value instanceof Boolean) {
        System.out.println("bool");
        ArrayList<Filter> filters = new ArrayList<Filter>();
        for (Integer id : checkboxedItems) {
          filters.add(new Compare.Equal(Transaction.ID, id));
        }
        System.out.println(Arrays.toString(checkboxedItems.toArray()));
        return new Or(filters.toArray(new Filter[] {}));
      } else if (value != null && value instanceof String) {
        try {
          Integer id = Integer.parseInt(value.toString());
          return new Compare.Equal(Transaction.ID, id);
        } catch (NumberFormatException e) {
          Notification.show("Błędna wartość");
          return new Compare.Equal(propertyId, 0);
        }
      }
    } else if (Transaction.ADRES.equals(propertyId)) {
      if (value != null && value instanceof Adres) {
        return new Compare.Equal(Transaction.ADRES, (Adres) value);
      }
    } else if (Transaction.TYP_PISMA_STR.equals(propertyId)) {
      if (value != null && value instanceof String) {
        return new Compare.Equal(Transaction.TYP_PISMA_STR, new TypPisma((String) value));
      }
    } else if (Transaction.TYP_WIADOMOSCI.equals(propertyId)) {
      if (value != null && value instanceof TypWiadomosci) {
        return ((TypWiadomosci) value).getFilter();
      }
    } else if (Transaction.USER.equals(propertyId) && value != null && value instanceof Integer) {
      DbHelper dbHelper = ((DashboardUI) UI.getCurrent()).getDbHelper();
      @SuppressWarnings("unchecked")
      JPAContainer<User> userContainer = dbHelper.getContainer(DbHelper.USER_CONTAINER);
      User u = userContainer.getItem(value).getEntity();
      return new Compare.Equal(Transaction.USER, u);
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tepi.filtertable.FilterGenerator#generateFilter(java.lang.Object, com.vaadin.ui.Field)
   */
  @Override
  public final Filter generateFilter(final Object propertyId, final Field<?> originatingField) {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tepi.filtertable.FilterGenerator#getCustomFilterComponent(java.lang.Object)
   */
  @Override
  public final AbstractField<?> getCustomFilterComponent(final Object propertyId) {
    if (Transaction.TYP_PISMA_STR.equals(propertyId)) {
      ComboBox box = new ComboBox();
      DashboardUI dashboardUI = ((DashboardUI) UI.getCurrent());
      @SuppressWarnings("unchecked")
      JPAContainer<TypPisma> typPismaContainer =
          dashboardUI.getDbHelper().getContainer(DbHelper.TYP_PISMA_CONTAINER);
      box.setContainerDataSource(typPismaContainer);
      return box;
    } else if (Transaction.ADRES.equals(propertyId)) {
      final CustomField<Adres> cf = new AddressSearchCustomField();
      return cf;
    } else if (Transaction.ID.equals(propertyId)) {
      ComboBox box = new ComboBox();
      box.addItem(Boolean.TRUE);
      box.setItemCaption(Boolean.TRUE, Strings.TYLKO_WYBRANE);
      box.setNewItemsAllowed(true);
      return box;
    } else if (Transaction.USER.equals(propertyId)) {
      ComboBox box = new ComboBox();
      DbHelper dbHelper = ((DashboardUI) UI.getCurrent()).getDbHelper();
      @SuppressWarnings("unchecked")
      JPAContainer<User> userContainer = dbHelper.getContainer(DbHelper.USER_CONTAINER);
      box.setContainerDataSource(userContainer);
      box.setItemCaptionPropertyId(User.LOGIN);
      User u = ((DashboardUI) UI.getCurrent()).getUser();
      // box.setValue(u.getId());
      if (!u.hasRole(Role.POWER_USER)) {
        box.setEnabled(false);
      }
      return box;
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tepi.filtertable.FilterGenerator#filterRemoved(java.lang.Object)
   */
  @Override
  public final void filterRemoved(final Object propertyId) {
    System.out.println("removed");
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tepi.filtertable.FilterGenerator#filterAdded(java.lang.Object, java.lang.Class,
   * java.lang.Object)
   */
  @Override
  public final void filterAdded(final Object propertyId, final Class<? extends Filter> filterType,
      final Object value) {
    System.out.println("added" + propertyId + " " + filterType + " " + value);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tepi.filtertable.FilterGenerator#filterGeneratorFailed(java.lang.Exception,
   * java.lang.Object, java.lang.Object)
   */
  @Override
  public final Filter filterGeneratorFailed(final Exception reason, final Object propertyId,
      final Object value) {
    System.out.println("failed" + propertyId + " " + reason.getMessage() + " " + value);
    return null;
  }

}
