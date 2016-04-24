package pl.kalisz.szpital.kancelaria.ui.views;

import org.vaadin.appfoundation.authentication.SessionHandler;

import pl.kalisz.szpital.kancelaria.KancelariaUI;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ClassResource;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
public class LogoutView extends CssLayout implements View {

	@Override
	public void enter(ViewChangeEvent event) {
		SessionHandler.logout();
		ClassResource x = new ClassResource(KancelariaUI.class,
				"images/koniec.jpg");
		Image img = new Image("", x);
		img.setSizeFull();
		addComponent(img);
		UI.getCurrent().getPage().setTitle("bye");
	}

}
