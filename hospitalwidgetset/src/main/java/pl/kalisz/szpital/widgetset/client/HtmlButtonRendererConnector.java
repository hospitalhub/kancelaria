package pl.kalisz.szpital.widgetset.client;

import com.google.web.bindery.event.shared.HandlerRegistration;
import com.vaadin.client.connectors.ClickableRendererConnector;
import com.vaadin.client.renderers.ClickableRenderer.RendererClickHandler;
import com.vaadin.shared.ui.Connect;

import elemental.json.JsonObject;

@SuppressWarnings("serial")
@Connect(pl.kalisz.szpital.widgetset.HtmlButtonRenderer.class)
public class HtmlButtonRendererConnector extends ClickableRendererConnector<String> {
	@Override
	public VHtmlButtonRenderer getRenderer() {
		return (VHtmlButtonRenderer) super.getRenderer();
	}

	@Override
	protected HandlerRegistration addClickHandler(RendererClickHandler<JsonObject> handler) {
		return getRenderer().addClickHandler(handler);
	}
}