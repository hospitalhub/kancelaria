package pl.kalisz.szpital.widgetset;

import com.vaadin.shared.MouseEventDetails;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Notification;

import pl.kalisz.szpital.widgetset.client.MyComponentServerRpc;
import pl.kalisz.szpital.widgetset.client.MyComponentState;

public class MyComponent extends AbstractComponent {
	private int clickCount = 0;

	private MyComponentServerRpc rpc = new MyComponentServerRpc() {
		public void clicked(MouseEventDetails mouseDetails) {
			clickCount++;
			setText("You have clicked " + clickCount + " times");
			Notification.show("clicked");
			System.out.println("clicked");
		}
	};

	public MyComponent() {
		registerRpc(rpc);
	}

	@Override
	public MyComponentState getState() {
		return (MyComponentState) super.getState();
	}

	public void setText(String text) {
		getState().text = text;
	}

	public String getText() {
		return getState().text;
	}

}