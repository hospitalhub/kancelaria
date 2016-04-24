package pl.kalisz.szpital.hr.table.generator;

import pl.kalisz.szpital.hr.data.entity.Kontraktowiec;
import pl.kalisz.szpital.hr.table.listener.RowDeleteListener;

import com.vaadin.ui.Button;
import com.vaadin.ui.CustomTable;
import com.vaadin.ui.CustomTable.ColumnGenerator;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
public class DeleteColumnGenerator implements ColumnGenerator {

	private String getProperty(CustomTable table, Object itemId, String property) {
		return table.getItem(itemId).getItemProperty(property).toString();
	}

	@Override
	public Object generateCell(CustomTable source, Object itemId,
			Object columnId) {
		String nazwisko = getProperty(source, itemId, Kontraktowiec.NAZWISKO);
		String imie = getProperty(source, itemId, Kontraktowiec.IMIE);
		Button button = new Button(String.format("Usuń %s %s", nazwisko, imie));
		button.addClickListener(new RowDeleteListener(itemId, source, UI
				.getCurrent()));
		return button;
	}
}
