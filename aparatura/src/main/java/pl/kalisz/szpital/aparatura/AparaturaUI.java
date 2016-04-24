package pl.kalisz.szpital.aparatura;

import javax.inject.Inject;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import pl.kalisz.szpital.aparatura.grid.GridLayout;
import pl.kalisz.szpital.aparatura.grid.HospitalGrid;
import pl.kalisz.szpital.aparatura.grid.transaction.TransactionGridLayout;
import pl.kalisz.szpital.aparatura.grid.urzadzenie.UrzadzeniaGridLayout;
import pl.kalisz.szpital.aparatura.grid.zlecenie.ZlecenieGridLayout;
import pl.kalisz.szpital.db.DbHelper;
import pl.kalisz.szpital.db.aparatura.Faktura;
import pl.kalisz.szpital.db.aparatura.Oferta;
import pl.kalisz.szpital.db.aparatura.Umowa;
import pl.kalisz.szpital.db.aparatura.Urzadzenie;
import pl.kalisz.szpital.db.aparatura.Zlecenie;
import pl.kalisz.szpital.db.aparatura.ZlecenieDetails;
import pl.kalisz.szpital.db.kancelaria.Transaction;
import pl.kalisz.szpital.hospitalwidgetset.HospitalSplitPanel;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.server.VaadinCDIServlet;
import com.vaadin.data.Container;
import com.vaadin.data.Container.Indexed;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Theme("reindeer")
@Title("WSZ Aparatura")
@CDIUI("")
public class AparaturaUI extends UI {

	private final static String WIDGETSET = "pl.kalisz.szpital.hospitalwidgetset.HospitalWidgetSet";

	@WebServlet(value = "/*", asyncSupported = false, initParams = { @WebInitParam(name = "session-timeout", value = "60") })
	@VaadinServletConfiguration(productionMode = false, ui = AparaturaUI.class, widgetset = WIDGETSET)
	public static class Servlet extends VaadinCDIServlet {
	}

	@Inject
	DbHelper dbHelper;

	@Inject
	ZlecenieGridLayout zlecenieGridLayout;
	@Inject
	TransactionGridLayout dokumentyGridLayout;

	private Grid getGrid(Class c) {
		Container container = dbHelper.getContainer(c);
		container.removeContainerProperty("id");
		container.removeContainerProperty("consistencyVersion");
		container.removeContainerProperty("nrZlecenia");
		HospitalGrid g = new HospitalGrid((Indexed) container) {

			@Override
			public void setRenderers() {
			}

			@Override
			public void prepareColumns() {
				switch (c.getSimpleName()) {
				case "ZlecenieDetails":
					setColumnOrder(ZlecenieDetails.VISIBLE);
					break;
				}
			}
		};
		return g;
	}

	@Override
	protected void init(VaadinRequest request) {
		// GridLayout<Zlecenie> zlecenieGridLayout = new ZlecenieGridLayout(
		// Zlecenie.class);
		// TransactionGridLayout dokumentyGridLayout = new
		// TransactionGridLayout(
		// Transaction.class);
		UrzadzeniaGridLayout urzadzeniaGridLayout = new UrzadzeniaGridLayout(
				Urzadzenie.class);
		HospitalSplitPanel hospitalSplitPanel = new HospitalSplitPanel();
		hospitalSplitPanel.addMasterSheet(zlecenieGridLayout, "Zlecenia");
		hospitalSplitPanel.addMasterSheet(urzadzeniaGridLayout, "Urządzenia");
		hospitalSplitPanel.addMasterSheet(dokumentyGridLayout, "Dokumenty");
		hospitalSplitPanel.addSlaveSheet(getGrid(ZlecenieDetails.class),
				"Szczegóły");
		hospitalSplitPanel.addSlaveSheet(getGrid(Umowa.class), "Umowy");
		hospitalSplitPanel.addSlaveSheet(getGrid(Faktura.class), "Faktury");
		hospitalSplitPanel.addSlaveSheet(getGrid(Oferta.class), "Oferty");
		hospitalSplitPanel.addSlaveSheet(new CssLayout(), "Raporty");
		setContent(hospitalSplitPanel);
	}

}
