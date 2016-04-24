package pl.kalisz.szpital.hr.table.generator;

import java.util.Date;

import com.vaadin.ui.CustomTable;
import com.vaadin.ui.CustomTable.CellStyleGenerator;

@SuppressWarnings("serial")
public class HRCellStyleGenerator implements CellStyleGenerator {

	private final static long WEEK_IN_MILLIS = 1000L * 3600 * 24 * 7;
	private final static long MONTH_IN_MILLIS = 1000L * 3600 * 24 * 30;

	@Override
	public String getStyle(CustomTable source, Object itemId, Object propertyId) {
		if (propertyId != null && propertyId.toString().startsWith("data")) {
			int row = ((Integer) itemId).intValue();
			String col = (String) propertyId;
			Date o = (Date) source.getItem(row).getItemProperty(col).getValue();
			if (o == null) {
				return "";
			}
			long timeDiff = o.getTime() - System.currentTimeMillis();
			if (o.before(new Date())) {
				return "red";
			} else if (timeDiff < WEEK_IN_MILLIS) {
				return "yellow";
			} else if (timeDiff < MONTH_IN_MILLIS) {
				return "orange";
			}
		}
		return "";
	}
}
