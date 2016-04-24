package pl.kalisz.szpital.hr.table;

import java.util.Date;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public class HRTableActions implements Handler {

	Action nieobecnosci = new Action("Nieobecności");

	@Override
	public void handleAction(Action action, Object sender, Object target) {
		if (action == null || target == null) {
			return;
		}
		if (action == nieobecnosci) {
			System.out.println(sender);
			System.out.println(target);
			Window x = new AbsenceWindow(target);
			UI.getCurrent().addWindow(x);
		}
	}

	@Override
	public Action[] getActions(Object target, Object sender) {
		return new Action[] { nieobecnosci };
	}
}