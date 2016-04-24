package pl.kalisz.szpital.kancelaria.ui.tables;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomTable;

@SuppressWarnings("serial")
public class TransactionIdColumnGenerator implements
		CustomTable.ColumnGenerator {

	TransactionFilterGenerator filterGenerator;

	public TransactionIdColumnGenerator(TransactionFilterGenerator filterGenerator) {
		this.filterGenerator = filterGenerator;
	}

	@Override
	public Object generateCell(final CustomTable source, final Object itemId,
			final Object columnId) {
		final CheckBox box = new CheckBox();
		box.setValue(filterGenerator.isChecked((Long) itemId));
		box.setCaption(itemId.toString());
		box.addValueChangeListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				if (box.getValue().booleanValue()) {
					filterGenerator.addItemId(Long.parseLong(box.getCaption()));
				} else {
					filterGenerator.removeItemId(Long.parseLong(box
							.getCaption()));
				}
			}
		});
		return box;
	}
}