package pl.kalisz.szpital.kancelaria.ui.menu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.log4j.Logger;
import org.tepi.filtertable.datefilter.DateInterval;

import pl.kalisz.szpital.db.kancelaria.Transaction;
import pl.kalisz.szpital.db.kancelaria.enums.TypWiadomosci;
import pl.kalisz.szpital.kancelaria.data.filerepo.TempFileLocationStrategy;
import pl.kalisz.szpital.kancelaria.raport.RaportEtykietyPdf;
import pl.kalisz.szpital.kancelaria.raport.RaportKorespondencjaPdf;
import pl.kalisz.szpital.kancelaria.raport.RaportPocztaPdf;
import pl.kalisz.szpital.kancelaria.ui.tables.TransactionTable;
import pl.kalisz.szpital.kancelaria.ui.windows.PdfDocumentWindow;
import pl.kalisz.szpital.kancelaria.utils.DateUtils;
import pl.kalisz.szpital.utils.Strings;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
public class ReportCommand implements Command {

	/** The Constant DAY_IN_MILLIS. */
	private static final int DAY_IN_MILLIS = 1000 * 60 * 60 * 24;

	/** The Constant logger. */
	private static final Logger LOGGER = Logger.getLogger(ReportCommand.class);

	private JPAContainer<Transaction> container;

	private TransactionTable table;

	public ReportCommand(TransactionTable table, JPAContainer<Transaction> container) {
		this.table = table;
		this.container = container;
	}

	/**
	 * Check date filter and set.
	 */
	private void checkDateFilterAndSet() {
		Object obj = table.getFilterFieldValue(Transaction.DATA_STR);
		Date from = DateUtils.getBegginning(new Date());
		Date to = new Date();
		if (obj != null && obj instanceof DateInterval) {
			LOGGER.debug((DateInterval) obj);
			DateInterval dateInterval = (DateInterval) obj;
			if (dateInterval.getFrom() != null) {
				from = DateUtils.getBegginning(dateInterval.getFrom());
				LOGGER.trace("FROM" + from);
			}
			if (dateInterval.getTo() != null) {
				to = DateUtils.getEnd(dateInterval.getTo());
				LOGGER.trace("TO" + to);
			}
			LOGGER.trace(to.getTime() - from.getTime());
			if (to.getTime() - from.getTime() > DAY_IN_MILLIS) {
				to = DateUtils.getEnd(from);
				Notification
						.show("Data końcowa raportu taka sama jak początkowa.");
			}
		} else {
			Notification.show("Ustawiono datę dzisiejszą dla raportu.");
		}
		table.setFilterFieldValue(Transaction.DATA_STR, new DateInterval(from,
				to));
	}

	@Override
	public void menuSelected(final MenuItem selectedItem) {
		if (Strings.BIEZACE_ZDARZENIA.equals(selectedItem.getText())) {
			buildRaport();
		} else if (Strings.RAPORT_POCZTOWY_POLECONE.equals(selectedItem
				.getText())) {
			buildRaportPolecone();
		} else if (Strings.RAPORT_ETYKIETY_ADRESÓW.equals(selectedItem
				.getText())) {
			buildRaportEtykiety();
		}
	}

	/**
	 * Builds the raport.
	 */
	private void buildRaport() {
		checkDateFilterAndSet();
		ArrayList<Transaction> ts = new ArrayList<Transaction>();
		table.sortTable(true);
		Collection<Object> ids = container.getItemIds();
		for (Object id : ids) {
			ts.add(container.getItem(id).getEntity());
		}
		// TODO(AM) przekazywać pdf -> stream resource tworz wewnatrz
		// pdfdocumentwindow
		StreamResource resource = new StreamResource(
				new RaportKorespondencjaPdf(ts), new TempFileLocationStrategy()
						.getFile(null).getName());
		((StreamResource) resource).setMIMEType(Strings.APPLICATION_PDF);
		PdfDocumentWindow documentWindow = new PdfDocumentWindow(resource);
		UI.getCurrent().addWindow(documentWindow);
		table.sortTable(false);
	}

	/**
	 * Builds the raport polecone.
	 */
	private void buildRaportPolecone() {
		table.setFilterFieldValue(
				Transaction.TYP_WIADOMOSCI, TypWiadomosci.WYCHODZĄCE_POLECONE);
		checkDateFilterAndSet();
		table.sortTable(true);
		ArrayList<Transaction> ts = new ArrayList<Transaction>();
		for (Object id : container.getItemIds()) {
			ts.add(container.getItem(id).getEntity());
		}
		StreamResource resource = new StreamResource(new RaportPocztaPdf(ts),
				new TempFileLocationStrategy().getFile(null).getName());
		((StreamResource) resource).setMIMEType(Strings.APPLICATION_PDF);
		PdfDocumentWindow documentWindow = new PdfDocumentWindow(resource);
		UI.getCurrent().addWindow(documentWindow);
		table.sortTable(false);
	}

	/**
	 * Builds the raport polecone.
	 */
	private void buildRaportEtykiety() {
		// TYLKO WYCHODZĄCE
		// Filter f1 = new Equal(Transaction.PRZYCHODZACY_STR, false);
		// container.addContainerFilter(f1);
		checkDateFilterAndSet();
		table.sortTable(true);
		ArrayList<Transaction> ts = new ArrayList<Transaction>();
		for (Object id : container.getItemIds()) {
			ts.add(container.getItem(id).getEntity());
		}
		// container.removeContainerFilter(f1);
		StreamResource resource = new StreamResource(new RaportEtykietyPdf(ts),
				new TempFileLocationStrategy().getFile(null).getName());
		((StreamResource) resource).setMIMEType(Strings.APPLICATION_PDF);
		PdfDocumentWindow documentWindow = new PdfDocumentWindow(resource);
		UI.getCurrent().addWindow(documentWindow);
		table.sortTable(false);
	}

}