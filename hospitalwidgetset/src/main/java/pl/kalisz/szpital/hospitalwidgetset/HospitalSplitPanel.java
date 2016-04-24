package pl.kalisz.szpital.hospitalwidgetset;

import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalSplitPanel;

@SuppressWarnings("serial")
public class HospitalSplitPanel extends VerticalSplitPanel {

	private TabSheet masterSheet = new TabSheet();
	private TabSheet slaveSheet = new TabSheet();

	public HospitalSplitPanel() {
		addComponent(masterSheet);
		addComponent(slaveSheet);
		masterSheet.setSizeFull();
		slaveSheet.setSizeFull();
	}

	public void addMasterSheet(Component c, String caption) {
		masterSheet.addTab(c, caption);
	}

	public void addSlaveSheet(Component c, String caption) {
		slaveSheet.addTab(c, caption);
	}

}
