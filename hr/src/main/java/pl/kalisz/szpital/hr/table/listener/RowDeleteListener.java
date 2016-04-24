package pl.kalisz.szpital.hr.table.listener;

import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomTable;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
public class RowDeleteListener implements ClickListener {
	Object rowId;
	CustomTable table;
	UI ui;

	public RowDeleteListener(Object rowId, CustomTable table2, UI ui) {
		this.rowId = rowId;
		this.table = table2;
		this.ui = ui;
	}

	public void buttonClick(ClickEvent event) {
		ConfirmDialog.show(ui, event.getButton().getCaption(),
				"Na pewno usunąć?", "Tak", "Nie", new ConfirmDialog.Listener() {

					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							table.removeItem(rowId);
						} else {
						}
					}
				});
	}
}
