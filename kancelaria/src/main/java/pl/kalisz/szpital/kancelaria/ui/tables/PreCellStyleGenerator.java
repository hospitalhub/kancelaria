package pl.kalisz.szpital.kancelaria.ui.tables;

import com.vaadin.ui.CustomTable;
import com.vaadin.ui.CustomTable.CellStyleGenerator;

import pl.kalisz.szpital.db.kancelaria.Transaction;

public class PreCellStyleGenerator implements CellStyleGenerator {
	@Override
	public String getStyle(CustomTable source, Object itemId,
			Object propertyId) {
		if (Transaction.ADRES.equals(propertyId)) {
			return "pre";
		}
		return null;
	}
}