package pl.kalisz.szpital.hr.table.generator;

import com.vaadin.ui.CustomTable.ColumnGenerator;

public class GeneratedColumnFactory {

	public static final String USUWANIE = "Usuwanie";

	public static final String[] GENERATED_COLUMNS = { USUWANIE };

	public static String[] getGeneratedColumns() {
		return GENERATED_COLUMNS;
	}

	public ColumnGenerator getGenerator(String column) {
		switch (column) {
		case USUWANIE:
			return new DeleteColumnGenerator();
		}
		return null;
	}

}
