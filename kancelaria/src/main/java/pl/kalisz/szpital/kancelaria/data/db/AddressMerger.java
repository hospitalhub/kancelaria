package pl.kalisz.szpital.kancelaria.data.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.vaadin.appfoundation.persistence.facade.FacadeFactory;

import pl.kalisz.szpital.db.DbHelper;
import pl.kalisz.szpital.db.kancelaria.Adres;
import pl.kalisz.szpital.db.kancelaria.Transaction;
import pl.kalisz.szpital.kancelaria.KancelariaUI;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.ui.UI;

/**
 * The Class AddressMerger.
 */
public class AddressMerger {

	/** The Constant logger. */
	static final Logger LOGGER = Logger.getLogger(DbHelper.class.toString());

	/**
	 * Merge adresy.
	 * 
	 * @param id1
	 *            the id1
	 * @param id
	 *            the id
	 * @return the int
	 */
	public static int mergeAdresy(final Long id1, final Long id) {
//		LOGGER.info("mergeuje " + id + " do " + id1);
//		String query = "select TRANSACTION t WHERE adresid = :adresId";
//		Map<String, Object> parameters = new HashMap<String, Object>();
//		parameters.put("adresId", id);
//		List<Transaction> ts = FacadeFactory.getFacade()
//				.list(query, parameters);
//		Adres newAdres = FacadeFactory.getFacade().find(Adres.class, id1);
//		for (Transaction t : ts) {
//			t.setAdres(newAdres);
//		}
//		FacadeFactory.getFacade().storeAll(ts);
//		int updatedRows = ts.size();
//		return updatedRows;
		return 0;
	}

	private static JPAContainer<Transaction> getContainer() {
		DbHelper dbHelper = ((KancelariaUI) UI.getCurrent()).getDbHelper();
		return (JPAContainer<Transaction>) dbHelper
				.getContainer(DbHelper.TRANSACTION_CONTAINER);
	}
}
