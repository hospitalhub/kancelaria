package pl.kalisz.szpital.widgetset.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.MouseEventDetailsBuilder;
import com.vaadin.client.annotations.OnStateChange;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.ui.Connect;

@SuppressWarnings("serial")
@Connect(pl.kalisz.szpital.widgetset.MyComponent.class)
public class MyComponentConnector extends AbstractComponentConnector {
	MyComponentServerRpc rpc = RpcProxy.create(MyComponentServerRpc.class, this);

	public MyComponentConnector() {
		getWidget().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				final MouseEventDetails mouseDetails = MouseEventDetailsBuilder
						.buildMouseEventDetails(event.getNativeEvent(), getWidget().getElement());

				rpc.clicked(mouseDetails);
			}
		});
	}

	@Override
	protected Widget createWidget() {
		return GWT.create(MyComponentWidget.class);
	}

	@Override
	public MyComponentWidget getWidget() {
		return (MyComponentWidget) super.getWidget();
	}

	@Override
	public MyComponentState getState() {
		return (MyComponentState) super.getState();
	}

	@OnStateChange("text")
	void updateText() {
		getWidget().setText(getState().text);
	}

}