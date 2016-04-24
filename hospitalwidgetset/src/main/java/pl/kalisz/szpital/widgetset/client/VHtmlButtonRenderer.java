package pl.kalisz.szpital.widgetset.client;

import com.google.gwt.core.client.GWT;
import com.vaadin.client.renderers.ClickableRenderer;
import com.vaadin.client.widget.grid.RendererCellReference;
import com.vaadin.ui.themes.ValoTheme;

public class VHtmlButtonRenderer extends ClickableRenderer<String, MyComponentWidget> {

//	private static final String V_NATIVEBUTTON = "v-nativebutton";
//	private static final String V_BUTTON = "v-button";

	@Override
	public void render(RendererCellReference cell, String text, MyComponentWidget button) {
		if (text != null) {
			button.setText("notnull " + text);
		} else {
			button.setText("nuil");
		}
//		button.removeStyleName(V_NATIVEBUTTON);
//		button.setStyleName(V_BUTTON);
		button.addStyleDependentName(ValoTheme.BUTTON_SMALL);
		button.addStyleDependentName(ValoTheme.BUTTON_PRIMARY);

		button.setVisible(text != null);
	}

	@Override
	public MyComponentWidget createWidget() {
		return GWT.create(MyComponentWidget.class);
	}
}