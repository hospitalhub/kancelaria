package pl.kalisz.szpital.aparatura.grid.zlecenie;

import pl.kalisz.szpital.aparatura.grid.HospitalFieldFactory;
import pl.kalisz.szpital.aparatura.grid.HospitalGrid;
import pl.kalisz.szpital.db.aparatura.Zlecenie;
import pl.kalisz.szpital.db.converters.DateConverter;
import pl.kalisz.szpital.db.converters.ZlecenieNr2TSConverter;

import com.vaadin.data.Container.Indexed;
import com.vaadin.ui.renderers.HtmlRenderer;

@SuppressWarnings("serial")
public class ZlecenieGrid extends HospitalGrid {

	public ZlecenieGrid(Indexed dataSource) {
		super(dataSource);
	}

	@Override
	public void prepareColumns() {
		hideColumns(Zlecenie.VISIBLE);
		setColumnOrder(Zlecenie.VISIBLE);
		setEditorFieldFactory(new HospitalFieldFactory());
	}

	@Override
	public void setRenderers() {
//		getColumn("Usuń").setRenderer(new ButtonRenderer());
		getColumn(Zlecenie.NRZLECENIA).setConverter(new ZlecenieNr2TSConverter());
		getColumn(Zlecenie.NRZLECENIA).setRenderer(new HtmlRenderer());
		getColumn(Zlecenie.DATAZLECENIA).setConverter(new DateConverter());
		getColumn(Zlecenie.DATAZLECENIA).setRenderer(new HtmlRenderer());
	}
}