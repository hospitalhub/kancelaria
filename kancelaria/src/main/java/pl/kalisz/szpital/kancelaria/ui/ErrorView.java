package pl.kalisz.szpital.kancelaria.ui;

import javax.inject.Inject;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ClassResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Image;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import pl.kalisz.szpital.widgetset.WidgetResources;

@SuppressWarnings("serial")
public class ErrorView extends CustomComponent implements View {

  // @Inject
  // private AccessControl accessControl;

  @Inject
  private javax.enterprise.event.Event<NavigationEvent> navigationEvent;

  @Override
  public void enter(ViewChangeEvent event) {
    VerticalLayout layout = new VerticalLayout();
    layout.setSizeFull();

    ClassResource x = new ClassResource(WidgetResources.class, "koniec.jpg");
    Image img = new Image("", x);
    img.setSizeFull();
    layout.addComponent(img);

    setCompositionRoot(layout);

    UI.getCurrent().getPage().setTitle("Pa!");
    VaadinSession.getCurrent().close();
  }

}
