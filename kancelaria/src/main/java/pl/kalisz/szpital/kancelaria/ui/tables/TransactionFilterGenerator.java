package pl.kalisz.szpital.kancelaria.ui.tables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.apache.log4j.Logger;
import org.tepi.filtertable.FilterGenerator;
import org.vaadin.appfoundation.authentication.data.User;

import pl.kalisz.szpital.db.DbHelper;
import pl.kalisz.szpital.db.kancelaria.Adres;
import pl.kalisz.szpital.db.kancelaria.Transaction;
import pl.kalisz.szpital.db.kancelaria.TypPisma;
import pl.kalisz.szpital.db.kancelaria.enums.TypWiadomosci;
import pl.kalisz.szpital.kancelaria.KancelariaUI;
import pl.kalisz.szpital.utils.Strings;

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

	private static final Logger LOGGER = Logger
			.getLogger(TransactionFilterGenerator.class);

	/** The checkboxed items. */
	private final HashSet<Long> checkboxedItems = new HashSet<Long>();

	/**
	 * Adds the item id.
	 * 
	 * @param itemId
	 *            the item id
	 */
	public final void addItemId(final Long itemId) {
		checkboxedItems.add(itemId);
	}

	/**
	 * Removes the item id.
	 * 
	 * @param itemId
	 *            the item id
	 */
	public final void removeItemId(final Long itemId) {
		checkboxedItems.remove(itemId);
	}

	/**
	 * Checks if is checked.
	 * 
	 * @param itemId
	 *            the item id
	 * @return true, if is checked
	 */
	public final boolean isChecked(final Long itemId) {
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
	 * @see
	 * org.tepi.filtertable.FilterGenerator#generateFilter(java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public final Filter generateFilter(final Object propertyId,
			final Object value) {
		LOGGER.info(propertyId + " filter set to " + value);
		if (Transaction.ID.equals(propertyId)) {
			if (value != null && value instanceof Boolean) {
				ArrayList<Filter> filters = new ArrayList<Filter>();
				for (Long id : checkboxedItems) {
					filters.add(new Compare.Equal(Transaction.ID, id));
				}
				LOGGER.debug(Arrays.toString(checkboxedItems.toArray()));
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
				return new Compare.Equal(Transaction.TYP_PISMA_STR,
						new TypPisma((String) value));
			}
		} else if (Transaction.TYP_WIADOMOSCI.equals(propertyId)) {
			if (value != null && value instanceof TypWiadomosci) {
				return ((TypWiadomosci) value).getFilter();
			}
		} else if (Transaction.USER.equals(propertyId) && value != null
				&& value instanceof User) {
			LOGGER.info("BINGO");
			return new Compare.Equal(Transaction.USER + "." + "id",
					((User) value).getId());
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tepi.filtertable.FilterGenerator#generateFilter(java.lang.Object,
	 * com.vaadin.ui.Field)
	 */
	@Override
	public final Filter generateFilter(final Object propertyId,
			final Field<?> originatingField) {
		LOGGER.info(propertyId + " null field generator");
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tepi.filtertable.FilterGenerator#getCustomFilterComponent(java.lang
	 * .Object)
	 */
	@Override
	public final AbstractField<?> getCustomFilterComponent(
			final Object propertyId) {
		KancelariaUI ui = ((KancelariaUI) UI.getCurrent());
		if (Transaction.TYP_PISMA_STR.equals(propertyId)) {
			ComboBox box = new ComboBox();
			box.setContainerDataSource(ui.getDbHelper().getContainer(
					DbHelper.TYP_PISMA_CONTAINER));
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
			box.setItemCaptionPropertyId("username");
			box.setContainerDataSource(ui.getDbHelper().getContainer(
					DbHelper.USER_CONTAINER));
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
		LOGGER.trace("filter removed");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tepi.filtertable.FilterGenerator#filterAdded(java.lang.Object,
	 * java.lang.Class, java.lang.Object)
	 */
	@Override
	public final void filterAdded(final Object propertyId,
			final Class<? extends Filter> filterType, final Object value) {
		// do nothing
		LOGGER.trace("filter added");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tepi.filtertable.FilterGenerator#filterGeneratorFailed(java.lang.
	 * Exception, java.lang.Object, java.lang.Object)
	 */
	@Override
	public final Filter filterGeneratorFailed(final Exception reason,
			final Object propertyId, final Object value) {
		LOGGER.trace("filter failed");
		return null;
	}

}
