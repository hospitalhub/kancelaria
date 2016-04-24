package pl.kalisz.szpital.kancelaria.ui.tables;

import org.vaadin.appfoundation.authentication.SessionHandler;
import org.vaadin.appfoundation.authentication.data.User;
import org.vaadin.appfoundation.persistence.facade.FacadeFactory;

import pl.kalisz.szpital.db.DbHelper;
import pl.kalisz.szpital.db.kancelaria.Transaction;
import pl.kalisz.szpital.kancelaria.KancelariaUI;
import pl.kalisz.szpital.kancelaria.utils.DateUtils;
import pl.kalisz.szpital.utils.Strings;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
public class TransactionTable extends
		PagedFilterTable30<JPAContainer<Transaction>> {

	public TransactionTable(Container container) {
		setContainerDataSource(container);
		setSizeFull();
		addStyleName(Strings.BORDERLESS);
		setImmediate(true);
		setSelectable(true);
		setMultiSelect(false);
		setColumnCollapsingAllowed(true);
		setColumnReorderingAllowed(true);

		setFooterVisible(false);
		addActionHandler(new TransactionActionHandler());

		TransactionFilterGenerator filterGenerator = new TransactionFilterGenerator();
		setFilterGenerator(filterGenerator);
		setFilterDecorator(new TransactionFilterDecorator());

		addGeneratedColumn(Transaction.PLIK_SCIEZKA_STR,
				new TransactionThumbColumnGenerator());

		// ADRES: format
		setCellStyleGenerator(new PreCellStyleGenerator());

		addGeneratedColumn(Transaction.ID, new TransactionIdColumnGenerator(
				filterGenerator));

		setFilterBarVisible(true);

		setVisibleColumns(Transaction.VISIBLE);
		setColumnHeaders(Transaction.HEADERS);
		setColumnCollapsed(Transaction.PLIK_SCIEZKA_STR, true);

		for (Object column : getVisibleColumns()) {
			setColumnExpandRatio(column, 100);
		}
		setColumnExpandRatio(Transaction.ADRES, 200);
		setToDefaultFilters();
	}

	/**
	 * Sets the to default filters.
	 */
	public void setToDefaultFilters() {
//		System.out.println("___");
//		System.out.println("ff" + getFilterFieldValue(Transaction.USER));
		resetFilters();
		TransactionFilterGenerator filterGenerator = (TransactionFilterGenerator) getFilterGenerator();
		filterGenerator.reset();
		// UserOLD u = ((KancelariaUI) UI.getCurrent()).getUser();
		// if (!u.hasRole(Role.POWER_USER)) {
		// FIXME domyślne filtry (ustawienia zapisywane do bazy)
		User u = SessionHandler.get();
		Object o = getFilterFieldValue(Transaction.USER);
		// FIXME TODO u z sessionhandler.get =/= u. z dbhelper.container
//		System.out.println(u);
//		System.out.println(u.getId());
		BeanItemContainer<User> container = (BeanItemContainer<User>) ((KancelariaUI) UI
				.getCurrent()).getDbHelper().getContainer(
				DbHelper.USER_CONTAINER);
		container.addContainerFilter(new Compare.Equal("username", u
				.getUsername()));
		System.out.println(container.getItemIds());
		User u1 = FacadeFactory.getFacade().find(User.class, u.getId());
		filterGenerator.getCustomFilterComponent(Transaction.USER)
				.setConvertedValue(u1);
		filterGenerator.generateFilter(Transaction.USER, u1);
		System.out.println(u1);
		System.out.println(u1.getId());
//		System.out.println("xx" + getFilterFieldValue(Transaction.USER));
		setFilterFieldValue(Transaction.USER, u1);
//		System.out.println("qq" + getFilterFieldValue(Transaction.USER));
		ComboBox box = (ComboBox) filterGenerator
				.getCustomFilterComponent(Transaction.USER);
		box.setValue(u1);
		// }
		sortTable(false);
	}

	/**
	 * Sort table.
	 * 
	 * @param ascending
	 *            the ascending
	 */
	public void sortTable(final boolean ascending) {
		Object[] sortOrder = new Object[] { Transaction.DATA_STR };
		boolean[] sortAsc = new boolean[] { ascending };
		sort(sortOrder, sortAsc);
	}

	@Override
	protected String formatPropertyValue(final Object rowId,
			final Object colId, final Property<?> property) {

		if (colId.equals(Transaction.DATA_STR)) {
			synchronized (this) {
				return DateUtils.DMY.format(property.getValue());
			}
		} else if (colId.equals(Transaction.USER)) {
			User u = (User) property.getValue();
			if (u != null && u.getUsername() != null) {
				return u.getUsername();
			} else {
				return "?";
			}
		}
		return super.formatPropertyValue(rowId, colId, property);
	}
}