package pl.kalisz.szpital.aparatura.grid.transaction;

import com.vaadin.data.Container.Indexed;
import com.vaadin.ui.renderers.ButtonRenderer;

import pl.kalisz.szpital.aparatura.grid.HospitalFieldFactory;
import pl.kalisz.szpital.aparatura.grid.HospitalGrid;
import pl.kalisz.szpital.db.kancelaria.Transaction;

@SuppressWarnings("serial")
public class TransactionGrid extends HospitalGrid {

	public TransactionGrid(Indexed dataSource) {
		super(dataSource);
	}

	@Override
	public void prepareColumns() {
		hideColumns(Transaction.VISIBLE);
		setColumnOrder(Transaction.VISIBLE);
		setEditorFieldFactory(new HospitalFieldFactory());
	}

	@Override
	public void setRenderers() {
//		getColumn("Usuń").setRenderer(new ButtonRenderer());
	}
}