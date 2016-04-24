package pl.kalisz.szpital.aparatura.grid.urzadzenie;

import com.vaadin.data.Container.Indexed;
import com.vaadin.ui.renderers.ButtonRenderer;

import pl.kalisz.szpital.aparatura.grid.HospitalFieldFactory;
import pl.kalisz.szpital.aparatura.grid.HospitalGrid;
import pl.kalisz.szpital.db.aparatura.Urzadzenie;

@SuppressWarnings("serial")
public class UrzadzenieGrid extends HospitalGrid {

	public UrzadzenieGrid(Indexed dataSource) {
		super(dataSource);
	}

	@Override
	public void prepareColumns() {
		hideColumns(Urzadzenie.VISIBLE);
		setColumnOrder(Urzadzenie.VISIBLE);
		setEditorFieldFactory(new HospitalFieldFactory());
	}

	@Override
	public void setRenderers() {
		getColumn("Usuń").setRenderer(new ButtonRenderer());
	}
}