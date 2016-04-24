package pl.kalisz.szpital.hr.table;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.tepi.filtertable.paged.PagedFilterTable;

import pl.kalisz.szpital.hr.DBHelper;
import pl.kalisz.szpital.hr.HRUI;
import pl.kalisz.szpital.hr.data.entity.Kontraktowiec;
import pl.kalisz.szpital.hr.data.entity.Specjalizacja;
import pl.kalisz.szpital.hr.table.generator.GeneratedColumnFactory;
import pl.kalisz.szpital.hr.table.generator.HRCellStyleGenerator;
import pl.kalisz.szpital.hr.table.generator.HRFilterDecorator;
import pl.kalisz.szpital.hr.table.generator.HRTableFilterGenerator;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
public class HRTable extends PagedFilterTable<JPAContainer<Kontraktowiec>> {

	Container kontraktowiecContainer;

	@Override
	public int getPageLength() {
		return ((HRUI) UI.getCurrent()).pageLength;
	}

	/**
	 * 
	 */
	private void setParams() {
		setFilterBarVisible(true);

		setColumnCollapsingAllowed(true);
		setColumnReorderingAllowed(true);

		setWidth(100, Unit.PERCENTAGE);
		setHeight(90, Unit.PERCENTAGE);

		setVisibleColumns((Object[]) Kontraktowiec.Columns);
		setColumnHeaders(Kontraktowiec.captions);

		setSelectable(true);
		setImmediate(true);
		setEditable(false);
	}

	/**
	 * 
	 * @param dbHelper
	 */
	public HRTable(DBHelper dbHelper) throws Exception {
		JPAContainer<Specjalizacja> sc;
		sc = dbHelper.getContainer(Specjalizacja.class);
		setFilterDecorator(new HRFilterDecorator());
		setFilterGenerator(new HRTableFilterGenerator(sc));
		addActionHandler(new HRTableActions());
		kontraktowiecContainer = dbHelper.getContainer(Kontraktowiec.class);
		setContainerDataSource((Container.Indexed)kontraktowiecContainer);
		
		setParams();
		setCellStyleGenerator(new HRCellStyleGenerator());

		GeneratedColumnFactory factory = new GeneratedColumnFactory();

		for (String column : GeneratedColumnFactory.GENERATED_COLUMNS) {
			addGeneratedColumn(column, factory.getGenerator(column));
		}

		setTableFieldFactory(new HRTableFieldFactory());
	}

	
	@Override
	protected String formatPropertyValue(Object rowId, Object colId,
			Property<?> property) {
		Object v = property.getValue();
		if (v instanceof Date) {
			Date dateValue = (Date) v;
			return new SimpleDateFormat("dd.MM.yyyy").format(dateValue);
		} else if (colId != null && colId.equals(Kontraktowiec.ARCHIWALNY)) {
			Boolean b = (Boolean) v;
			// boolean true => Archiwalny
			return b ? "archiwalny" : "";
		} else if (colId != null && colId.equals(Kontraktowiec.PLEC)) {
			if (v == null) {
				return "?";
			} else {
				Boolean b = (Boolean) v;
				// boolean true => Archiwalny
				return b ? "mężczyzna" : "kobieta";
			}
		}

		return super.formatPropertyValue(rowId, colId, property);
	}

}
