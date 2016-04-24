package pl.kalisz.szpital.widgetset.client;

import com.google.gwt.user.client.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

public class MyComponentWidget extends Label {

	public MyComponentWidget() {
		setText("This is MyComponent");
		setStyleName(ValoTheme.BUTTON_BORDERLESS);
		setStyleName(ValoTheme.BUTTON_HUGE);
	}
}